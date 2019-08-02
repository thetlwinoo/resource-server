package com.resource.server.web.rest;

import com.resource.server.domain.Addresses;
import com.resource.server.service.AddressesExtendService;
import com.resource.server.service.AddressesService;
import com.resource.server.service.dto.AddressesDTO;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * AddressesExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class AddressesExtendResource {

    private static final String ENTITY_NAME = "addresses-extend";
    private final Logger log = LoggerFactory.getLogger(AddressesExtendResource.class);
    private final AddressesExtendService addressesExtendService;
    private final AddressesService addressesService;

    public AddressesExtendResource(AddressesExtendService addressesExtendService, AddressesService addressesService) {
        this.addressesExtendService = addressesExtendService;
        this.addressesService = addressesService;
    }

    @RequestMapping(value = "/addresses-extend/fetch", method = RequestMethod.GET)
    public ResponseEntity fetch(Principal principal) {
        List<Addresses> addressesList = addressesExtendService.fetchAddresses(principal);
        return new ResponseEntity<List<Addresses>>(addressesList, HttpStatus.OK);
    }

    @RequestMapping(value = "/addresses-extend/setdefault", method = RequestMethod.POST)
    public ResponseEntity setDefaultAddress(@RequestBody Long addressId, Principal principal) {
        addressesExtendService.setDefaultAddress(principal, addressId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/addresses-extend/clear", method = RequestMethod.POST)
    public ResponseEntity clearDefaultAddress(Principal principal) {
        addressesExtendService.clearDefaultAddress(principal);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/addresses-extend")
    public ResponseEntity<AddressesDTO> createAddresses(@Valid @RequestBody AddressesDTO addressesDTO, Principal principal) throws URISyntaxException {
        log.debug("REST request to save Addresses : {}", addressesDTO);
        if (addressesDTO.getId() != null) {
            throw new BadRequestAlertException("A new addresses cannot already have an ID", ENTITY_NAME, "idexists");
        }

        AddressesDTO result = this.addressesExtendService.crateAddresses(addressesDTO,principal);
        return ResponseEntity.created(new URI("/api/addresses-extend/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping(value = "/addresses-extend")
    public ResponseEntity<AddressesDTO> updateAddresses(@Valid @RequestBody AddressesDTO addressesDTO, Principal principal) throws URISyntaxException {
        log.debug("REST request to update Addresses : {}", addressesDTO);
        if (addressesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        AddressesDTO result = this.addressesExtendService.updateAddresses(addressesDTO,principal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, addressesDTO.getId().toString()))
            .body(result);
    }

    @GetMapping(value = "/addresses-extend")
    public List<AddressesDTO> getAllAddresses() {
        log.debug("REST request to get all Addresses");
        return addressesService.findAll();
    }

    @GetMapping("/addresses-extend/{id}")
    public ResponseEntity<AddressesDTO> getAddresses(@PathVariable Long id) {
        log.debug("REST request to get Addresses : {}", id);
        Optional<AddressesDTO> addressesDTO = addressesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressesDTO);
    }

}
