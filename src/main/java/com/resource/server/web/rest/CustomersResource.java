package com.resource.server.web.rest;
import com.resource.server.service.CustomersService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.CustomersDTO;
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
 * REST controller for managing Customers.
 */
@RestController
@RequestMapping("/api")
public class CustomersResource {

    private final Logger log = LoggerFactory.getLogger(CustomersResource.class);

    private static final String ENTITY_NAME = "customers";

    private final CustomersService customersService;

    public CustomersResource(CustomersService customersService) {
        this.customersService = customersService;
    }

    /**
     * POST  /customers : Create a new customers.
     *
     * @param customersDTO the customersDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customersDTO, or with status 400 (Bad Request) if the customers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customers")
    public ResponseEntity<CustomersDTO> createCustomers(@Valid @RequestBody CustomersDTO customersDTO) throws URISyntaxException {
        log.debug("REST request to save Customers : {}", customersDTO);
        if (customersDTO.getId() != null) {
            throw new BadRequestAlertException("A new customers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomersDTO result = customersService.save(customersDTO);
        return ResponseEntity.created(new URI("/api/customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customers : Updates an existing customers.
     *
     * @param customersDTO the customersDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customersDTO,
     * or with status 400 (Bad Request) if the customersDTO is not valid,
     * or with status 500 (Internal Server Error) if the customersDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customers")
    public ResponseEntity<CustomersDTO> updateCustomers(@Valid @RequestBody CustomersDTO customersDTO) throws URISyntaxException {
        log.debug("REST request to update Customers : {}", customersDTO);
        if (customersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomersDTO result = customersService.save(customersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customersDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customers : get all the customers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of customers in body
     */
    @GetMapping("/customers")
    public List<CustomersDTO> getAllCustomers() {
        log.debug("REST request to get all Customers");
        return customersService.findAll();
    }

    /**
     * GET  /customers/:id : get the "id" customers.
     *
     * @param id the id of the customersDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customersDTO, or with status 404 (Not Found)
     */
    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomersDTO> getCustomers(@PathVariable Long id) {
        log.debug("REST request to get Customers : {}", id);
        Optional<CustomersDTO> customersDTO = customersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customersDTO);
    }

    /**
     * DELETE  /customers/:id : delete the "id" customers.
     *
     * @param id the id of the customersDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomers(@PathVariable Long id) {
        log.debug("REST request to delete Customers : {}", id);
        customersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
