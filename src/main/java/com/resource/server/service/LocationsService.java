package com.resource.server.service;

import com.resource.server.service.dto.LocationsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Locations.
 */
public interface LocationsService {

    /**
     * Save a locations.
     *
     * @param locationsDTO the entity to save
     * @return the persisted entity
     */
    LocationsDTO save(LocationsDTO locationsDTO);

    /**
     * Get all the locations.
     *
     * @return the list of entities
     */
    List<LocationsDTO> findAll();


    /**
     * Get the "id" locations.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LocationsDTO> findOne(Long id);

    /**
     * Delete the "id" locations.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
