package com.resource.server.web.rest;
import com.resource.server.service.CultureService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.CultureDTO;
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
 * REST controller for managing Culture.
 */
@RestController
@RequestMapping("/api")
public class CultureResource {

    private final Logger log = LoggerFactory.getLogger(CultureResource.class);

    private static final String ENTITY_NAME = "culture";

    private final CultureService cultureService;

    public CultureResource(CultureService cultureService) {
        this.cultureService = cultureService;
    }

    /**
     * POST  /cultures : Create a new culture.
     *
     * @param cultureDTO the cultureDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cultureDTO, or with status 400 (Bad Request) if the culture has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cultures")
    public ResponseEntity<CultureDTO> createCulture(@Valid @RequestBody CultureDTO cultureDTO) throws URISyntaxException {
        log.debug("REST request to save Culture : {}", cultureDTO);
        if (cultureDTO.getId() != null) {
            throw new BadRequestAlertException("A new culture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CultureDTO result = cultureService.save(cultureDTO);
        return ResponseEntity.created(new URI("/api/cultures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cultures : Updates an existing culture.
     *
     * @param cultureDTO the cultureDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cultureDTO,
     * or with status 400 (Bad Request) if the cultureDTO is not valid,
     * or with status 500 (Internal Server Error) if the cultureDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cultures")
    public ResponseEntity<CultureDTO> updateCulture(@Valid @RequestBody CultureDTO cultureDTO) throws URISyntaxException {
        log.debug("REST request to update Culture : {}", cultureDTO);
        if (cultureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CultureDTO result = cultureService.save(cultureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cultureDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cultures : get all the cultures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cultures in body
     */
    @GetMapping("/cultures")
    public List<CultureDTO> getAllCultures() {
        log.debug("REST request to get all Cultures");
        return cultureService.findAll();
    }

    /**
     * GET  /cultures/:id : get the "id" culture.
     *
     * @param id the id of the cultureDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cultureDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cultures/{id}")
    public ResponseEntity<CultureDTO> getCulture(@PathVariable Long id) {
        log.debug("REST request to get Culture : {}", id);
        Optional<CultureDTO> cultureDTO = cultureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cultureDTO);
    }

    /**
     * DELETE  /cultures/:id : delete the "id" culture.
     *
     * @param id the id of the cultureDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cultures/{id}")
    public ResponseEntity<Void> deleteCulture(@PathVariable Long id) {
        log.debug("REST request to delete Culture : {}", id);
        cultureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
