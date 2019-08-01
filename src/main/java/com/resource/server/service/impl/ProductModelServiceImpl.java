package com.resource.server.service.impl;

import com.resource.server.service.ProductModelService;
import com.resource.server.domain.ProductModel;
import com.resource.server.repository.ProductModelRepository;
import com.resource.server.service.dto.ProductModelDTO;
import com.resource.server.service.mapper.ProductModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProductModel.
 */
@Service
@Transactional
public class ProductModelServiceImpl implements ProductModelService {

    private final Logger log = LoggerFactory.getLogger(ProductModelServiceImpl.class);

    private final ProductModelRepository productModelRepository;

    private final ProductModelMapper productModelMapper;

    public ProductModelServiceImpl(ProductModelRepository productModelRepository, ProductModelMapper productModelMapper) {
        this.productModelRepository = productModelRepository;
        this.productModelMapper = productModelMapper;
    }

    /**
     * Save a productModel.
     *
     * @param productModelDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductModelDTO save(ProductModelDTO productModelDTO) {
        log.debug("Request to save ProductModel : {}", productModelDTO);
        ProductModel productModel = productModelMapper.toEntity(productModelDTO);
        productModel = productModelRepository.save(productModel);
        return productModelMapper.toDto(productModel);
    }

    /**
     * Get all the productModels.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductModelDTO> findAll() {
        log.debug("Request to get all ProductModels");
        return productModelRepository.findAll().stream()
            .map(productModelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productModel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductModelDTO> findOne(Long id) {
        log.debug("Request to get ProductModel : {}", id);
        return productModelRepository.findById(id)
            .map(productModelMapper::toDto);
    }

    /**
     * Delete the productModel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductModel : {}", id);
        productModelRepository.deleteById(id);
    }
}
