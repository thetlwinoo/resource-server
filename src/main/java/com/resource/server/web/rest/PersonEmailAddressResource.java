package com.resource.server.web.rest;
import com.resource.server.service.PersonEmailAddressService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.PersonEmailAddressDTO;
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
 * REST controller for managing PersonEmailAddress.
 */
@RestController
@RequestMapping("/api")
public class PersonEmailAddressResource {

    private final Logger log = LoggerFactory.getLogger(PersonEmailAddressResource.class);

    private static final String ENTITY_NAME = "personEmailAddress";

    private final PersonEmailAddressService personEmailAddressService;

    public PersonEmailAddressResource(PersonEmailAddressService personEmailAddressService) {
        this.personEmailAddressService = personEmailAddressService;
    }

    /**
     * POST  /person-email-addresses : Create a new personEmailAddress.
     *
     * @param personEmailAddressDTO the personEmailAddressDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personEmailAddressDTO, or with status 400 (Bad Request) if the personEmailAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-email-addresses")
    public ResponseEntity<PersonEmailAddressDTO> createPersonEmailAddress(@Valid @RequestBody PersonEmailAddressDTO personEmailAddressDTO) throws URISyntaxException {
        log.debug("REST request to save PersonEmailAddress : {}", personEmailAddressDTO);
        if (personEmailAddressDTO.getId() != null) {
            throw new BadRequestAlertException("A new personEmailAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonEmailAddressDTO result = personEmailAddressService.save(personEmailAddressDTO);
        return ResponseEntity.created(new URI("/api/person-email-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-email-addresses : Updates an existing personEmailAddress.
     *
     * @param personEmailAddressDTO the personEmailAddressDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personEmailAddressDTO,
     * or with status 400 (Bad Request) if the personEmailAddressDTO is not valid,
     * or with status 500 (Internal Server Error) if the personEmailAddressDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-email-addresses")
    public ResponseEntity<PersonEmailAddressDTO> updatePersonEmailAddress(@Valid @RequestBody PersonEmailAddressDTO personEmailAddressDTO) throws URISyntaxException {
        log.debug("REST request to update PersonEmailAddress : {}", personEmailAddressDTO);
        if (personEmailAddressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonEmailAddressDTO result = personEmailAddressService.save(personEmailAddressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personEmailAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-email-addresses : get all the personEmailAddresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personEmailAddresses in body
     */
    @GetMapping("/person-email-addresses")
    public List<PersonEmailAddressDTO> getAllPersonEmailAddresses() {
        log.debug("REST request to get all PersonEmailAddresses");
        return personEmailAddressService.findAll();
    }

    /**
     * GET  /person-email-addresses/:id : get the "id" personEmailAddress.
     *
     * @param id the id of the personEmailAddressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personEmailAddressDTO, or with status 404 (Not Found)
     */
    @GetMapping("/person-email-addresses/{id}")
    public ResponseEntity<PersonEmailAddressDTO> getPersonEmailAddress(@PathVariable Long id) {
        log.debug("REST request to get PersonEmailAddress : {}", id);
        Optional<PersonEmailAddressDTO> personEmailAddressDTO = personEmailAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personEmailAddressDTO);
    }

    /**
     * DELETE  /person-email-addresses/:id : delete the "id" personEmailAddress.
     *
     * @param id the id of the personEmailAddressDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-email-addresses/{id}")
    public ResponseEntity<Void> deletePersonEmailAddress(@PathVariable Long id) {
        log.debug("REST request to delete PersonEmailAddress : {}", id);
        personEmailAddressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
