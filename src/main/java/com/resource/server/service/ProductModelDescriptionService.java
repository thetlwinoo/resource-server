package com.resource.server.service;

import com.resource.server.service.dto.ProductModelDescriptionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ProductModelDescription.
 */
public interface ProductModelDescriptionService {

    /**
     * Save a productModelDescription.
     *
     * @param productModelDescriptionDTO the entity to save
     * @return the persisted entity
     */
    ProductModelDescriptionDTO save(ProductModelDescriptionDTO productModelDescriptionDTO);

    /**
     * Get all the productModelDescriptions.
     *
     * @return the list of entities
     */
    List<ProductModelDescriptionDTO> findAll();


    /**
     * Get the "id" productModelDescription.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductModelDescriptionDTO> findOne(Long id);

    /**
     * Delete the "id" productModelDescription.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
