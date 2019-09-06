package com.resource.server.web.rest;
import com.resource.server.service.ReviewsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ReviewsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Reviews.
 */
@RestController
@RequestMapping("/api")
public class ReviewsResource {

    private final Logger log = LoggerFactory.getLogger(ReviewsResource.class);

    private static final String ENTITY_NAME = "reviews";

    private final ReviewsService reviewsService;

    public ReviewsResource(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    /**
     * POST  /reviews : Create a new reviews.
     *
     * @param reviewsDTO the reviewsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reviewsDTO, or with status 400 (Bad Request) if the reviews has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reviews")
    public ResponseEntity<ReviewsDTO> createReviews(@RequestBody ReviewsDTO reviewsDTO) throws URISyntaxException {
        log.debug("REST request to save Reviews : {}", reviewsDTO);
        if (reviewsDTO.getId() != null) {
            throw new BadRequestAlertException("A new reviews cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReviewsDTO result = reviewsService.save(reviewsDTO);
        return ResponseEntity.created(new URI("/api/reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reviews : Updates an existing reviews.
     *
     * @param reviewsDTO the reviewsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reviewsDTO,
     * or with status 400 (Bad Request) if the reviewsDTO is not valid,
     * or with status 500 (Internal Server Error) if the reviewsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reviews")
    public ResponseEntity<ReviewsDTO> updateReviews(@RequestBody ReviewsDTO reviewsDTO) throws URISyntaxException {
        log.debug("REST request to update Reviews : {}", reviewsDTO);
        if (reviewsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReviewsDTO result = reviewsService.save(reviewsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reviewsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reviews : get all the reviews.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of reviews in body
     */
    @GetMapping("/reviews")
    public List<ReviewsDTO> getAllReviews(@RequestParam(required = false) String filter) {
        if ("review-is-null".equals(filter)) {
            log.debug("REST request to get all Reviewss where review is null");
            return reviewsService.findAllWhereReviewIsNull();
        }
        log.debug("REST request to get all Reviews");
        return reviewsService.findAll();
    }

    /**
     * GET  /reviews/:id : get the "id" reviews.
     *
     * @param id the id of the reviewsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reviewsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewsDTO> getReviews(@PathVariable Long id) {
        log.debug("REST request to get Reviews : {}", id);
        Optional<ReviewsDTO> reviewsDTO = reviewsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reviewsDTO);
    }

    /**
     * DELETE  /reviews/:id : delete the "id" reviews.
     *
     * @param id the id of the reviewsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReviews(@PathVariable Long id) {
        log.debug("REST request to delete Reviews : {}", id);
        reviewsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
