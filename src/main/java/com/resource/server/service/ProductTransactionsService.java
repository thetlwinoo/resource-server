package com.resource.server.service;

import com.resource.server.service.dto.ProductTransactionsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ProductTransactions.
 */
public interface ProductTransactionsService {

    /**
     * Save a productTransactions.
     *
     * @param productTransactionsDTO the entity to save
     * @return the persisted entity
     */
    ProductTransactionsDTO save(ProductTransactionsDTO productTransactionsDTO);

    /**
     * Get all the productTransactions.
     *
     * @return the list of entities
     */
    List<ProductTransactionsDTO> findAll();


    /**
     * Get the "id" productTransactions.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductTransactionsDTO> findOne(Long id);

    /**
     * Delete the "id" productTransactions.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
