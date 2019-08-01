package com.resource.server.web.rest;
import com.resource.server.service.CustomerTransactionsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.CustomerTransactionsDTO;
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
 * REST controller for managing CustomerTransactions.
 */
@RestController
@RequestMapping("/api")
public class CustomerTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(CustomerTransactionsResource.class);

    private static final String ENTITY_NAME = "customerTransactions";

    private final CustomerTransactionsService customerTransactionsService;

    public CustomerTransactionsResource(CustomerTransactionsService customerTransactionsService) {
        this.customerTransactionsService = customerTransactionsService;
    }

    /**
     * POST  /customer-transactions : Create a new customerTransactions.
     *
     * @param customerTransactionsDTO the customerTransactionsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerTransactionsDTO, or with status 400 (Bad Request) if the customerTransactions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-transactions")
    public ResponseEntity<CustomerTransactionsDTO> createCustomerTransactions(@Valid @RequestBody CustomerTransactionsDTO customerTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerTransactions : {}", customerTransactionsDTO);
        if (customerTransactionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerTransactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerTransactionsDTO result = customerTransactionsService.save(customerTransactionsDTO);
        return ResponseEntity.created(new URI("/api/customer-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-transactions : Updates an existing customerTransactions.
     *
     * @param customerTransactionsDTO the customerTransactionsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerTransactionsDTO,
     * or with status 400 (Bad Request) if the customerTransactionsDTO is not valid,
     * or with status 500 (Internal Server Error) if the customerTransactionsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-transactions")
    public ResponseEntity<CustomerTransactionsDTO> updateCustomerTransactions(@Valid @RequestBody CustomerTransactionsDTO customerTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerTransactions : {}", customerTransactionsDTO);
        if (customerTransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerTransactionsDTO result = customerTransactionsService.save(customerTransactionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerTransactionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-transactions : get all the customerTransactions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of customerTransactions in body
     */
    @GetMapping("/customer-transactions")
    public List<CustomerTransactionsDTO> getAllCustomerTransactions() {
        log.debug("REST request to get all CustomerTransactions");
        return customerTransactionsService.findAll();
    }

    /**
     * GET  /customer-transactions/:id : get the "id" customerTransactions.
     *
     * @param id the id of the customerTransactionsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerTransactionsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/customer-transactions/{id}")
    public ResponseEntity<CustomerTransactionsDTO> getCustomerTransactions(@PathVariable Long id) {
        log.debug("REST request to get CustomerTransactions : {}", id);
        Optional<CustomerTransactionsDTO> customerTransactionsDTO = customerTransactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerTransactionsDTO);
    }

    /**
     * DELETE  /customer-transactions/:id : delete the "id" customerTransactions.
     *
     * @param id the id of the customerTransactionsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-transactions/{id}")
    public ResponseEntity<Void> deleteCustomerTransactions(@PathVariable Long id) {
        log.debug("REST request to delete CustomerTransactions : {}", id);
        customerTransactionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
