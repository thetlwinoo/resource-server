package com.resource.server.web.rest;
import com.resource.server.service.SuppliersService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.SuppliersDTO;
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
 * REST controller for managing Suppliers.
 */
@RestController
@RequestMapping("/api")
public class SuppliersResource {

    private final Logger log = LoggerFactory.getLogger(SuppliersResource.class);

    private static final String ENTITY_NAME = "suppliers";

    private final SuppliersService suppliersService;

    public SuppliersResource(SuppliersService suppliersService) {
        this.suppliersService = suppliersService;
    }

    /**
     * POST  /suppliers : Create a new suppliers.
     *
     * @param suppliersDTO the suppliersDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new suppliersDTO, or with status 400 (Bad Request) if the suppliers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/suppliers")
    public ResponseEntity<SuppliersDTO> createSuppliers(@Valid @RequestBody SuppliersDTO suppliersDTO) throws URISyntaxException {
        log.debug("REST request to save Suppliers : {}", suppliersDTO);
        if (suppliersDTO.getId() != null) {
            throw new BadRequestAlertException("A new suppliers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SuppliersDTO result = suppliersService.save(suppliersDTO);
        return ResponseEntity.created(new URI("/api/suppliers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /suppliers : Updates an existing suppliers.
     *
     * @param suppliersDTO the suppliersDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated suppliersDTO,
     * or with status 400 (Bad Request) if the suppliersDTO is not valid,
     * or with status 500 (Internal Server Error) if the suppliersDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/suppliers")
    public ResponseEntity<SuppliersDTO> updateSuppliers(@Valid @RequestBody SuppliersDTO suppliersDTO) throws URISyntaxException {
        log.debug("REST request to update Suppliers : {}", suppliersDTO);
        if (suppliersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SuppliersDTO result = suppliersService.save(suppliersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, suppliersDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /suppliers : get all the suppliers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of suppliers in body
     */
    @GetMapping("/suppliers")
    public List<SuppliersDTO> getAllSuppliers() {
        log.debug("REST request to get all Suppliers");
        return suppliersService.findAll();
    }

    /**
     * GET  /suppliers/:id : get the "id" suppliers.
     *
     * @param id the id of the suppliersDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the suppliersDTO, or with status 404 (Not Found)
     */
    @GetMapping("/suppliers/{id}")
    public ResponseEntity<SuppliersDTO> getSuppliers(@PathVariable Long id) {
        log.debug("REST request to get Suppliers : {}", id);
        Optional<SuppliersDTO> suppliersDTO = suppliersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(suppliersDTO);
    }

    /**
     * DELETE  /suppliers/:id : delete the "id" suppliers.
     *
     * @param id the id of the suppliersDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/suppliers/{id}")
    public ResponseEntity<Void> deleteSuppliers(@PathVariable Long id) {
        log.debug("REST request to delete Suppliers : {}", id);
        suppliersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
