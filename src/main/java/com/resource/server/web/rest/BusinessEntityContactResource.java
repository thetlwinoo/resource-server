package com.resource.server.web.rest;
import com.resource.server.service.BusinessEntityContactService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.BusinessEntityContactDTO;
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
 * REST controller for managing BusinessEntityContact.
 */
@RestController
@RequestMapping("/api")
public class BusinessEntityContactResource {

    private final Logger log = LoggerFactory.getLogger(BusinessEntityContactResource.class);

    private static final String ENTITY_NAME = "businessEntityContact";

    private final BusinessEntityContactService businessEntityContactService;

    public BusinessEntityContactResource(BusinessEntityContactService businessEntityContactService) {
        this.businessEntityContactService = businessEntityContactService;
    }

    /**
     * POST  /business-entity-contacts : Create a new businessEntityContact.
     *
     * @param businessEntityContactDTO the businessEntityContactDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new businessEntityContactDTO, or with status 400 (Bad Request) if the businessEntityContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/business-entity-contacts")
    public ResponseEntity<BusinessEntityContactDTO> createBusinessEntityContact(@RequestBody BusinessEntityContactDTO businessEntityContactDTO) throws URISyntaxException {
        log.debug("REST request to save BusinessEntityContact : {}", businessEntityContactDTO);
        if (businessEntityContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessEntityContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessEntityContactDTO result = businessEntityContactService.save(businessEntityContactDTO);
        return ResponseEntity.created(new URI("/api/business-entity-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /business-entity-contacts : Updates an existing businessEntityContact.
     *
     * @param businessEntityContactDTO the businessEntityContactDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated businessEntityContactDTO,
     * or with status 400 (Bad Request) if the businessEntityContactDTO is not valid,
     * or with status 500 (Internal Server Error) if the businessEntityContactDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/business-entity-contacts")
    public ResponseEntity<BusinessEntityContactDTO> updateBusinessEntityContact(@RequestBody BusinessEntityContactDTO businessEntityContactDTO) throws URISyntaxException {
        log.debug("REST request to update BusinessEntityContact : {}", businessEntityContactDTO);
        if (businessEntityContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessEntityContactDTO result = businessEntityContactService.save(businessEntityContactDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, businessEntityContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /business-entity-contacts : get all the businessEntityContacts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of businessEntityContacts in body
     */
    @GetMapping("/business-entity-contacts")
    public List<BusinessEntityContactDTO> getAllBusinessEntityContacts() {
        log.debug("REST request to get all BusinessEntityContacts");
        return businessEntityContactService.findAll();
    }

    /**
     * GET  /business-entity-contacts/:id : get the "id" businessEntityContact.
     *
     * @param id the id of the businessEntityContactDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the businessEntityContactDTO, or with status 404 (Not Found)
     */
    @GetMapping("/business-entity-contacts/{id}")
    public ResponseEntity<BusinessEntityContactDTO> getBusinessEntityContact(@PathVariable Long id) {
        log.debug("REST request to get BusinessEntityContact : {}", id);
        Optional<BusinessEntityContactDTO> businessEntityContactDTO = businessEntityContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessEntityContactDTO);
    }

    /**
     * DELETE  /business-entity-contacts/:id : delete the "id" businessEntityContact.
     *
     * @param id the id of the businessEntityContactDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/business-entity-contacts/{id}")
    public ResponseEntity<Void> deleteBusinessEntityContact(@PathVariable Long id) {
        log.debug("REST request to delete BusinessEntityContact : {}", id);
        businessEntityContactService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
