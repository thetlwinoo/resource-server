package com.resource.server.service;

import com.resource.server.service.dto.ProductModelDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ProductModel.
 */
public interface ProductModelService {

    /**
     * Save a productModel.
     *
     * @param productModelDTO the entity to save
     * @return the persisted entity
     */
    ProductModelDTO save(ProductModelDTO productModelDTO);

    /**
     * Get all the productModels.
     *
     * @return the list of entities
     */
    List<ProductModelDTO> findAll();


    /**
     * Get the "id" productModel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductModelDTO> findOne(Long id);

    /**
     * Delete the "id" productModel.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
