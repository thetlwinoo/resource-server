package com.resource.server.service;

import com.resource.server.service.dto.StockGroupsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing StockGroups.
 */
public interface StockGroupsService {

    /**
     * Save a stockGroups.
     *
     * @param stockGroupsDTO the entity to save
     * @return the persisted entity
     */
    StockGroupsDTO save(StockGroupsDTO stockGroupsDTO);

    /**
     * Get all the stockGroups.
     *
     * @return the list of entities
     */
    List<StockGroupsDTO> findAll();


    /**
     * Get the "id" stockGroups.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StockGroupsDTO> findOne(Long id);

    /**
     * Delete the "id" stockGroups.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
