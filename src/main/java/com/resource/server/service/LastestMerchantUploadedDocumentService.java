package com.resource.server.service;

import com.resource.server.service.dto.LastestMerchantUploadedDocumentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing LastestMerchantUploadedDocument.
 */
public interface LastestMerchantUploadedDocumentService {

    /**
     * Save a lastestMerchantUploadedDocument.
     *
     * @param lastestMerchantUploadedDocumentDTO the entity to save
     * @return the persisted entity
     */
    LastestMerchantUploadedDocumentDTO save(LastestMerchantUploadedDocumentDTO lastestMerchantUploadedDocumentDTO);

    /**
     * Get all the lastestMerchantUploadedDocuments.
     *
     * @return the list of entities
     */
    List<LastestMerchantUploadedDocumentDTO> findAll();


    /**
     * Get the "id" lastestMerchantUploadedDocument.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LastestMerchantUploadedDocumentDTO> findOne(Long id);

    /**
     * Delete the "id" lastestMerchantUploadedDocument.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
