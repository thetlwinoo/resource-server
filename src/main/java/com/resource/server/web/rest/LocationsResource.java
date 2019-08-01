package com.resource.server.web.rest;
import com.resource.server.service.LocationsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.LocationsDTO;
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
 * REST controller for managing Locations.
 */
@RestController
@RequestMapping("/api")
public class LocationsResource {

    private final Logger log = LoggerFactory.getLogger(LocationsResource.class);

    private static final String ENTITY_NAME = "locations";

    private final LocationsService locationsService;

    public LocationsResource(LocationsService locationsService) {
        this.locationsService = locationsService;
    }

    /**
     * POST  /locations : Create a new locations.
     *
     * @param locationsDTO the locationsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new locationsDTO, or with status 400 (Bad Request) if the locations has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/locations")
    public ResponseEntity<LocationsDTO> createLocations(@Valid @RequestBody LocationsDTO locationsDTO) throws URISyntaxException {
        log.debug("REST request to save Locations : {}", locationsDTO);
        if (locationsDTO.getId() != null) {
            throw new BadRequestAlertException("A new locations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocationsDTO result = locationsService.save(locationsDTO);
        return ResponseEntity.created(new URI("/api/locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /locations : Updates an existing locations.
     *
     * @param locationsDTO the locationsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated locationsDTO,
     * or with status 400 (Bad Request) if the locationsDTO is not valid,
     * or with status 500 (Internal Server Error) if the locationsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/locations")
    public ResponseEntity<LocationsDTO> updateLocations(@Valid @RequestBody LocationsDTO locationsDTO) throws URISyntaxException {
        log.debug("REST request to update Locations : {}", locationsDTO);
        if (locationsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocationsDTO result = locationsService.save(locationsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, locationsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /locations : get all the locations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of locations in body
     */
    @GetMapping("/locations")
    public List<LocationsDTO> getAllLocations() {
        log.debug("REST request to get all Locations");
        return locationsService.findAll();
    }

    /**
     * GET  /locations/:id : get the "id" locations.
     *
     * @param id the id of the locationsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locationsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/locations/{id}")
    public ResponseEntity<LocationsDTO> getLocations(@PathVariable Long id) {
        log.debug("REST request to get Locations : {}", id);
        Optional<LocationsDTO> locationsDTO = locationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locationsDTO);
    }

    /**
     * DELETE  /locations/:id : delete the "id" locations.
     *
     * @param id the id of the locationsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/locations/{id}")
    public ResponseEntity<Void> deleteLocations(@PathVariable Long id) {
        log.debug("REST request to delete Locations : {}", id);
        locationsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
