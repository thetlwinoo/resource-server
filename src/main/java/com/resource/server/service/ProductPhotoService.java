package com.resource.server.service;

import com.resource.server.service.dto.ProductPhotoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ProductPhoto.
 */
public interface ProductPhotoService {

    /**
     * Save a productPhoto.
     *
     * @param productPhotoDTO the entity to save
     * @return the persisted entity
     */
    ProductPhotoDTO save(ProductPhotoDTO productPhotoDTO);

    /**
     * Get all the productPhotos.
     *
     * @return the list of entities
     */
    List<ProductPhotoDTO> findAll();


    /**
     * Get the "id" productPhoto.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductPhotoDTO> findOne(Long id);

    /**
     * Delete the "id" productPhoto.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
