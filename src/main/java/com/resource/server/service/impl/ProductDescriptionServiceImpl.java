package com.resource.server.service.impl;

import com.resource.server.service.ProductDescriptionService;
import com.resource.server.domain.ProductDescription;
import com.resource.server.repository.ProductDescriptionRepository;
import com.resource.server.service.dto.ProductDescriptionDTO;
import com.resource.server.service.mapper.ProductDescriptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProductDescription.
 */
@Service
@Transactional
public class ProductDescriptionServiceImpl implements ProductDescriptionService {

    private final Logger log = LoggerFactory.getLogger(ProductDescriptionServiceImpl.class);

    private final ProductDescriptionRepository productDescriptionRepository;

    private final ProductDescriptionMapper productDescriptionMapper;

    public ProductDescriptionServiceImpl(ProductDescriptionRepository productDescriptionRepository, ProductDescriptionMapper productDescriptionMapper) {
        this.productDescriptionRepository = productDescriptionRepository;
        this.productDescriptionMapper = productDescriptionMapper;
    }

    /**
     * Save a productDescription.
     *
     * @param productDescriptionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductDescriptionDTO save(ProductDescriptionDTO productDescriptionDTO) {
        log.debug("Request to save ProductDescription : {}", productDescriptionDTO);
        ProductDescription productDescription = productDescriptionMapper.toEntity(productDescriptionDTO);
        productDescription = productDescriptionRepository.save(productDescription);
        return productDescriptionMapper.toDto(productDescription);
    }

    /**
     * Get all the productDescriptions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductDescriptionDTO> findAll() {
        log.debug("Request to get all ProductDescriptions");
        return productDescriptionRepository.findAll().stream()
            .map(productDescriptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productDescription by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDescriptionDTO> findOne(Long id) {
        log.debug("Request to get ProductDescription : {}", id);
        return productDescriptionRepository.findById(id)
            .map(productDescriptionMapper::toDto);
    }

    /**
     * Delete the productDescription by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductDescription : {}", id);
        productDescriptionRepository.deleteById(id);
    }
}
