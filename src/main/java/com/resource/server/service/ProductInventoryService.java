package com.resource.server.service;

import com.resource.server.service.dto.ProductInventoryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ProductInventory.
 */
public interface ProductInventoryService {

    /**
     * Save a productInventory.
     *
     * @param productInventoryDTO the entity to save
     * @return the persisted entity
     */
    ProductInventoryDTO save(ProductInventoryDTO productInventoryDTO);

    /**
     * Get all the productInventories.
     *
     * @return the list of entities
     */
    List<ProductInventoryDTO> findAll();


    /**
     * Get the "id" productInventory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductInventoryDTO> findOne(Long id);

    /**
     * Delete the "id" productInventory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
