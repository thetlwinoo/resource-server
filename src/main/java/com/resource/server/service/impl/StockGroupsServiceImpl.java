package com.resource.server.service.impl;

import com.resource.server.service.StockGroupsService;
import com.resource.server.domain.StockGroups;
import com.resource.server.repository.StockGroupsRepository;
import com.resource.server.service.dto.StockGroupsDTO;
import com.resource.server.service.mapper.StockGroupsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing StockGroups.
 */
@Service
@Transactional
public class StockGroupsServiceImpl implements StockGroupsService {

    private final Logger log = LoggerFactory.getLogger(StockGroupsServiceImpl.class);

    private final StockGroupsRepository stockGroupsRepository;

    private final StockGroupsMapper stockGroupsMapper;

    public StockGroupsServiceImpl(StockGroupsRepository stockGroupsRepository, StockGroupsMapper stockGroupsMapper) {
        this.stockGroupsRepository = stockGroupsRepository;
        this.stockGroupsMapper = stockGroupsMapper;
    }

    /**
     * Save a stockGroups.
     *
     * @param stockGroupsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StockGroupsDTO save(StockGroupsDTO stockGroupsDTO) {
        log.debug("Request to save StockGroups : {}", stockGroupsDTO);
        StockGroups stockGroups = stockGroupsMapper.toEntity(stockGroupsDTO);
        stockGroups = stockGroupsRepository.save(stockGroups);
        return stockGroupsMapper.toDto(stockGroups);
    }

    /**
     * Get all the stockGroups.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockGroupsDTO> findAll() {
        log.debug("Request to get all StockGroups");
        return stockGroupsRepository.findAll().stream()
            .map(stockGroupsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stockGroups by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockGroupsDTO> findOne(Long id) {
        log.debug("Request to get StockGroups : {}", id);
        return stockGroupsRepository.findById(id)
            .map(stockGroupsMapper::toDto);
    }

    /**
     * Delete the stockGroups by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockGroups : {}", id);
        stockGroupsRepository.deleteById(id);
    }
}
