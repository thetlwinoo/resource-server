package com.resource.server.service.impl;

import com.resource.server.service.LocationsService;
import com.resource.server.domain.Locations;
import com.resource.server.repository.LocationsRepository;
import com.resource.server.service.dto.LocationsDTO;
import com.resource.server.service.mapper.LocationsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Locations.
 */
@Service
@Transactional
public class LocationsServiceImpl implements LocationsService {

    private final Logger log = LoggerFactory.getLogger(LocationsServiceImpl.class);

    private final LocationsRepository locationsRepository;

    private final LocationsMapper locationsMapper;

    public LocationsServiceImpl(LocationsRepository locationsRepository, LocationsMapper locationsMapper) {
        this.locationsRepository = locationsRepository;
        this.locationsMapper = locationsMapper;
    }

    /**
     * Save a locations.
     *
     * @param locationsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LocationsDTO save(LocationsDTO locationsDTO) {
        log.debug("Request to save Locations : {}", locationsDTO);
        Locations locations = locationsMapper.toEntity(locationsDTO);
        locations = locationsRepository.save(locations);
        return locationsMapper.toDto(locations);
    }

    /**
     * Get all the locations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LocationsDTO> findAll() {
        log.debug("Request to get all Locations");
        return locationsRepository.findAll().stream()
            .map(locationsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one locations by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LocationsDTO> findOne(Long id) {
        log.debug("Request to get Locations : {}", id);
        return locationsRepository.findById(id)
            .map(locationsMapper::toDto);
    }

    /**
     * Delete the locations by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Locations : {}", id);
        locationsRepository.deleteById(id);
    }
}
