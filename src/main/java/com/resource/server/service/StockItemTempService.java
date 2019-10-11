package com.resource.server.service;

import com.resource.server.service.dto.StockItemTempDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing StockItemTemp.
 */
public interface StockItemTempService {

    /**
     * Save a stockItemTemp.
     *
     * @param stockItemTempDTO the entity to save
     * @return the persisted entity
     */
    StockItemTempDTO save(StockItemTempDTO stockItemTempDTO);

    /**
     * Get all the stockItemTemps.
     *
     * @return the list of entities
     */
    List<StockItemTempDTO> findAll();


    /**
     * Get the "id" stockItemTemp.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StockItemTempDTO> findOne(Long id);

    /**
     * Delete the "id" stockItemTemp.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
