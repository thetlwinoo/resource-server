package com.resource.server.service.impl;

import com.resource.server.service.LastestMerchantUploadedDocumentService;
import com.resource.server.domain.LastestMerchantUploadedDocument;
import com.resource.server.repository.LastestMerchantUploadedDocumentRepository;
import com.resource.server.service.dto.LastestMerchantUploadedDocumentDTO;
import com.resource.server.service.mapper.LastestMerchantUploadedDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing LastestMerchantUploadedDocument.
 */
@Service
@Transactional
public class LastestMerchantUploadedDocumentServiceImpl implements LastestMerchantUploadedDocumentService {

    private final Logger log = LoggerFactory.getLogger(LastestMerchantUploadedDocumentServiceImpl.class);

    private final LastestMerchantUploadedDocumentRepository lastestMerchantUploadedDocumentRepository;

    private final LastestMerchantUploadedDocumentMapper lastestMerchantUploadedDocumentMapper;

    public LastestMerchantUploadedDocumentServiceImpl(LastestMerchantUploadedDocumentRepository lastestMerchantUploadedDocumentRepository, LastestMerchantUploadedDocumentMapper lastestMerchantUploadedDocumentMapper) {
        this.lastestMerchantUploadedDocumentRepository = lastestMerchantUploadedDocumentRepository;
        this.lastestMerchantUploadedDocumentMapper = lastestMerchantUploadedDocumentMapper;
    }

    /**
     * Save a lastestMerchantUploadedDocument.
     *
     * @param lastestMerchantUploadedDocumentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LastestMerchantUploadedDocumentDTO save(LastestMerchantUploadedDocumentDTO lastestMerchantUploadedDocumentDTO) {
        log.debug("Request to save LastestMerchantUploadedDocument : {}", lastestMerchantUploadedDocumentDTO);
        LastestMerchantUploadedDocument lastestMerchantUploadedDocument = lastestMerchantUploadedDocumentMapper.toEntity(lastestMerchantUploadedDocumentDTO);
        lastestMerchantUploadedDocument = lastestMerchantUploadedDocumentRepository.save(lastestMerchantUploadedDocument);
        return lastestMerchantUploadedDocumentMapper.toDto(lastestMerchantUploadedDocument);
    }

    /**
     * Get all the lastestMerchantUploadedDocuments.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LastestMerchantUploadedDocumentDTO> findAll() {
        log.debug("Request to get all LastestMerchantUploadedDocuments");
        return lastestMerchantUploadedDocumentRepository.findAll().stream()
            .map(lastestMerchantUploadedDocumentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one lastestMerchantUploadedDocument by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LastestMerchantUploadedDocumentDTO> findOne(Long id) {
        log.debug("Request to get LastestMerchantUploadedDocument : {}", id);
        return lastestMerchantUploadedDocumentRepository.findById(id)
            .map(lastestMerchantUploadedDocumentMapper::toDto);
    }

    /**
     * Delete the lastestMerchantUploadedDocument by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LastestMerchantUploadedDocument : {}", id);
        lastestMerchantUploadedDocumentRepository.deleteById(id);
    }
}
