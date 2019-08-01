package com.resource.server.web.rest;
import com.resource.server.service.BusinessEntityAddressService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.BusinessEntityAddressDTO;
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
 * REST controller for managing BusinessEntityAddress.
 */
@RestController
@RequestMapping("/api")
public class BusinessEntityAddressResource {

    private final Logger log = LoggerFactory.getLogger(BusinessEntityAddressResource.class);

    private static final String ENTITY_NAME = "businessEntityAddress";

    private final BusinessEntityAddressService businessEntityAddressService;

    public BusinessEntityAddressResource(BusinessEntityAddressService businessEntityAddressService) {
        this.businessEntityAddressService = businessEntityAddressService;
    }

    /**
     * POST  /business-entity-addresses : Create a new businessEntityAddress.
     *
     * @param businessEntityAddressDTO the businessEntityAddressDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new businessEntityAddressDTO, or with status 400 (Bad Request) if the businessEntityAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/business-entity-addresses")
    public ResponseEntity<BusinessEntityAddressDTO> createBusinessEntityAddress(@RequestBody BusinessEntityAddressDTO businessEntityAddressDTO) throws URISyntaxException {
        log.debug("REST request to save BusinessEntityAddress : {}", businessEntityAddressDTO);
        if (businessEntityAddressDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessEntityAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessEntityAddressDTO result = businessEntityAddressService.save(businessEntityAddressDTO);
        return ResponseEntity.created(new URI("/api/business-entity-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /business-entity-addresses : Updates an existing businessEntityAddress.
     *
     * @param businessEntityAddressDTO the businessEntityAddressDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated businessEntityAddressDTO,
     * or with status 400 (Bad Request) if the businessEntityAddressDTO is not valid,
     * or with status 500 (Internal Server Error) if the businessEntityAddressDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/business-entity-addresses")
    public ResponseEntity<BusinessEntityAddressDTO> updateBusinessEntityAddress(@RequestBody BusinessEntityAddressDTO businessEntityAddressDTO) throws URISyntaxException {
        log.debug("REST request to update BusinessEntityAddress : {}", businessEntityAddressDTO);
        if (businessEntityAddressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessEntityAddressDTO result = businessEntityAddressService.save(businessEntityAddressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, businessEntityAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /business-entity-addresses : get all the businessEntityAddresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of businessEntityAddresses in body
     */
    @GetMapping("/business-entity-addresses")
    public List<BusinessEntityAddressDTO> getAllBusinessEntityAddresses() {
        log.debug("REST request to get all BusinessEntityAddresses");
        return businessEntityAddressService.findAll();
    }

    /**
     * GET  /business-entity-addresses/:id : get the "id" businessEntityAddress.
     *
     * @param id the id of the businessEntityAddressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the businessEntityAddressDTO, or with status 404 (Not Found)
     */
    @GetMapping("/business-entity-addresses/{id}")
    public ResponseEntity<BusinessEntityAddressDTO> getBusinessEntityAddress(@PathVariable Long id) {
        log.debug("REST request to get BusinessEntityAddress : {}", id);
        Optional<BusinessEntityAddressDTO> businessEntityAddressDTO = businessEntityAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessEntityAddressDTO);
    }

    /**
     * DELETE  /business-entity-addresses/:id : delete the "id" businessEntityAddress.
     *
     * @param id the id of the businessEntityAddressDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/business-entity-addresses/{id}")
    public ResponseEntity<Void> deleteBusinessEntityAddress(@PathVariable Long id) {
        log.debug("REST request to delete BusinessEntityAddress : {}", id);
        businessEntityAddressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
