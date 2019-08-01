package com.resource.server.web.rest;
import com.resource.server.service.ReviewLinesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ReviewLinesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ReviewLines.
 */
@RestController
@RequestMapping("/api")
public class ReviewLinesResource {

    private final Logger log = LoggerFactory.getLogger(ReviewLinesResource.class);

    private static final String ENTITY_NAME = "reviewLines";

    private final ReviewLinesService reviewLinesService;

    public ReviewLinesResource(ReviewLinesService reviewLinesService) {
        this.reviewLinesService = reviewLinesService;
    }

    /**
     * POST  /review-lines : Create a new reviewLines.
     *
     * @param reviewLinesDTO the reviewLinesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reviewLinesDTO, or with status 400 (Bad Request) if the reviewLines has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/review-lines")
    public ResponseEntity<ReviewLinesDTO> createReviewLines(@RequestBody ReviewLinesDTO reviewLinesDTO) throws URISyntaxException {
        log.debug("REST request to save ReviewLines : {}", reviewLinesDTO);
        if (reviewLinesDTO.getId() != null) {
            throw new BadRequestAlertException("A new reviewLines cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReviewLinesDTO result = reviewLinesService.save(reviewLinesDTO);
        return ResponseEntity.created(new URI("/api/review-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /review-lines : Updates an existing reviewLines.
     *
     * @param reviewLinesDTO the reviewLinesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reviewLinesDTO,
     * or with status 400 (Bad Request) if the reviewLinesDTO is not valid,
     * or with status 500 (Internal Server Error) if the reviewLinesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/review-lines")
    public ResponseEntity<ReviewLinesDTO> updateReviewLines(@RequestBody ReviewLinesDTO reviewLinesDTO) throws URISyntaxException {
        log.debug("REST request to update ReviewLines : {}", reviewLinesDTO);
        if (reviewLinesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReviewLinesDTO result = reviewLinesService.save(reviewLinesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reviewLinesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /review-lines : get all the reviewLines.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reviewLines in body
     */
    @GetMapping("/review-lines")
    public List<ReviewLinesDTO> getAllReviewLines() {
        log.debug("REST request to get all ReviewLines");
        return reviewLinesService.findAll();
    }

    /**
     * GET  /review-lines/:id : get the "id" reviewLines.
     *
     * @param id the id of the reviewLinesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reviewLinesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/review-lines/{id}")
    public ResponseEntity<ReviewLinesDTO> getReviewLines(@PathVariable Long id) {
        log.debug("REST request to get ReviewLines : {}", id);
        Optional<ReviewLinesDTO> reviewLinesDTO = reviewLinesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reviewLinesDTO);
    }

    /**
     * DELETE  /review-lines/:id : delete the "id" reviewLines.
     *
     * @param id the id of the reviewLinesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/review-lines/{id}")
    public ResponseEntity<Void> deleteReviewLines(@PathVariable Long id) {
        log.debug("REST request to delete ReviewLines : {}", id);
        reviewLinesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
