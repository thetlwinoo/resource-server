package com.resource.server.web.rest;
import com.resource.server.service.ContactTypeService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ContactTypeDTO;
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
 * REST controller for managing ContactType.
 */
@RestController
@RequestMapping("/api")
public class ContactTypeResource {

    private final Logger log = LoggerFactory.getLogger(ContactTypeResource.class);

    private static final String ENTITY_NAME = "contactType";

    private final ContactTypeService contactTypeService;

    public ContactTypeResource(ContactTypeService contactTypeService) {
        this.contactTypeService = contactTypeService;
    }

    /**
     * POST  /contact-types : Create a new contactType.
     *
     * @param contactTypeDTO the contactTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactTypeDTO, or with status 400 (Bad Request) if the contactType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contact-types")
    public ResponseEntity<ContactTypeDTO> createContactType(@Valid @RequestBody ContactTypeDTO contactTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ContactType : {}", contactTypeDTO);
        if (contactTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactTypeDTO result = contactTypeService.save(contactTypeDTO);
        return ResponseEntity.created(new URI("/api/contact-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contact-types : Updates an existing contactType.
     *
     * @param contactTypeDTO the contactTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactTypeDTO,
     * or with status 400 (Bad Request) if the contactTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the contactTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contact-types")
    public ResponseEntity<ContactTypeDTO> updateContactType(@Valid @RequestBody ContactTypeDTO contactTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ContactType : {}", contactTypeDTO);
        if (contactTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactTypeDTO result = contactTypeService.save(contactTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contact-types : get all the contactTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contactTypes in body
     */
    @GetMapping("/contact-types")
    public List<ContactTypeDTO> getAllContactTypes() {
        log.debug("REST request to get all ContactTypes");
        return contactTypeService.findAll();
    }

    /**
     * GET  /contact-types/:id : get the "id" contactType.
     *
     * @param id the id of the contactTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contact-types/{id}")
    public ResponseEntity<ContactTypeDTO> getContactType(@PathVariable Long id) {
        log.debug("REST request to get ContactType : {}", id);
        Optional<ContactTypeDTO> contactTypeDTO = contactTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactTypeDTO);
    }

    /**
     * DELETE  /contact-types/:id : delete the "id" contactType.
     *
     * @param id the id of the contactTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contact-types/{id}")
    public ResponseEntity<Void> deleteContactType(@PathVariable Long id) {
        log.debug("REST request to delete ContactType : {}", id);
        contactTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
