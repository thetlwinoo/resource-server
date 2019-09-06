package com.resource.server.web.rest;
import com.resource.server.service.ProductReviewService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductReviewDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProductReview.
 */
@RestController
@RequestMapping("/api")
public class ProductReviewResource {

    private final Logger log = LoggerFactory.getLogger(ProductReviewResource.class);

    private static final String ENTITY_NAME = "productReview";

    private final ProductReviewService productReviewService;

    public ProductReviewResource(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

    /**
     * POST  /product-reviews : Create a new productReview.
     *
     * @param productReviewDTO the productReviewDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productReviewDTO, or with status 400 (Bad Request) if the productReview has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-reviews")
    public ResponseEntity<ProductReviewDTO> createProductReview(@Valid @RequestBody ProductReviewDTO productReviewDTO) throws URISyntaxException {
        log.debug("REST request to save ProductReview : {}", productReviewDTO);
        if (productReviewDTO.getId() != null) {
            throw new BadRequestAlertException("A new productReview cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductReviewDTO result = productReviewService.save(productReviewDTO);
        return ResponseEntity.created(new URI("/api/product-reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-reviews : Updates an existing productReview.
     *
     * @param productReviewDTO the productReviewDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productReviewDTO,
     * or with status 400 (Bad Request) if the productReviewDTO is not valid,
     * or with status 500 (Internal Server Error) if the productReviewDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-reviews")
    public ResponseEntity<ProductReviewDTO> updateProductReview(@Valid @RequestBody ProductReviewDTO productReviewDTO) throws URISyntaxException {
        log.debug("REST request to update ProductReview : {}", productReviewDTO);
        if (productReviewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductReviewDTO result = productReviewService.save(productReviewDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productReviewDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-reviews : get all the productReviews.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productReviews in body
     */
    @GetMapping("/product-reviews")
    public List<ProductReviewDTO> getAllProductReviews() {
        log.debug("REST request to get all ProductReviews");
        return productReviewService.findAll();
    }

    /**
     * GET  /product-reviews/:id : get the "id" productReview.
     *
     * @param id the id of the productReviewDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productReviewDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-reviews/{id}")
    public ResponseEntity<ProductReviewDTO> getProductReview(@PathVariable Long id) {
        log.debug("REST request to get ProductReview : {}", id);
        Optional<ProductReviewDTO> productReviewDTO = productReviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productReviewDTO);
    }

    /**
     * DELETE  /product-reviews/:id : delete the "id" productReview.
     *
     * @param id the id of the productReviewDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-reviews/{id}")
    public ResponseEntity<Void> deleteProductReview(@PathVariable Long id) {
        log.debug("REST request to delete ProductReview : {}", id);
        productReviewService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
