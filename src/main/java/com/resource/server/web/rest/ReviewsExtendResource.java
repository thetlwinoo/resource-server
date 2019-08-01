package com.resource.server.web.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resource.server.service.ReviewsExtendService;
import com.resource.server.service.dto.ReviewsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * ReviewsExtendResource controller
 */
@RestController
@RequestMapping("/api/reviews-extend")
public class ReviewsExtendResource {

    private final Logger log = LoggerFactory.getLogger(ReviewsExtendResource.class);
    private final ReviewsExtendService reviewsExtendService;

    public ReviewsExtendResource(ReviewsExtendService reviewsExtendService) {
        this.reviewsExtendService = reviewsExtendService;
    }

//    @RequestMapping(value = "/reviews/{id}", method = RequestMethod.GET)
//    public ResponseEntity<Reviews> getReviewsByProductId(@PathVariable Long id) {
//        Reviews review = reviewsExtendService.getReviewsByProductId(id);
//        return new ResponseEntity<Reviews>(review, HttpStatus.OK);
//    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity postReview(@RequestBody String payload, Principal principal)throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);
        JsonNode jsonNode1 = actualObj.get("orderId");
        JsonNode jsonNode2 = actualObj.get("reviewAsAnonymous");
        JsonNode jsonNode3 = actualObj.get("overAllSellerRating");
        JsonNode jsonNode4 = actualObj.get("overAllSellerReview");
        JsonNode jsonNode5 = actualObj.get("overAllDeliveryRating");
        JsonNode jsonNode6 = actualObj.get("overAllDeliveryReview");
//        JsonNode jsonNode7 = actualObj.get("completedReview");

        Long orderId = jsonNode1.longValue();

        ReviewsDTO reviewsDTO = new ReviewsDTO();
        reviewsDTO.setOverAllSellerRating(jsonNode3.intValue());
        if(jsonNode4 != null){
            reviewsDTO.setOverAllSellerReview(jsonNode4.textValue());
        }
        reviewsDTO.setOverAllDeliveryRating(jsonNode5.intValue());
        if(jsonNode6 != null) {
            reviewsDTO.setOverAllDeliveryReview(jsonNode6.textValue());
        }
        reviewsDTO.setCompletedReview(false);
        reviewsDTO.setReviewAsAnonymous(jsonNode2.booleanValue());
        reviewsDTO = reviewsExtendService.postReview(principal, reviewsDTO, orderId);
        return new ResponseEntity<ReviewsDTO>(reviewsDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/ordered-products", method = RequestMethod.GET)
    public ResponseEntity getAllOrderedProducts(Principal principal) {
        List returnList = reviewsExtendService.findAllOrderedProducts(principal);
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

}
