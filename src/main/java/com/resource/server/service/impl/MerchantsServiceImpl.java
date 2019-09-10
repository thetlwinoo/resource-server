package com.resource.server.service.impl;

import com.resource.server.service.MerchantsService;
import com.resource.server.domain.Merchants;
import com.resource.server.repository.MerchantsRepository;
import com.resource.server.service.dto.MerchantsDTO;
import com.resource.server.service.mapper.MerchantsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Merchants.
 */
@Service
@Transactional
public class MerchantsServiceImpl implements MerchantsService {

    private final Logger log = LoggerFactory.getLogger(MerchantsServiceImpl.class);

    private final MerchantsRepository merchantsRepository;

    private final MerchantsMapper merchantsMapper;

    public MerchantsServiceImpl(MerchantsRepository merchantsRepository, MerchantsMapper merchantsMapper) {
        this.merchantsRepository = merchantsRepository;
        this.merchantsMapper = merchantsMapper;
    }

    /**
     * Save a merchants.
     *
     * @param merchantsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MerchantsDTO save(MerchantsDTO merchantsDTO) {
        log.debug("Request to save Merchants : {}", merchantsDTO);
        Merchants merchants = merchantsMapper.toEntity(merchantsDTO);
        merchants = merchantsRepository.save(merchants);
        return merchantsMapper.toDto(merchants);
    }

    /**
     * Get all the merchants.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MerchantsDTO> findAll() {
        log.debug("Request to get all Merchants");
        return merchantsRepository.findAll().stream()
            .map(merchantsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  get all the merchants where Product is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<MerchantsDTO> findAllWhereProductIsNull() {
        log.debug("Request to get all merchants where Product is null");
        return StreamSupport
            .stream(merchantsRepository.findAll().spliterator(), false)
            .filter(merchants -> merchants.getProduct() == null)
            .map(merchantsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one merchants by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MerchantsDTO> findOne(Long id) {
        log.debug("Request to get Merchants : {}", id);
        return merchantsRepository.findById(id)
            .map(merchantsMapper::toDto);
    }

    /**
     * Delete the merchants by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Merchants : {}", id);
        merchantsRepository.deleteById(id);
    }
}
