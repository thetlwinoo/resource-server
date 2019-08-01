package com.resource.server.web.rest;
import com.resource.server.service.SupplierTransactionsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.SupplierTransactionsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SupplierTransactions.
 */
@RestController
@RequestMapping("/api")
public class SupplierTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(SupplierTransactionsResource.class);

    private static final String ENTITY_NAME = "supplierTransactions";

    private final SupplierTransactionsService supplierTransactionsService;

    public SupplierTransactionsResource(SupplierTransactionsService supplierTransactionsService) {
        this.supplierTransactionsService = supplierTransactionsService;
    }

    /**
     * POST  /supplier-transactions : Create a new supplierTransactions.
     *
     * @param supplierTransactionsDTO the supplierTransactionsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supplierTransactionsDTO, or with status 400 (Bad Request) if the supplierTransactions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supplier-transactions")
    public ResponseEntity<SupplierTransactionsDTO> createSupplierTransactions(@Valid @RequestBody SupplierTransactionsDTO supplierTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to save SupplierTransactions : {}", supplierTransactionsDTO);
        if (supplierTransactionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplierTransactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplierTransactionsDTO result = supplierTransactionsService.save(supplierTransactionsDTO);
        return ResponseEntity.created(new URI("/api/supplier-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supplier-transactions : Updates an existing supplierTransactions.
     *
     * @param supplierTransactionsDTO the supplierTransactionsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supplierTransactionsDTO,
     * or with status 400 (Bad Request) if the supplierTransactionsDTO is not valid,
     * or with status 500 (Internal Server Error) if the supplierTransactionsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supplier-transactions")
    public ResponseEntity<SupplierTransactionsDTO> updateSupplierTransactions(@Valid @RequestBody SupplierTransactionsDTO supplierTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to update SupplierTransactions : {}", supplierTransactionsDTO);
        if (supplierTransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplierTransactionsDTO result = supplierTransactionsService.save(supplierTransactionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplierTransactionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supplier-transactions : get all the supplierTransactions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of supplierTransactions in body
     */
    @GetMapping("/supplier-transactions")
    public List<SupplierTransactionsDTO> getAllSupplierTransactions() {
        log.debug("REST request to get all SupplierTransactions");
        return supplierTransactionsService.findAll();
    }

    /**
     * GET  /supplier-transactions/:id : get the "id" supplierTransactions.
     *
     * @param id the id of the supplierTransactionsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supplierTransactionsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/supplier-transactions/{id}")
    public ResponseEntity<SupplierTransactionsDTO> getSupplierTransactions(@PathVariable Long id) {
        log.debug("REST request to get SupplierTransactions : {}", id);
        Optional<SupplierTransactionsDTO> supplierTransactionsDTO = supplierTransactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplierTransactionsDTO);
    }

    /**
     * DELETE  /supplier-transactions/:id : delete the "id" supplierTransactions.
     *
     * @param id the id of the supplierTransactionsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supplier-transactions/{id}")
    public ResponseEntity<Void> deleteSupplierTransactions(@PathVariable Long id) {
        log.debug("REST request to delete SupplierTransactions : {}", id);
        supplierTransactionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
