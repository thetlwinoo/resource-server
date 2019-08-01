package com.resource.server.web.rest;
import com.resource.server.service.TransactionTypesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.TransactionTypesDTO;
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
 * REST controller for managing TransactionTypes.
 */
@RestController
@RequestMapping("/api")
public class TransactionTypesResource {

    private final Logger log = LoggerFactory.getLogger(TransactionTypesResource.class);

    private static final String ENTITY_NAME = "transactionTypes";

    private final TransactionTypesService transactionTypesService;

    public TransactionTypesResource(TransactionTypesService transactionTypesService) {
        this.transactionTypesService = transactionTypesService;
    }

    /**
     * POST  /transaction-types : Create a new transactionTypes.
     *
     * @param transactionTypesDTO the transactionTypesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transactionTypesDTO, or with status 400 (Bad Request) if the transactionTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transaction-types")
    public ResponseEntity<TransactionTypesDTO> createTransactionTypes(@Valid @RequestBody TransactionTypesDTO transactionTypesDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionTypes : {}", transactionTypesDTO);
        if (transactionTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionTypesDTO result = transactionTypesService.save(transactionTypesDTO);
        return ResponseEntity.created(new URI("/api/transaction-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transaction-types : Updates an existing transactionTypes.
     *
     * @param transactionTypesDTO the transactionTypesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transactionTypesDTO,
     * or with status 400 (Bad Request) if the transactionTypesDTO is not valid,
     * or with status 500 (Internal Server Error) if the transactionTypesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transaction-types")
    public ResponseEntity<TransactionTypesDTO> updateTransactionTypes(@Valid @RequestBody TransactionTypesDTO transactionTypesDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionTypes : {}", transactionTypesDTO);
        if (transactionTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionTypesDTO result = transactionTypesService.save(transactionTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transactionTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transaction-types : get all the transactionTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of transactionTypes in body
     */
    @GetMapping("/transaction-types")
    public List<TransactionTypesDTO> getAllTransactionTypes() {
        log.debug("REST request to get all TransactionTypes");
        return transactionTypesService.findAll();
    }

    /**
     * GET  /transaction-types/:id : get the "id" transactionTypes.
     *
     * @param id the id of the transactionTypesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transactionTypesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/transaction-types/{id}")
    public ResponseEntity<TransactionTypesDTO> getTransactionTypes(@PathVariable Long id) {
        log.debug("REST request to get TransactionTypes : {}", id);
        Optional<TransactionTypesDTO> transactionTypesDTO = transactionTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionTypesDTO);
    }

    /**
     * DELETE  /transaction-types/:id : delete the "id" transactionTypes.
     *
     * @param id the id of the transactionTypesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transaction-types/{id}")
    public ResponseEntity<Void> deleteTransactionTypes(@PathVariable Long id) {
        log.debug("REST request to delete TransactionTypes : {}", id);
        transactionTypesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
