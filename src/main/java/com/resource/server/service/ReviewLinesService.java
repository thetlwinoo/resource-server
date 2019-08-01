package com.resource.server.service;

import com.resource.server.service.dto.ReviewLinesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ReviewLines.
 */
public interface ReviewLinesService {

    /**
     * Save a reviewLines.
     *
     * @param reviewLinesDTO the entity to save
     * @return the persisted entity
     */
    ReviewLinesDTO save(ReviewLinesDTO reviewLinesDTO);

    /**
     * Get all the reviewLines.
     *
     * @return the list of entities
     */
    List<ReviewLinesDTO> findAll();


    /**
     * Get the "id" reviewLines.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ReviewLinesDTO> findOne(Long id);

    /**
     * Delete the "id" reviewLines.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
