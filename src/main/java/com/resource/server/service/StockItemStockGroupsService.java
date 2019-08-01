package com.resource.server.service;

import com.resource.server.service.dto.StockItemStockGroupsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing StockItemStockGroups.
 */
public interface StockItemStockGroupsService {

    /**
     * Save a stockItemStockGroups.
     *
     * @param stockItemStockGroupsDTO the entity to save
     * @return the persisted entity
     */
    StockItemStockGroupsDTO save(StockItemStockGroupsDTO stockItemStockGroupsDTO);

    /**
     * Get all the stockItemStockGroups.
     *
     * @return the list of entities
     */
    List<StockItemStockGroupsDTO> findAll();


    /**
     * Get the "id" stockItemStockGroups.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StockItemStockGroupsDTO> findOne(Long id);

    /**
     * Delete the "id" stockItemStockGroups.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
