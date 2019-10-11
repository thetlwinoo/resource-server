package com.resource.server.web.rest;
import com.resource.server.service.UploadTransactionsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.UploadTransactionsDTO;
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
 * REST controller for managing UploadTransactions.
 */
@RestController
@RequestMapping("/api")
public class UploadTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(UploadTransactionsResource.class);

    private static final String ENTITY_NAME = "uploadTransactions";

    private final UploadTransactionsService uploadTransactionsService;

    public UploadTransactionsResource(UploadTransactionsService uploadTransactionsService) {
        this.uploadTransactionsService = uploadTransactionsService;
    }

    /**
     * POST  /upload-transactions : Create a new uploadTransactions.
     *
     * @param uploadTransactionsDTO the uploadTransactionsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uploadTransactionsDTO, or with status 400 (Bad Request) if the uploadTransactions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/upload-transactions")
    public ResponseEntity<UploadTransactionsDTO> createUploadTransactions(@RequestBody UploadTransactionsDTO uploadTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to save UploadTransactions : {}", uploadTransactionsDTO);
        if (uploadTransactionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new uploadTransactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UploadTransactionsDTO result = uploadTransactionsService.save(uploadTransactionsDTO);
        return ResponseEntity.created(new URI("/api/upload-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /upload-transactions : Updates an existing uploadTransactions.
     *
     * @param uploadTransactionsDTO the uploadTransactionsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uploadTransactionsDTO,
     * or with status 400 (Bad Request) if the uploadTransactionsDTO is not valid,
     * or with status 500 (Internal Server Error) if the uploadTransactionsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/upload-transactions")
    public ResponseEntity<UploadTransactionsDTO> updateUploadTransactions(@RequestBody UploadTransactionsDTO uploadTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to update UploadTransactions : {}", uploadTransactionsDTO);
        if (uploadTransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UploadTransactionsDTO result = uploadTransactionsService.save(uploadTransactionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, uploadTransactionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /upload-transactions : get all the uploadTransactions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of uploadTransactions in body
     */
    @GetMapping("/upload-transactions")
    public List<UploadTransactionsDTO> getAllUploadTransactions() {
        log.debug("REST request to get all UploadTransactions");
        return uploadTransactionsService.findAll();
    }

    /**
     * GET  /upload-transactions/:id : get the "id" uploadTransactions.
     *
     * @param id the id of the uploadTransactionsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uploadTransactionsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/upload-transactions/{id}")
    public ResponseEntity<UploadTransactionsDTO> getUploadTransactions(@PathVariable Long id) {
        log.debug("REST request to get UploadTransactions : {}", id);
        Optional<UploadTransactionsDTO> uploadTransactionsDTO = uploadTransactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uploadTransactionsDTO);
    }

    /**
     * DELETE  /upload-transactions/:id : delete the "id" uploadTransactions.
     *
     * @param id the id of the uploadTransactionsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/upload-transactions/{id}")
    public ResponseEntity<Void> deleteUploadTransactions(@PathVariable Long id) {
        log.debug("REST request to delete UploadTransactions : {}", id);
        uploadTransactionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
