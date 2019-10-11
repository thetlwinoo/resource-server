package com.resource.server.service.impl;

import com.resource.server.service.StockItemTempService;
import com.resource.server.domain.StockItemTemp;
import com.resource.server.repository.StockItemTempRepository;
import com.resource.server.service.dto.StockItemTempDTO;
import com.resource.server.service.mapper.StockItemTempMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing StockItemTemp.
 */
@Service
@Transactional
public class StockItemTempServiceImpl implements StockItemTempService {

    private final Logger log = LoggerFactory.getLogger(StockItemTempServiceImpl.class);

    private final StockItemTempRepository stockItemTempRepository;

    private final StockItemTempMapper stockItemTempMapper;

    public StockItemTempServiceImpl(StockItemTempRepository stockItemTempRepository, StockItemTempMapper stockItemTempMapper) {
        this.stockItemTempRepository = stockItemTempRepository;
        this.stockItemTempMapper = stockItemTempMapper;
    }

    /**
     * Save a stockItemTemp.
     *
     * @param stockItemTempDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StockItemTempDTO save(StockItemTempDTO stockItemTempDTO) {
        log.debug("Request to save StockItemTemp : {}", stockItemTempDTO);
        StockItemTemp stockItemTemp = stockItemTempMapper.toEntity(stockItemTempDTO);
        stockItemTemp = stockItemTempRepository.save(stockItemTemp);
        return stockItemTempMapper.toDto(stockItemTemp);
    }

    /**
     * Get all the stockItemTemps.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockItemTempDTO> findAll() {
        log.debug("Request to get all StockItemTemps");
        return stockItemTempRepository.findAll().stream()
            .map(stockItemTempMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stockItemTemp by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockItemTempDTO> findOne(Long id) {
        log.debug("Request to get StockItemTemp : {}", id);
        return stockItemTempRepository.findById(id)
            .map(stockItemTempMapper::toDto);
    }

    /**
     * Delete the stockItemTemp by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockItemTemp : {}", id);
        stockItemTempRepository.deleteById(id);
    }
}
