package com.resource.server.service.impl;

import com.resource.server.service.ProductTransactionsService;
import com.resource.server.domain.ProductTransactions;
import com.resource.server.repository.ProductTransactionsRepository;
import com.resource.server.service.dto.ProductTransactionsDTO;
import com.resource.server.service.mapper.ProductTransactionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProductTransactions.
 */
@Service
@Transactional
public class ProductTransactionsServiceImpl implements ProductTransactionsService {

    private final Logger log = LoggerFactory.getLogger(ProductTransactionsServiceImpl.class);

    private final ProductTransactionsRepository productTransactionsRepository;

    private final ProductTransactionsMapper productTransactionsMapper;

    public ProductTransactionsServiceImpl(ProductTransactionsRepository productTransactionsRepository, ProductTransactionsMapper productTransactionsMapper) {
        this.productTransactionsRepository = productTransactionsRepository;
        this.productTransactionsMapper = productTransactionsMapper;
    }

    /**
     * Save a productTransactions.
     *
     * @param productTransactionsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductTransactionsDTO save(ProductTransactionsDTO productTransactionsDTO) {
        log.debug("Request to save ProductTransactions : {}", productTransactionsDTO);
        ProductTransactions productTransactions = productTransactionsMapper.toEntity(productTransactionsDTO);
        productTransactions = productTransactionsRepository.save(productTransactions);
        return productTransactionsMapper.toDto(productTransactions);
    }

    /**
     * Get all the productTransactions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductTransactionsDTO> findAll() {
        log.debug("Request to get all ProductTransactions");
        return productTransactionsRepository.findAll().stream()
            .map(productTransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productTransactions by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductTransactionsDTO> findOne(Long id) {
        log.debug("Request to get ProductTransactions : {}", id);
        return productTransactionsRepository.findById(id)
            .map(productTransactionsMapper::toDto);
    }

    /**
     * Delete the productTransactions by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductTransactions : {}", id);
        productTransactionsRepository.deleteById(id);
    }
}
