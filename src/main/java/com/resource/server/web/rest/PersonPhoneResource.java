package com.resource.server.web.rest;
import com.resource.server.service.PersonPhoneService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.PersonPhoneDTO;
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
 * REST controller for managing PersonPhone.
 */
@RestController
@RequestMapping("/api")
public class PersonPhoneResource {

    private final Logger log = LoggerFactory.getLogger(PersonPhoneResource.class);

    private static final String ENTITY_NAME = "personPhone";

    private final PersonPhoneService personPhoneService;

    public PersonPhoneResource(PersonPhoneService personPhoneService) {
        this.personPhoneService = personPhoneService;
    }

    /**
     * POST  /person-phones : Create a new personPhone.
     *
     * @param personPhoneDTO the personPhoneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personPhoneDTO, or with status 400 (Bad Request) if the personPhone has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-phones")
    public ResponseEntity<PersonPhoneDTO> createPersonPhone(@Valid @RequestBody PersonPhoneDTO personPhoneDTO) throws URISyntaxException {
        log.debug("REST request to save PersonPhone : {}", personPhoneDTO);
        if (personPhoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new personPhone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonPhoneDTO result = personPhoneService.save(personPhoneDTO);
        return ResponseEntity.created(new URI("/api/person-phones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-phones : Updates an existing personPhone.
     *
     * @param personPhoneDTO the personPhoneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personPhoneDTO,
     * or with status 400 (Bad Request) if the personPhoneDTO is not valid,
     * or with status 500 (Internal Server Error) if the personPhoneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-phones")
    public ResponseEntity<PersonPhoneDTO> updatePersonPhone(@Valid @RequestBody PersonPhoneDTO personPhoneDTO) throws URISyntaxException {
        log.debug("REST request to update PersonPhone : {}", personPhoneDTO);
        if (personPhoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonPhoneDTO result = personPhoneService.save(personPhoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personPhoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-phones : get all the personPhones.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personPhones in body
     */
    @GetMapping("/person-phones")
    public List<PersonPhoneDTO> getAllPersonPhones() {
        log.debug("REST request to get all PersonPhones");
        return personPhoneService.findAll();
    }

    /**
     * GET  /person-phones/:id : get the "id" personPhone.
     *
     * @param id the id of the personPhoneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personPhoneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/person-phones/{id}")
    public ResponseEntity<PersonPhoneDTO> getPersonPhone(@PathVariable Long id) {
        log.debug("REST request to get PersonPhone : {}", id);
        Optional<PersonPhoneDTO> personPhoneDTO = personPhoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personPhoneDTO);
    }

    /**
     * DELETE  /person-phones/:id : delete the "id" personPhone.
     *
     * @param id the id of the personPhoneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-phones/{id}")
    public ResponseEntity<Void> deletePersonPhone(@PathVariable Long id) {
        log.debug("REST request to delete PersonPhone : {}", id);
        personPhoneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
