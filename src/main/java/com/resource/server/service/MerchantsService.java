package com.resource.server.service;

import com.resource.server.service.dto.MerchantsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Merchants.
 */
public interface MerchantsService {

    /**
     * Save a merchants.
     *
     * @param merchantsDTO the entity to save
     * @return the persisted entity
     */
    MerchantsDTO save(MerchantsDTO merchantsDTO);

    /**
     * Get all the merchants.
     *
     * @return the list of entities
     */
    List<MerchantsDTO> findAll();


    /**
     * Get the "id" merchants.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MerchantsDTO> findOne(Long id);

    /**
     * Delete the "id" merchants.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
