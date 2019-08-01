package com.resource.server.web.rest;
import com.resource.server.service.SystemParametersService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.SystemParametersDTO;
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
 * REST controller for managing SystemParameters.
 */
@RestController
@RequestMapping("/api")
public class SystemParametersResource {

    private final Logger log = LoggerFactory.getLogger(SystemParametersResource.class);

    private static final String ENTITY_NAME = "systemParameters";

    private final SystemParametersService systemParametersService;

    public SystemParametersResource(SystemParametersService systemParametersService) {
        this.systemParametersService = systemParametersService;
    }

    /**
     * POST  /system-parameters : Create a new systemParameters.
     *
     * @param systemParametersDTO the systemParametersDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new systemParametersDTO, or with status 400 (Bad Request) if the systemParameters has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/system-parameters")
    public ResponseEntity<SystemParametersDTO> createSystemParameters(@Valid @RequestBody SystemParametersDTO systemParametersDTO) throws URISyntaxException {
        log.debug("REST request to save SystemParameters : {}", systemParametersDTO);
        if (systemParametersDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemParameters cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemParametersDTO result = systemParametersService.save(systemParametersDTO);
        return ResponseEntity.created(new URI("/api/system-parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /system-parameters : Updates an existing systemParameters.
     *
     * @param systemParametersDTO the systemParametersDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated systemParametersDTO,
     * or with status 400 (Bad Request) if the systemParametersDTO is not valid,
     * or with status 500 (Internal Server Error) if the systemParametersDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/system-parameters")
    public ResponseEntity<SystemParametersDTO> updateSystemParameters(@Valid @RequestBody SystemParametersDTO systemParametersDTO) throws URISyntaxException {
        log.debug("REST request to update SystemParameters : {}", systemParametersDTO);
        if (systemParametersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemParametersDTO result = systemParametersService.save(systemParametersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, systemParametersDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /system-parameters : get all the systemParameters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of systemParameters in body
     */
    @GetMapping("/system-parameters")
    public List<SystemParametersDTO> getAllSystemParameters() {
        log.debug("REST request to get all SystemParameters");
        return systemParametersService.findAll();
    }

    /**
     * GET  /system-parameters/:id : get the "id" systemParameters.
     *
     * @param id the id of the systemParametersDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the systemParametersDTO, or with status 404 (Not Found)
     */
    @GetMapping("/system-parameters/{id}")
    public ResponseEntity<SystemParametersDTO> getSystemParameters(@PathVariable Long id) {
        log.debug("REST request to get SystemParameters : {}", id);
        Optional<SystemParametersDTO> systemParametersDTO = systemParametersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemParametersDTO);
    }

    /**
     * DELETE  /system-parameters/:id : delete the "id" systemParameters.
     *
     * @param id the id of the systemParametersDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/system-parameters/{id}")
    public ResponseEntity<Void> deleteSystemParameters(@PathVariable Long id) {
        log.debug("REST request to delete SystemParameters : {}", id);
        systemParametersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
