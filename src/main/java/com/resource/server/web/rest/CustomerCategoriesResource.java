package com.resource.server.web.rest;
import com.resource.server.service.CustomerCategoriesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.CustomerCategoriesDTO;
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
 * REST controller for managing CustomerCategories.
 */
@RestController
@RequestMapping("/api")
public class CustomerCategoriesResource {

    private final Logger log = LoggerFactory.getLogger(CustomerCategoriesResource.class);

    private static final String ENTITY_NAME = "customerCategories";

    private final CustomerCategoriesService customerCategoriesService;

    public CustomerCategoriesResource(CustomerCategoriesService customerCategoriesService) {
        this.customerCategoriesService = customerCategoriesService;
    }

    /**
     * POST  /customer-categories : Create a new customerCategories.
     *
     * @param customerCategoriesDTO the customerCategoriesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerCategoriesDTO, or with status 400 (Bad Request) if the customerCategories has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-categories")
    public ResponseEntity<CustomerCategoriesDTO> createCustomerCategories(@Valid @RequestBody CustomerCategoriesDTO customerCategoriesDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerCategories : {}", customerCategoriesDTO);
        if (customerCategoriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerCategories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerCategoriesDTO result = customerCategoriesService.save(customerCategoriesDTO);
        return ResponseEntity.created(new URI("/api/customer-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-categories : Updates an existing customerCategories.
     *
     * @param customerCategoriesDTO the customerCategoriesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerCategoriesDTO,
     * or with status 400 (Bad Request) if the customerCategoriesDTO is not valid,
     * or with status 500 (Internal Server Error) if the customerCategoriesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-categories")
    public ResponseEntity<CustomerCategoriesDTO> updateCustomerCategories(@Valid @RequestBody CustomerCategoriesDTO customerCategoriesDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerCategories : {}", customerCategoriesDTO);
        if (customerCategoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerCategoriesDTO result = customerCategoriesService.save(customerCategoriesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerCategoriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-categories : get all the customerCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of customerCategories in body
     */
    @GetMapping("/customer-categories")
    public List<CustomerCategoriesDTO> getAllCustomerCategories() {
        log.debug("REST request to get all CustomerCategories");
        return customerCategoriesService.findAll();
    }

    /**
     * GET  /customer-categories/:id : get the "id" customerCategories.
     *
     * @param id the id of the customerCategoriesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerCategoriesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/customer-categories/{id}")
    public ResponseEntity<CustomerCategoriesDTO> getCustomerCategories(@PathVariable Long id) {
        log.debug("REST request to get CustomerCategories : {}", id);
        Optional<CustomerCategoriesDTO> customerCategoriesDTO = customerCategoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerCategoriesDTO);
    }

    /**
     * DELETE  /customer-categories/:id : delete the "id" customerCategories.
     *
     * @param id the id of the customerCategoriesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-categories/{id}")
    public ResponseEntity<Void> deleteCustomerCategories(@PathVariable Long id) {
        log.debug("REST request to delete CustomerCategories : {}", id);
        customerCategoriesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
