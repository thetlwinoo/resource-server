package com.resource.server.web.rest;
import com.resource.server.service.AddressesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.AddressesDTO;
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
 * REST controller for managing Addresses.
 */
@RestController
@RequestMapping("/api")
public class AddressesResource {

    private final Logger log = LoggerFactory.getLogger(AddressesResource.class);

    private static final String ENTITY_NAME = "addresses";

    private final AddressesService addressesService;

    public AddressesResource(AddressesService addressesService) {
        this.addressesService = addressesService;
    }

    /**
     * POST  /addresses : Create a new addresses.
     *
     * @param addressesDTO the addressesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new addressesDTO, or with status 400 (Bad Request) if the addresses has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/addresses")
    public ResponseEntity<AddressesDTO> createAddresses(@Valid @RequestBody AddressesDTO addressesDTO) throws URISyntaxException {
        log.debug("REST request to save Addresses : {}", addressesDTO);
        if (addressesDTO.getId() != null) {
            throw new BadRequestAlertException("A new addresses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressesDTO result = addressesService.save(addressesDTO);
        return ResponseEntity.created(new URI("/api/addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /addresses : Updates an existing addresses.
     *
     * @param addressesDTO the addressesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated addressesDTO,
     * or with status 400 (Bad Request) if the addressesDTO is not valid,
     * or with status 500 (Internal Server Error) if the addressesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/addresses")
    public ResponseEntity<AddressesDTO> updateAddresses(@Valid @RequestBody AddressesDTO addressesDTO) throws URISyntaxException {
        log.debug("REST request to update Addresses : {}", addressesDTO);
        if (addressesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AddressesDTO result = addressesService.save(addressesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, addressesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /addresses : get all the addresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of addresses in body
     */
    @GetMapping("/addresses")
    public List<AddressesDTO> getAllAddresses() {
        log.debug("REST request to get all Addresses");
        return addressesService.findAll();
    }

    /**
     * GET  /addresses/:id : get the "id" addresses.
     *
     * @param id the id of the addressesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the addressesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/addresses/{id}")
    public ResponseEntity<AddressesDTO> getAddresses(@PathVariable Long id) {
        log.debug("REST request to get Addresses : {}", id);
        Optional<AddressesDTO> addressesDTO = addressesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressesDTO);
    }

    /**
     * DELETE  /addresses/:id : delete the "id" addresses.
     *
     * @param id the id of the addressesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Void> deleteAddresses(@PathVariable Long id) {
        log.debug("REST request to delete Addresses : {}", id);
        addressesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
