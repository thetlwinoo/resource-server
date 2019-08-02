package com.resource.server.web.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resource.server.domain.ReviewLines;
import com.resource.server.service.ReviewsExtendService;
import com.resource.server.service.dto.ReviewLinesDTO;
import com.resource.server.service.dto.ReviewsDTO;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    private static final String ENTITY_NAME = "reviews";

    public ReviewsExtendResource(ReviewsExtendService reviewsExtendService) {
        this.reviewsExtendService = reviewsExtendService;
    }

//    @RequestMapping(value = "/reviews/{id}", method = RequestMethod.GET)
//    public ResponseEntity<Reviews> getReviewsByProductId(@PathVariable Long id) {
//        Reviews review = reviewsExtendService.getReviewsByProductId(id);
//        return new ResponseEntity<Reviews>(review, HttpStatus.OK);
//    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, params = "orderId")
    public ResponseEntity<ReviewsDTO> createReviews(@RequestBody ReviewsDTO reviewsDTO, @RequestParam("orderId") Long orderId, Principal principal) throws URISyntaxException {
        log.debug("REST request to save Reviews : {}", reviewsDTO);
        if (reviewsDTO.getId() != null) {
            throw new BadRequestAlertException("A new reviews cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReviewsDTO result = reviewsExtendService.save(principal, reviewsDTO, orderId);
        return ResponseEntity.created(new URI("/api/reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @RequestMapping(value = "/completed", method = RequestMethod.POST, params = "orderId")
    public ResponseEntity<ReviewsDTO> completedReviews(@RequestParam("orderId") Long orderId) {
        ReviewsDTO result = reviewsExtendService.completedReview(orderId);
        return ResponseEntity.ok()
            .body(result);
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT, params = "orderId")
    public ResponseEntity<ReviewsDTO> updateReviews(@RequestBody ReviewsDTO reviewsDTO, @RequestParam("orderId") Long orderId, Principal principal) throws URISyntaxException {
        log.debug("REST request to update Reviews : {}", reviewsDTO);
        if (reviewsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReviewsDTO result = reviewsExtendService.save(principal, reviewsDTO, orderId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reviewsDTO.getId().toString()))
            .body(result);
    }

    @RequestMapping(value = "/ordered-products", method = RequestMethod.GET)
    public ResponseEntity getAllOrderedProducts(Principal principal) {
        List returnList = reviewsExtendService.findAllOrderedProducts(principal);
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

}
