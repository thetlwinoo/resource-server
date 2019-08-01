package com.resource.server.service.impl;

import com.resource.server.service.ProductInventoryService;
import com.resource.server.domain.ProductInventory;
import com.resource.server.repository.ProductInventoryRepository;
import com.resource.server.service.dto.ProductInventoryDTO;
import com.resource.server.service.mapper.ProductInventoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProductInventory.
 */
@Service
@Transactional
public class ProductInventoryServiceImpl implements ProductInventoryService {

    private final Logger log = LoggerFactory.getLogger(ProductInventoryServiceImpl.class);

    private final ProductInventoryRepository productInventoryRepository;

    private final ProductInventoryMapper productInventoryMapper;

    public ProductInventoryServiceImpl(ProductInventoryRepository productInventoryRepository, ProductInventoryMapper productInventoryMapper) {
        this.productInventoryRepository = productInventoryRepository;
        this.productInventoryMapper = productInventoryMapper;
    }

    /**
     * Save a productInventory.
     *
     * @param productInventoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductInventoryDTO save(ProductInventoryDTO productInventoryDTO) {
        log.debug("Request to save ProductInventory : {}", productInventoryDTO);
        ProductInventory productInventory = productInventoryMapper.toEntity(productInventoryDTO);
        productInventory = productInventoryRepository.save(productInventory);
        return productInventoryMapper.toDto(productInventory);
    }

    /**
     * Get all the productInventories.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductInventoryDTO> findAll() {
        log.debug("Request to get all ProductInventories");
        return productInventoryRepository.findAll().stream()
            .map(productInventoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productInventory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductInventoryDTO> findOne(Long id) {
        log.debug("Request to get ProductInventory : {}", id);
        return productInventoryRepository.findById(id)
            .map(productInventoryMapper::toDto);
    }

    /**
     * Delete the productInventory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductInventory : {}", id);
        productInventoryRepository.deleteById(id);
    }
}
