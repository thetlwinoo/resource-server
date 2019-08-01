package com.resource.server.web.rest;
import com.resource.server.service.CitiesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.CitiesDTO;
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
 * REST controller for managing Cities.
 */
@RestController
@RequestMapping("/api")
public class CitiesResource {

    private final Logger log = LoggerFactory.getLogger(CitiesResource.class);

    private static final String ENTITY_NAME = "cities";

    private final CitiesService citiesService;

    public CitiesResource(CitiesService citiesService) {
        this.citiesService = citiesService;
    }

    /**
     * POST  /cities : Create a new cities.
     *
     * @param citiesDTO the citiesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new citiesDTO, or with status 400 (Bad Request) if the cities has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cities")
    public ResponseEntity<CitiesDTO> createCities(@Valid @RequestBody CitiesDTO citiesDTO) throws URISyntaxException {
        log.debug("REST request to save Cities : {}", citiesDTO);
        if (citiesDTO.getId() != null) {
            throw new BadRequestAlertException("A new cities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CitiesDTO result = citiesService.save(citiesDTO);
        return ResponseEntity.created(new URI("/api/cities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cities : Updates an existing cities.
     *
     * @param citiesDTO the citiesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated citiesDTO,
     * or with status 400 (Bad Request) if the citiesDTO is not valid,
     * or with status 500 (Internal Server Error) if the citiesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cities")
    public ResponseEntity<CitiesDTO> updateCities(@Valid @RequestBody CitiesDTO citiesDTO) throws URISyntaxException {
        log.debug("REST request to update Cities : {}", citiesDTO);
        if (citiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CitiesDTO result = citiesService.save(citiesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, citiesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cities : get all the cities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cities in body
     */
    @GetMapping("/cities")
    public List<CitiesDTO> getAllCities() {
        log.debug("REST request to get all Cities");
        return citiesService.findAll();
    }

    /**
     * GET  /cities/:id : get the "id" cities.
     *
     * @param id the id of the citiesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the citiesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cities/{id}")
    public ResponseEntity<CitiesDTO> getCities(@PathVariable Long id) {
        log.debug("REST request to get Cities : {}", id);
        Optional<CitiesDTO> citiesDTO = citiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(citiesDTO);
    }

    /**
     * DELETE  /cities/:id : delete the "id" cities.
     *
     * @param id the id of the citiesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cities/{id}")
    public ResponseEntity<Void> deleteCities(@PathVariable Long id) {
        log.debug("REST request to delete Cities : {}", id);
        citiesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
