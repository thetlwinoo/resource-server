package com.resource.server.web.rest;
import com.resource.server.service.AddressTypesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.AddressTypesDTO;
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
 * REST controller for managing AddressTypes.
 */
@RestController
@RequestMapping("/api")
public class AddressTypesResource {

    private final Logger log = LoggerFactory.getLogger(AddressTypesResource.class);

    private static final String ENTITY_NAME = "addressTypes";

    private final AddressTypesService addressTypesService;

    public AddressTypesResource(AddressTypesService addressTypesService) {
        this.addressTypesService = addressTypesService;
    }

    /**
     * POST  /address-types : Create a new addressTypes.
     *
     * @param addressTypesDTO the addressTypesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new addressTypesDTO, or with status 400 (Bad Request) if the addressTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/address-types")
    public ResponseEntity<AddressTypesDTO> createAddressTypes(@Valid @RequestBody AddressTypesDTO addressTypesDTO) throws URISyntaxException {
        log.debug("REST request to save AddressTypes : {}", addressTypesDTO);
        if (addressTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new addressTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressTypesDTO result = addressTypesService.save(addressTypesDTO);
        return ResponseEntity.created(new URI("/api/address-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /address-types : Updates an existing addressTypes.
     *
     * @param addressTypesDTO the addressTypesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated addressTypesDTO,
     * or with status 400 (Bad Request) if the addressTypesDTO is not valid,
     * or with status 500 (Internal Server Error) if the addressTypesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/address-types")
    public ResponseEntity<AddressTypesDTO> updateAddressTypes(@Valid @RequestBody AddressTypesDTO addressTypesDTO) throws URISyntaxException {
        log.debug("REST request to update AddressTypes : {}", addressTypesDTO);
        if (addressTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AddressTypesDTO result = addressTypesService.save(addressTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, addressTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /address-types : get all the addressTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of addressTypes in body
     */
    @GetMapping("/address-types")
    public List<AddressTypesDTO> getAllAddressTypes() {
        log.debug("REST request to get all AddressTypes");
        return addressTypesService.findAll();
    }

    /**
     * GET  /address-types/:id : get the "id" addressTypes.
     *
     * @param id the id of the addressTypesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the addressTypesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/address-types/{id}")
    public ResponseEntity<AddressTypesDTO> getAddressTypes(@PathVariable Long id) {
        log.debug("REST request to get AddressTypes : {}", id);
        Optional<AddressTypesDTO> addressTypesDTO = addressTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressTypesDTO);
    }

    /**
     * DELETE  /address-types/:id : delete the "id" addressTypes.
     *
     * @param id the id of the addressTypesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/address-types/{id}")
    public ResponseEntity<Void> deleteAddressTypes(@PathVariable Long id) {
        log.debug("REST request to delete AddressTypes : {}", id);
        addressTypesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
