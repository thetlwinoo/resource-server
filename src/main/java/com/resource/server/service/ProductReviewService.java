package com.resource.server.service;

import com.resource.server.service.dto.ProductReviewDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ProductReview.
 */
public interface ProductReviewService {

    /**
     * Save a productReview.
     *
     * @param productReviewDTO the entity to save
     * @return the persisted entity
     */
    ProductReviewDTO save(ProductReviewDTO productReviewDTO);

    /**
     * Get all the productReviews.
     *
     * @return the list of entities
     */
    List<ProductReviewDTO> findAll();


    /**
     * Get the "id" productReview.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductReviewDTO> findOne(Long id);

    /**
     * Delete the "id" productReview.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
