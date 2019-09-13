package com.resource.server.service.impl;

import com.resource.server.service.ProductModelDescriptionService;
import com.resource.server.domain.ProductModelDescription;
import com.resource.server.repository.ProductModelDescriptionRepository;
import com.resource.server.service.dto.ProductModelDescriptionDTO;
import com.resource.server.service.mapper.ProductModelDescriptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProductModelDescription.
 */
@Service
@Transactional
public class ProductModelDescriptionServiceImpl implements ProductModelDescriptionService {

    private final Logger log = LoggerFactory.getLogger(ProductModelDescriptionServiceImpl.class);

    private final ProductModelDescriptionRepository productModelDescriptionRepository;

    private final ProductModelDescriptionMapper productModelDescriptionMapper;

    public ProductModelDescriptionServiceImpl(ProductModelDescriptionRepository productModelDescriptionRepository, ProductModelDescriptionMapper productModelDescriptionMapper) {
        this.productModelDescriptionRepository = productModelDescriptionRepository;
        this.productModelDescriptionMapper = productModelDescriptionMapper;
    }

    /**
     * Save a productModelDescription.
     *
     * @param productModelDescriptionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductModelDescriptionDTO save(ProductModelDescriptionDTO productModelDescriptionDTO) {
        log.debug("Request to save ProductModelDescription : {}", productModelDescriptionDTO);
        ProductModelDescription productModelDescription = productModelDescriptionMapper.toEntity(productModelDescriptionDTO);
        productModelDescription = productModelDescriptionRepository.save(productModelDescription);
        return productModelDescriptionMapper.toDto(productModelDescription);
    }

    /**
     * Get all the productModelDescriptions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductModelDescriptionDTO> findAll() {
        log.debug("Request to get all ProductModelDescriptions");
        return productModelDescriptionRepository.findAll().stream()
            .map(productModelDescriptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productModelDescription by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductModelDescriptionDTO> findOne(Long id) {
        log.debug("Request to get ProductModelDescription : {}", id);
        return productModelDescriptionRepository.findById(id)
            .map(productModelDescriptionMapper::toDto);
    }

    /**
     * Delete the productModelDescription by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductModelDescription : {}", id);
        productModelDescriptionRepository.deleteById(id);
    }
}
