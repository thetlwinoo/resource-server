package com.resource.server.web.rest;
import com.resource.server.service.PeopleService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.PeopleDTO;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing People.
 */
@RestController
@RequestMapping("/api")
public class PeopleResource {

    private final Logger log = LoggerFactory.getLogger(PeopleResource.class);

    private static final String ENTITY_NAME = "people";

    private final PeopleService peopleService;

    public PeopleResource(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    /**
     * POST  /people : Create a new people.
     *
     * @param peopleDTO the peopleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new peopleDTO, or with status 400 (Bad Request) if the people has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/people")
    public ResponseEntity<PeopleDTO> createPeople(@Valid @RequestBody PeopleDTO peopleDTO) throws URISyntaxException {
        log.debug("REST request to save People : {}", peopleDTO);
        if (peopleDTO.getId() != null) {
            throw new BadRequestAlertException("A new people cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeopleDTO result = peopleService.save(peopleDTO);
        return ResponseEntity.created(new URI("/api/people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /people : Updates an existing people.
     *
     * @param peopleDTO the peopleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated peopleDTO,
     * or with status 400 (Bad Request) if the peopleDTO is not valid,
     * or with status 500 (Internal Server Error) if the peopleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/people")
    public ResponseEntity<PeopleDTO> updatePeople(@Valid @RequestBody PeopleDTO peopleDTO) throws URISyntaxException {
        log.debug("REST request to update People : {}", peopleDTO);
        if (peopleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeopleDTO result = peopleService.save(peopleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, peopleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /people : get all the people.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of people in body
     */
    @GetMapping("/people")
    public List<PeopleDTO> getAllPeople(@RequestParam(required = false) String filter) {
        if ("cart-is-null".equals(filter)) {
            log.debug("REST request to get all Peoples where cart is null");
            return peopleService.findAllWhereCartIsNull();
        }
        if ("wishlist-is-null".equals(filter)) {
            log.debug("REST request to get all Peoples where wishlist is null");
            return peopleService.findAllWhereWishlistIsNull();
        }
        if ("compare-is-null".equals(filter)) {
            log.debug("REST request to get all Peoples where compare is null");
            return peopleService.findAllWhereCompareIsNull();
        }
        log.debug("REST request to get all People");
        return peopleService.findAll();
    }

    /**
     * GET  /people/:id : get the "id" people.
     *
     * @param id the id of the peopleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the peopleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/people/{id}")
    public ResponseEntity<PeopleDTO> getPeople(@PathVariable Long id) {
        log.debug("REST request to get People : {}", id);
        Optional<PeopleDTO> peopleDTO = peopleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(peopleDTO);
    }

    /**
     * DELETE  /people/:id : delete the "id" people.
     *
     * @param id the id of the peopleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/people/{id}")
    public ResponseEntity<Void> deletePeople(@PathVariable Long id) {
        log.debug("REST request to delete People : {}", id);
        peopleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
