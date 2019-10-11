package com.resource.server.web.rest;
import com.resource.server.service.WarrantyTypesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.WarrantyTypesDTO;
import com.resource.server.service.dto.WarrantyTypesCriteria;
import com.resource.server.service.WarrantyTypesQueryService;
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
 * REST controller for managing WarrantyTypes.
 */
@RestController
@RequestMapping("/api")
public class WarrantyTypesResource {

    private final Logger log = LoggerFactory.getLogger(WarrantyTypesResource.class);

    private static final String ENTITY_NAME = "warrantyTypes";

    private final WarrantyTypesService warrantyTypesService;

    private final WarrantyTypesQueryService warrantyTypesQueryService;

    public WarrantyTypesResource(WarrantyTypesService warrantyTypesService, WarrantyTypesQueryService warrantyTypesQueryService) {
        this.warrantyTypesService = warrantyTypesService;
        this.warrantyTypesQueryService = warrantyTypesQueryService;
    }

    /**
     * POST  /warranty-types : Create a new warrantyTypes.
     *
     * @param warrantyTypesDTO the warrantyTypesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new warrantyTypesDTO, or with status 400 (Bad Request) if the warrantyTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/warranty-types")
    public ResponseEntity<WarrantyTypesDTO> createWarrantyTypes(@Valid @RequestBody WarrantyTypesDTO warrantyTypesDTO) throws URISyntaxException {
        log.debug("REST request to save WarrantyTypes : {}", warrantyTypesDTO);
        if (warrantyTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new warrantyTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WarrantyTypesDTO result = warrantyTypesService.save(warrantyTypesDTO);
        return ResponseEntity.created(new URI("/api/warranty-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /warranty-types : Updates an existing warrantyTypes.
     *
     * @param warrantyTypesDTO the warrantyTypesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated warrantyTypesDTO,
     * or with status 400 (Bad Request) if the warrantyTypesDTO is not valid,
     * or with status 500 (Internal Server Error) if the warrantyTypesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/warranty-types")
    public ResponseEntity<WarrantyTypesDTO> updateWarrantyTypes(@Valid @RequestBody WarrantyTypesDTO warrantyTypesDTO) throws URISyntaxException {
        log.debug("REST request to update WarrantyTypes : {}", warrantyTypesDTO);
        if (warrantyTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WarrantyTypesDTO result = warrantyTypesService.save(warrantyTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, warrantyTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /warranty-types : get all the warrantyTypes.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of warrantyTypes in body
     */
    @GetMapping("/warranty-types")
    public ResponseEntity<List<WarrantyTypesDTO>> getAllWarrantyTypes(WarrantyTypesCriteria criteria) {
        log.debug("REST request to get WarrantyTypes by criteria: {}", criteria);
        List<WarrantyTypesDTO> entityList = warrantyTypesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /warranty-types/count : count all the warrantyTypes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/warranty-types/count")
    public ResponseEntity<Long> countWarrantyTypes(WarrantyTypesCriteria criteria) {
        log.debug("REST request to count WarrantyTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(warrantyTypesQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /warranty-types/:id : get the "id" warrantyTypes.
     *
     * @param id the id of the warrantyTypesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the warrantyTypesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/warranty-types/{id}")
    public ResponseEntity<WarrantyTypesDTO> getWarrantyTypes(@PathVariable Long id) {
        log.debug("REST request to get WarrantyTypes : {}", id);
        Optional<WarrantyTypesDTO> warrantyTypesDTO = warrantyTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(warrantyTypesDTO);
    }

    /**
     * DELETE  /warranty-types/:id : delete the "id" warrantyTypes.
     *
     * @param id the id of the warrantyTypesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/warranty-types/{id}")
    public ResponseEntity<Void> deleteWarrantyTypes(@PathVariable Long id) {
        log.debug("REST request to delete WarrantyTypes : {}", id);
        warrantyTypesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
