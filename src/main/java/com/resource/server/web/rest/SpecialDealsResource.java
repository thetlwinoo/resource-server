package com.resource.server.web.rest;
import com.resource.server.service.SpecialDealsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.SpecialDealsDTO;
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
 * REST controller for managing SpecialDeals.
 */
@RestController
@RequestMapping("/api")
public class SpecialDealsResource {

    private final Logger log = LoggerFactory.getLogger(SpecialDealsResource.class);

    private static final String ENTITY_NAME = "specialDeals";

    private final SpecialDealsService specialDealsService;

    public SpecialDealsResource(SpecialDealsService specialDealsService) {
        this.specialDealsService = specialDealsService;
    }

    /**
     * POST  /special-deals : Create a new specialDeals.
     *
     * @param specialDealsDTO the specialDealsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new specialDealsDTO, or with status 400 (Bad Request) if the specialDeals has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/special-deals")
    public ResponseEntity<SpecialDealsDTO> createSpecialDeals(@Valid @RequestBody SpecialDealsDTO specialDealsDTO) throws URISyntaxException {
        log.debug("REST request to save SpecialDeals : {}", specialDealsDTO);
        if (specialDealsDTO.getId() != null) {
            throw new BadRequestAlertException("A new specialDeals cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpecialDealsDTO result = specialDealsService.save(specialDealsDTO);
        return ResponseEntity.created(new URI("/api/special-deals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /special-deals : Updates an existing specialDeals.
     *
     * @param specialDealsDTO the specialDealsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated specialDealsDTO,
     * or with status 400 (Bad Request) if the specialDealsDTO is not valid,
     * or with status 500 (Internal Server Error) if the specialDealsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/special-deals")
    public ResponseEntity<SpecialDealsDTO> updateSpecialDeals(@Valid @RequestBody SpecialDealsDTO specialDealsDTO) throws URISyntaxException {
        log.debug("REST request to update SpecialDeals : {}", specialDealsDTO);
        if (specialDealsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SpecialDealsDTO result = specialDealsService.save(specialDealsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, specialDealsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /special-deals : get all the specialDeals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of specialDeals in body
     */
    @GetMapping("/special-deals")
    public List<SpecialDealsDTO> getAllSpecialDeals() {
        log.debug("REST request to get all SpecialDeals");
        return specialDealsService.findAll();
    }

    /**
     * GET  /special-deals/:id : get the "id" specialDeals.
     *
     * @param id the id of the specialDealsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the specialDealsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/special-deals/{id}")
    public ResponseEntity<SpecialDealsDTO> getSpecialDeals(@PathVariable Long id) {
        log.debug("REST request to get SpecialDeals : {}", id);
        Optional<SpecialDealsDTO> specialDealsDTO = specialDealsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(specialDealsDTO);
    }

    /**
     * DELETE  /special-deals/:id : delete the "id" specialDeals.
     *
     * @param id the id of the specialDealsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/special-deals/{id}")
    public ResponseEntity<Void> deleteSpecialDeals(@PathVariable Long id) {
        log.debug("REST request to delete SpecialDeals : {}", id);
        specialDealsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
