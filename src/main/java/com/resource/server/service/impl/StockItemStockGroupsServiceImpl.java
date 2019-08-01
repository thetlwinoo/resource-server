package com.resource.server.service.impl;

import com.resource.server.service.StockItemStockGroupsService;
import com.resource.server.domain.StockItemStockGroups;
import com.resource.server.repository.StockItemStockGroupsRepository;
import com.resource.server.service.dto.StockItemStockGroupsDTO;
import com.resource.server.service.mapper.StockItemStockGroupsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing StockItemStockGroups.
 */
@Service
@Transactional
public class StockItemStockGroupsServiceImpl implements StockItemStockGroupsService {

    private final Logger log = LoggerFactory.getLogger(StockItemStockGroupsServiceImpl.class);

    private final StockItemStockGroupsRepository stockItemStockGroupsRepository;

    private final StockItemStockGroupsMapper stockItemStockGroupsMapper;

    public StockItemStockGroupsServiceImpl(StockItemStockGroupsRepository stockItemStockGroupsRepository, StockItemStockGroupsMapper stockItemStockGroupsMapper) {
        this.stockItemStockGroupsRepository = stockItemStockGroupsRepository;
        this.stockItemStockGroupsMapper = stockItemStockGroupsMapper;
    }

    /**
     * Save a stockItemStockGroups.
     *
     * @param stockItemStockGroupsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StockItemStockGroupsDTO save(StockItemStockGroupsDTO stockItemStockGroupsDTO) {
        log.debug("Request to save StockItemStockGroups : {}", stockItemStockGroupsDTO);
        StockItemStockGroups stockItemStockGroups = stockItemStockGroupsMapper.toEntity(stockItemStockGroupsDTO);
        stockItemStockGroups = stockItemStockGroupsRepository.save(stockItemStockGroups);
        return stockItemStockGroupsMapper.toDto(stockItemStockGroups);
    }

    /**
     * Get all the stockItemStockGroups.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockItemStockGroupsDTO> findAll() {
        log.debug("Request to get all StockItemStockGroups");
        return stockItemStockGroupsRepository.findAll().stream()
            .map(stockItemStockGroupsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stockItemStockGroups by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockItemStockGroupsDTO> findOne(Long id) {
        log.debug("Request to get StockItemStockGroups : {}", id);
        return stockItemStockGroupsRepository.findById(id)
            .map(stockItemStockGroupsMapper::toDto);
    }

    /**
     * Delete the stockItemStockGroups by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockItemStockGroups : {}", id);
        stockItemStockGroupsRepository.deleteById(id);
    }
}
