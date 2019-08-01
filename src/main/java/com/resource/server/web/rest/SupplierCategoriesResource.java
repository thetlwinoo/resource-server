package com.resource.server.web.rest;
import com.resource.server.service.SupplierCategoriesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.SupplierCategoriesDTO;
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
 * REST controller for managing SupplierCategories.
 */
@RestController
@RequestMapping("/api")
public class SupplierCategoriesResource {

    private final Logger log = LoggerFactory.getLogger(SupplierCategoriesResource.class);

    private static final String ENTITY_NAME = "supplierCategories";

    private final SupplierCategoriesService supplierCategoriesService;

    public SupplierCategoriesResource(SupplierCategoriesService supplierCategoriesService) {
        this.supplierCategoriesService = supplierCategoriesService;
    }

    /**
     * POST  /supplier-categories : Create a new supplierCategories.
     *
     * @param supplierCategoriesDTO the supplierCategoriesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supplierCategoriesDTO, or with status 400 (Bad Request) if the supplierCategories has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supplier-categories")
    public ResponseEntity<SupplierCategoriesDTO> createSupplierCategories(@Valid @RequestBody SupplierCategoriesDTO supplierCategoriesDTO) throws URISyntaxException {
        log.debug("REST request to save SupplierCategories : {}", supplierCategoriesDTO);
        if (supplierCategoriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplierCategories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplierCategoriesDTO result = supplierCategoriesService.save(supplierCategoriesDTO);
        return ResponseEntity.created(new URI("/api/supplier-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supplier-categories : Updates an existing supplierCategories.
     *
     * @param supplierCategoriesDTO the supplierCategoriesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supplierCategoriesDTO,
     * or with status 400 (Bad Request) if the supplierCategoriesDTO is not valid,
     * or with status 500 (Internal Server Error) if the supplierCategoriesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supplier-categories")
    public ResponseEntity<SupplierCategoriesDTO> updateSupplierCategories(@Valid @RequestBody SupplierCategoriesDTO supplierCategoriesDTO) throws URISyntaxException {
        log.debug("REST request to update SupplierCategories : {}", supplierCategoriesDTO);
        if (supplierCategoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplierCategoriesDTO result = supplierCategoriesService.save(supplierCategoriesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplierCategoriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supplier-categories : get all the supplierCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of supplierCategories in body
     */
    @GetMapping("/supplier-categories")
    public List<SupplierCategoriesDTO> getAllSupplierCategories() {
        log.debug("REST request to get all SupplierCategories");
        return supplierCategoriesService.findAll();
    }

    /**
     * GET  /supplier-categories/:id : get the "id" supplierCategories.
     *
     * @param id the id of the supplierCategoriesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supplierCategoriesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/supplier-categories/{id}")
    public ResponseEntity<SupplierCategoriesDTO> getSupplierCategories(@PathVariable Long id) {
        log.debug("REST request to get SupplierCategories : {}", id);
        Optional<SupplierCategoriesDTO> supplierCategoriesDTO = supplierCategoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplierCategoriesDTO);
    }

    /**
     * DELETE  /supplier-categories/:id : delete the "id" supplierCategories.
     *
     * @param id the id of the supplierCategoriesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supplier-categories/{id}")
    public ResponseEntity<Void> deleteSupplierCategories(@PathVariable Long id) {
        log.debug("REST request to delete SupplierCategories : {}", id);
        supplierCategoriesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
