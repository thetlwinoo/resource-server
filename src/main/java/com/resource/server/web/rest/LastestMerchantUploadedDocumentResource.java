package com.resource.server.web.rest;
import com.resource.server.service.LastestMerchantUploadedDocumentService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.LastestMerchantUploadedDocumentDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LastestMerchantUploadedDocument.
 */
@RestController
@RequestMapping("/api")
public class LastestMerchantUploadedDocumentResource {

    private final Logger log = LoggerFactory.getLogger(LastestMerchantUploadedDocumentResource.class);

    private static final String ENTITY_NAME = "lastestMerchantUploadedDocument";

    private final LastestMerchantUploadedDocumentService lastestMerchantUploadedDocumentService;

    public LastestMerchantUploadedDocumentResource(LastestMerchantUploadedDocumentService lastestMerchantUploadedDocumentService) {
        this.lastestMerchantUploadedDocumentService = lastestMerchantUploadedDocumentService;
    }

    /**
     * POST  /lastest-merchant-uploaded-documents : Create a new lastestMerchantUploadedDocument.
     *
     * @param lastestMerchantUploadedDocumentDTO the lastestMerchantUploadedDocumentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lastestMerchantUploadedDocumentDTO, or with status 400 (Bad Request) if the lastestMerchantUploadedDocument has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lastest-merchant-uploaded-documents")
    public ResponseEntity<LastestMerchantUploadedDocumentDTO> createLastestMerchantUploadedDocument(@RequestBody LastestMerchantUploadedDocumentDTO lastestMerchantUploadedDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save LastestMerchantUploadedDocument : {}", lastestMerchantUploadedDocumentDTO);
        if (lastestMerchantUploadedDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new lastestMerchantUploadedDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LastestMerchantUploadedDocumentDTO result = lastestMerchantUploadedDocumentService.save(lastestMerchantUploadedDocumentDTO);
        return ResponseEntity.created(new URI("/api/lastest-merchant-uploaded-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lastest-merchant-uploaded-documents : Updates an existing lastestMerchantUploadedDocument.
     *
     * @param lastestMerchantUploadedDocumentDTO the lastestMerchantUploadedDocumentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lastestMerchantUploadedDocumentDTO,
     * or with status 400 (Bad Request) if the lastestMerchantUploadedDocumentDTO is not valid,
     * or with status 500 (Internal Server Error) if the lastestMerchantUploadedDocumentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lastest-merchant-uploaded-documents")
    public ResponseEntity<LastestMerchantUploadedDocumentDTO> updateLastestMerchantUploadedDocument(@RequestBody LastestMerchantUploadedDocumentDTO lastestMerchantUploadedDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update LastestMerchantUploadedDocument : {}", lastestMerchantUploadedDocumentDTO);
        if (lastestMerchantUploadedDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LastestMerchantUploadedDocumentDTO result = lastestMerchantUploadedDocumentService.save(lastestMerchantUploadedDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lastestMerchantUploadedDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lastest-merchant-uploaded-documents : get all the lastestMerchantUploadedDocuments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lastestMerchantUploadedDocuments in body
     */
    @GetMapping("/lastest-merchant-uploaded-documents")
    public List<LastestMerchantUploadedDocumentDTO> getAllLastestMerchantUploadedDocuments() {
        log.debug("REST request to get all LastestMerchantUploadedDocuments");
        return lastestMerchantUploadedDocumentService.findAll();
    }

    /**
     * GET  /lastest-merchant-uploaded-documents/:id : get the "id" lastestMerchantUploadedDocument.
     *
     * @param id the id of the lastestMerchantUploadedDocumentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lastestMerchantUploadedDocumentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lastest-merchant-uploaded-documents/{id}")
    public ResponseEntity<LastestMerchantUploadedDocumentDTO> getLastestMerchantUploadedDocument(@PathVariable Long id) {
        log.debug("REST request to get LastestMerchantUploadedDocument : {}", id);
        Optional<LastestMerchantUploadedDocumentDTO> lastestMerchantUploadedDocumentDTO = lastestMerchantUploadedDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lastestMerchantUploadedDocumentDTO);
    }

    /**
     * DELETE  /lastest-merchant-uploaded-documents/:id : delete the "id" lastestMerchantUploadedDocument.
     *
     * @param id the id of the lastestMerchantUploadedDocumentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lastest-merchant-uploaded-documents/{id}")
    public ResponseEntity<Void> deleteLastestMerchantUploadedDocument(@PathVariable Long id) {
        log.debug("REST request to delete LastestMerchantUploadedDocument : {}", id);
        lastestMerchantUploadedDocumentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
