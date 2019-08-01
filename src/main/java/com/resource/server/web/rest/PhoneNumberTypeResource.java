package com.resource.server.web.rest;
import com.resource.server.service.PhoneNumberTypeService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.PhoneNumberTypeDTO;
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
 * REST controller for managing PhoneNumberType.
 */
@RestController
@RequestMapping("/api")
public class PhoneNumberTypeResource {

    private final Logger log = LoggerFactory.getLogger(PhoneNumberTypeResource.class);

    private static final String ENTITY_NAME = "phoneNumberType";

    private final PhoneNumberTypeService phoneNumberTypeService;

    public PhoneNumberTypeResource(PhoneNumberTypeService phoneNumberTypeService) {
        this.phoneNumberTypeService = phoneNumberTypeService;
    }

    /**
     * POST  /phone-number-types : Create a new phoneNumberType.
     *
     * @param phoneNumberTypeDTO the phoneNumberTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new phoneNumberTypeDTO, or with status 400 (Bad Request) if the phoneNumberType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/phone-number-types")
    public ResponseEntity<PhoneNumberTypeDTO> createPhoneNumberType(@Valid @RequestBody PhoneNumberTypeDTO phoneNumberTypeDTO) throws URISyntaxException {
        log.debug("REST request to save PhoneNumberType : {}", phoneNumberTypeDTO);
        if (phoneNumberTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new phoneNumberType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhoneNumberTypeDTO result = phoneNumberTypeService.save(phoneNumberTypeDTO);
        return ResponseEntity.created(new URI("/api/phone-number-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /phone-number-types : Updates an existing phoneNumberType.
     *
     * @param phoneNumberTypeDTO the phoneNumberTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated phoneNumberTypeDTO,
     * or with status 400 (Bad Request) if the phoneNumberTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the phoneNumberTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/phone-number-types")
    public ResponseEntity<PhoneNumberTypeDTO> updatePhoneNumberType(@Valid @RequestBody PhoneNumberTypeDTO phoneNumberTypeDTO) throws URISyntaxException {
        log.debug("REST request to update PhoneNumberType : {}", phoneNumberTypeDTO);
        if (phoneNumberTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhoneNumberTypeDTO result = phoneNumberTypeService.save(phoneNumberTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, phoneNumberTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /phone-number-types : get all the phoneNumberTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of phoneNumberTypes in body
     */
    @GetMapping("/phone-number-types")
    public List<PhoneNumberTypeDTO> getAllPhoneNumberTypes() {
        log.debug("REST request to get all PhoneNumberTypes");
        return phoneNumberTypeService.findAll();
    }

    /**
     * GET  /phone-number-types/:id : get the "id" phoneNumberType.
     *
     * @param id the id of the phoneNumberTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the phoneNumberTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/phone-number-types/{id}")
    public ResponseEntity<PhoneNumberTypeDTO> getPhoneNumberType(@PathVariable Long id) {
        log.debug("REST request to get PhoneNumberType : {}", id);
        Optional<PhoneNumberTypeDTO> phoneNumberTypeDTO = phoneNumberTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phoneNumberTypeDTO);
    }

    /**
     * DELETE  /phone-number-types/:id : delete the "id" phoneNumberType.
     *
     * @param id the id of the phoneNumberTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/phone-number-types/{id}")
    public ResponseEntity<Void> deletePhoneNumberType(@PathVariable Long id) {
        log.debug("REST request to delete PhoneNumberType : {}", id);
        phoneNumberTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
