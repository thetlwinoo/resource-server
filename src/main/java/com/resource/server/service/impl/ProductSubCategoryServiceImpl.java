package com.resource.server.service.impl;

import com.resource.server.service.ProductSubCategoryService;
import com.resource.server.domain.ProductSubCategory;
import com.resource.server.repository.ProductSubCategoryRepository;
import com.resource.server.service.dto.ProductSubCategoryDTO;
import com.resource.server.service.mapper.ProductSubCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ProductSubCategory.
 */
@Service
@Transactional
public class ProductSubCategoryServiceImpl implements ProductSubCategoryService {

    private final Logger log = LoggerFactory.getLogger(ProductSubCategoryServiceImpl.class);

    private final ProductSubCategoryRepository productSubCategoryRepository;

    private final ProductSubCategoryMapper productSubCategoryMapper;

    public ProductSubCategoryServiceImpl(ProductSubCategoryRepository productSubCategoryRepository, ProductSubCategoryMapper productSubCategoryMapper) {
        this.productSubCategoryRepository = productSubCategoryRepository;
        this.productSubCategoryMapper = productSubCategoryMapper;
    }

    /**
     * Save a productSubCategory.
     *
     * @param productSubCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductSubCategoryDTO save(ProductSubCategoryDTO productSubCategoryDTO) {
        log.debug("Request to save ProductSubCategory : {}", productSubCategoryDTO);
        ProductSubCategory productSubCategory = productSubCategoryMapper.toEntity(productSubCategoryDTO);
        productSubCategory = productSubCategoryRepository.save(productSubCategory);
        return productSubCategoryMapper.toDto(productSubCategory);
    }

    /**
     * Get all the productSubCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductSubCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductSubCategories");
        return productSubCategoryRepository.findAll(pageable)
            .map(productSubCategoryMapper::toDto);
    }


    /**
     * Get one productSubCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductSubCategoryDTO> findOne(Long id) {
        log.debug("Request to get ProductSubCategory : {}", id);
        return productSubCategoryRepository.findById(id)
            .map(productSubCategoryMapper::toDto);
    }

    /**
     * Delete the productSubCategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductSubCategory : {}", id);
        productSubCategoryRepository.deleteById(id);
    }
}
