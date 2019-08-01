package com.resource.server.web.rest;
import com.resource.server.service.StateProvincesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.StateProvincesDTO;
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
 * REST controller for managing StateProvinces.
 */
@RestController
@RequestMapping("/api")
public class StateProvincesResource {

    private final Logger log = LoggerFactory.getLogger(StateProvincesResource.class);

    private static final String ENTITY_NAME = "stateProvinces";

    private final StateProvincesService stateProvincesService;

    public StateProvincesResource(StateProvincesService stateProvincesService) {
        this.stateProvincesService = stateProvincesService;
    }

    /**
     * POST  /state-provinces : Create a new stateProvinces.
     *
     * @param stateProvincesDTO the stateProvincesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stateProvincesDTO, or with status 400 (Bad Request) if the stateProvinces has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/state-provinces")
    public ResponseEntity<StateProvincesDTO> createStateProvinces(@Valid @RequestBody StateProvincesDTO stateProvincesDTO) throws URISyntaxException {
        log.debug("REST request to save StateProvinces : {}", stateProvincesDTO);
        if (stateProvincesDTO.getId() != null) {
            throw new BadRequestAlertException("A new stateProvinces cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StateProvincesDTO result = stateProvincesService.save(stateProvincesDTO);
        return ResponseEntity.created(new URI("/api/state-provinces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /state-provinces : Updates an existing stateProvinces.
     *
     * @param stateProvincesDTO the stateProvincesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stateProvincesDTO,
     * or with status 400 (Bad Request) if the stateProvincesDTO is not valid,
     * or with status 500 (Internal Server Error) if the stateProvincesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/state-provinces")
    public ResponseEntity<StateProvincesDTO> updateStateProvinces(@Valid @RequestBody StateProvincesDTO stateProvincesDTO) throws URISyntaxException {
        log.debug("REST request to update StateProvinces : {}", stateProvincesDTO);
        if (stateProvincesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StateProvincesDTO result = stateProvincesService.save(stateProvincesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stateProvincesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /state-provinces : get all the stateProvinces.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stateProvinces in body
     */
    @GetMapping("/state-provinces")
    public List<StateProvincesDTO> getAllStateProvinces() {
        log.debug("REST request to get all StateProvinces");
        return stateProvincesService.findAll();
    }

    /**
     * GET  /state-provinces/:id : get the "id" stateProvinces.
     *
     * @param id the id of the stateProvincesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stateProvincesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/state-provinces/{id}")
    public ResponseEntity<StateProvincesDTO> getStateProvinces(@PathVariable Long id) {
        log.debug("REST request to get StateProvinces : {}", id);
        Optional<StateProvincesDTO> stateProvincesDTO = stateProvincesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stateProvincesDTO);
    }

    /**
     * DELETE  /state-provinces/:id : delete the "id" stateProvinces.
     *
     * @param id the id of the stateProvincesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/state-provinces/{id}")
    public ResponseEntity<Void> deleteStateProvinces(@PathVariable Long id) {
        log.debug("REST request to delete StateProvinces : {}", id);
        stateProvincesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
