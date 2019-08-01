package com.resource.server.web.rest;
import com.resource.server.service.ShipMethodService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ShipMethodDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ShipMethod.
 */
@RestController
@RequestMapping("/api")
public class ShipMethodResource {

    private final Logger log = LoggerFactory.getLogger(ShipMethodResource.class);

    private static final String ENTITY_NAME = "shipMethod";

    private final ShipMethodService shipMethodService;

    public ShipMethodResource(ShipMethodService shipMethodService) {
        this.shipMethodService = shipMethodService;
    }

    /**
     * POST  /ship-methods : Create a new shipMethod.
     *
     * @param shipMethodDTO the shipMethodDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipMethodDTO, or with status 400 (Bad Request) if the shipMethod has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ship-methods")
    public ResponseEntity<ShipMethodDTO> createShipMethod(@RequestBody ShipMethodDTO shipMethodDTO) throws URISyntaxException {
        log.debug("REST request to save ShipMethod : {}", shipMethodDTO);
        if (shipMethodDTO.getId() != null) {
            throw new BadRequestAlertException("A new shipMethod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShipMethodDTO result = shipMethodService.save(shipMethodDTO);
        return ResponseEntity.created(new URI("/api/ship-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ship-methods : Updates an existing shipMethod.
     *
     * @param shipMethodDTO the shipMethodDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipMethodDTO,
     * or with status 400 (Bad Request) if the shipMethodDTO is not valid,
     * or with status 500 (Internal Server Error) if the shipMethodDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ship-methods")
    public ResponseEntity<ShipMethodDTO> updateShipMethod(@RequestBody ShipMethodDTO shipMethodDTO) throws URISyntaxException {
        log.debug("REST request to update ShipMethod : {}", shipMethodDTO);
        if (shipMethodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShipMethodDTO result = shipMethodService.save(shipMethodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipMethodDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ship-methods : get all the shipMethods.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shipMethods in body
     */
    @GetMapping("/ship-methods")
    public List<ShipMethodDTO> getAllShipMethods() {
        log.debug("REST request to get all ShipMethods");
        return shipMethodService.findAll();
    }

    /**
     * GET  /ship-methods/:id : get the "id" shipMethod.
     *
     * @param id the id of the shipMethodDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipMethodDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ship-methods/{id}")
    public ResponseEntity<ShipMethodDTO> getShipMethod(@PathVariable Long id) {
        log.debug("REST request to get ShipMethod : {}", id);
        Optional<ShipMethodDTO> shipMethodDTO = shipMethodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipMethodDTO);
    }

    /**
     * DELETE  /ship-methods/:id : delete the "id" shipMethod.
     *
     * @param id the id of the shipMethodDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ship-methods/{id}")
    public ResponseEntity<Void> deleteShipMethod(@PathVariable Long id) {
        log.debug("REST request to delete ShipMethod : {}", id);
        shipMethodService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
