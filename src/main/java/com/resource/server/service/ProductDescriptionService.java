package com.resource.server.service;

import com.resource.server.service.dto.ProductDescriptionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ProductDescription.
 */
public interface ProductDescriptionService {

    /**
     * Save a productDescription.
     *
     * @param productDescriptionDTO the entity to save
     * @return the persisted entity
     */
    ProductDescriptionDTO save(ProductDescriptionDTO productDescriptionDTO);

    /**
     * Get all the productDescriptions.
     *
     * @return the list of entities
     */
    List<ProductDescriptionDTO> findAll();


    /**
     * Get the "id" productDescription.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductDescriptionDTO> findOne(Long id);

    /**
     * Delete the "id" productDescription.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
