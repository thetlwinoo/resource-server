package com.resource.server.web.rest;
import com.resource.server.service.ComparesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ComparesDTO;
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
 * REST controller for managing Compares.
 */
@RestController
@RequestMapping("/api")
public class ComparesResource {

    private final Logger log = LoggerFactory.getLogger(ComparesResource.class);

    private static final String ENTITY_NAME = "compares";

    private final ComparesService comparesService;

    public ComparesResource(ComparesService comparesService) {
        this.comparesService = comparesService;
    }

    /**
     * POST  /compares : Create a new compares.
     *
     * @param comparesDTO the comparesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comparesDTO, or with status 400 (Bad Request) if the compares has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compares")
    public ResponseEntity<ComparesDTO> createCompares(@RequestBody ComparesDTO comparesDTO) throws URISyntaxException {
        log.debug("REST request to save Compares : {}", comparesDTO);
        if (comparesDTO.getId() != null) {
            throw new BadRequestAlertException("A new compares cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComparesDTO result = comparesService.save(comparesDTO);
        return ResponseEntity.created(new URI("/api/compares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /compares : Updates an existing compares.
     *
     * @param comparesDTO the comparesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comparesDTO,
     * or with status 400 (Bad Request) if the comparesDTO is not valid,
     * or with status 500 (Internal Server Error) if the comparesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compares")
    public ResponseEntity<ComparesDTO> updateCompares(@RequestBody ComparesDTO comparesDTO) throws URISyntaxException {
        log.debug("REST request to update Compares : {}", comparesDTO);
        if (comparesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComparesDTO result = comparesService.save(comparesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, comparesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /compares : get all the compares.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of compares in body
     */
    @GetMapping("/compares")
    public List<ComparesDTO> getAllCompares() {
        log.debug("REST request to get all Compares");
        return comparesService.findAll();
    }

    /**
     * GET  /compares/:id : get the "id" compares.
     *
     * @param id the id of the comparesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comparesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/compares/{id}")
    public ResponseEntity<ComparesDTO> getCompares(@PathVariable Long id) {
        log.debug("REST request to get Compares : {}", id);
        Optional<ComparesDTO> comparesDTO = comparesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comparesDTO);
    }

    /**
     * DELETE  /compares/:id : delete the "id" compares.
     *
     * @param id the id of the comparesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/compares/{id}")
    public ResponseEntity<Void> deleteCompares(@PathVariable Long id) {
        log.debug("REST request to delete Compares : {}", id);
        comparesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
