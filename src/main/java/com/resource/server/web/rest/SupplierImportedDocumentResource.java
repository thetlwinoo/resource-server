package com.resource.server.web.rest;
import com.resource.server.service.SupplierImportedDocumentService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.SupplierImportedDocumentDTO;
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
 * REST controller for managing SupplierImportedDocument.
 */
@RestController
@RequestMapping("/api")
public class SupplierImportedDocumentResource {

    private final Logger log = LoggerFactory.getLogger(SupplierImportedDocumentResource.class);

    private static final String ENTITY_NAME = "supplierImportedDocument";

    private final SupplierImportedDocumentService supplierImportedDocumentService;

    public SupplierImportedDocumentResource(SupplierImportedDocumentService supplierImportedDocumentService) {
        this.supplierImportedDocumentService = supplierImportedDocumentService;
    }

    /**
     * POST  /supplier-imported-documents : Create a new supplierImportedDocument.
     *
     * @param supplierImportedDocumentDTO the supplierImportedDocumentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supplierImportedDocumentDTO, or with status 400 (Bad Request) if the supplierImportedDocument has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supplier-imported-documents")
    public ResponseEntity<SupplierImportedDocumentDTO> createSupplierImportedDocument(@RequestBody SupplierImportedDocumentDTO supplierImportedDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save SupplierImportedDocument : {}", supplierImportedDocumentDTO);
        if (supplierImportedDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplierImportedDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplierImportedDocumentDTO result = supplierImportedDocumentService.save(supplierImportedDocumentDTO);
        return ResponseEntity.created(new URI("/api/supplier-imported-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supplier-imported-documents : Updates an existing supplierImportedDocument.
     *
     * @param supplierImportedDocumentDTO the supplierImportedDocumentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supplierImportedDocumentDTO,
     * or with status 400 (Bad Request) if the supplierImportedDocumentDTO is not valid,
     * or with status 500 (Internal Server Error) if the supplierImportedDocumentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supplier-imported-documents")
    public ResponseEntity<SupplierImportedDocumentDTO> updateSupplierImportedDocument(@RequestBody SupplierImportedDocumentDTO supplierImportedDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update SupplierImportedDocument : {}", supplierImportedDocumentDTO);
        if (supplierImportedDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplierImportedDocumentDTO result = supplierImportedDocumentService.save(supplierImportedDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplierImportedDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supplier-imported-documents : get all the supplierImportedDocuments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of supplierImportedDocuments in body
     */
    @GetMapping("/supplier-imported-documents")
    public List<SupplierImportedDocumentDTO> getAllSupplierImportedDocuments() {
        log.debug("REST request to get all SupplierImportedDocuments");
        return supplierImportedDocumentService.findAll();
    }

    /**
     * GET  /supplier-imported-documents/:id : get the "id" supplierImportedDocument.
     *
     * @param id the id of the supplierImportedDocumentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supplierImportedDocumentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/supplier-imported-documents/{id}")
    public ResponseEntity<SupplierImportedDocumentDTO> getSupplierImportedDocument(@PathVariable Long id) {
        log.debug("REST request to get SupplierImportedDocument : {}", id);
        Optional<SupplierImportedDocumentDTO> supplierImportedDocumentDTO = supplierImportedDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplierImportedDocumentDTO);
    }

    /**
     * DELETE  /supplier-imported-documents/:id : delete the "id" supplierImportedDocument.
     *
     * @param id the id of the supplierImportedDocumentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supplier-imported-documents/{id}")
    public ResponseEntity<Void> deleteSupplierImportedDocument(@PathVariable Long id) {
        log.debug("REST request to delete SupplierImportedDocument : {}", id);
        supplierImportedDocumentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
