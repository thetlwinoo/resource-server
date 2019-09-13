package com.resource.server.service;

import com.resource.server.service.dto.ProductCategoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ProductCategory.
 */
public interface ProductCategoryService {

    /**
     * Save a productCategory.
     *
     * @param productCategoryDTO the entity to save
     * @return the persisted entity
     */
    ProductCategoryDTO save(ProductCategoryDTO productCategoryDTO);

    /**
     * Get all the productCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductCategoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" productCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" productCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
