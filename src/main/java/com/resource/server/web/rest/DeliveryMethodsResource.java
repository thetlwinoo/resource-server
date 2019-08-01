package com.resource.server.web.rest;
import com.resource.server.service.DeliveryMethodsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.DeliveryMethodsDTO;
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
 * REST controller for managing DeliveryMethods.
 */
@RestController
@RequestMapping("/api")
public class DeliveryMethodsResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryMethodsResource.class);

    private static final String ENTITY_NAME = "deliveryMethods";

    private final DeliveryMethodsService deliveryMethodsService;

    public DeliveryMethodsResource(DeliveryMethodsService deliveryMethodsService) {
        this.deliveryMethodsService = deliveryMethodsService;
    }

    /**
     * POST  /delivery-methods : Create a new deliveryMethods.
     *
     * @param deliveryMethodsDTO the deliveryMethodsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deliveryMethodsDTO, or with status 400 (Bad Request) if the deliveryMethods has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/delivery-methods")
    public ResponseEntity<DeliveryMethodsDTO> createDeliveryMethods(@Valid @RequestBody DeliveryMethodsDTO deliveryMethodsDTO) throws URISyntaxException {
        log.debug("REST request to save DeliveryMethods : {}", deliveryMethodsDTO);
        if (deliveryMethodsDTO.getId() != null) {
            throw new BadRequestAlertException("A new deliveryMethods cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeliveryMethodsDTO result = deliveryMethodsService.save(deliveryMethodsDTO);
        return ResponseEntity.created(new URI("/api/delivery-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /delivery-methods : Updates an existing deliveryMethods.
     *
     * @param deliveryMethodsDTO the deliveryMethodsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deliveryMethodsDTO,
     * or with status 400 (Bad Request) if the deliveryMethodsDTO is not valid,
     * or with status 500 (Internal Server Error) if the deliveryMethodsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/delivery-methods")
    public ResponseEntity<DeliveryMethodsDTO> updateDeliveryMethods(@Valid @RequestBody DeliveryMethodsDTO deliveryMethodsDTO) throws URISyntaxException {
        log.debug("REST request to update DeliveryMethods : {}", deliveryMethodsDTO);
        if (deliveryMethodsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeliveryMethodsDTO result = deliveryMethodsService.save(deliveryMethodsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deliveryMethodsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /delivery-methods : get all the deliveryMethods.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of deliveryMethods in body
     */
    @GetMapping("/delivery-methods")
    public List<DeliveryMethodsDTO> getAllDeliveryMethods() {
        log.debug("REST request to get all DeliveryMethods");
        return deliveryMethodsService.findAll();
    }

    /**
     * GET  /delivery-methods/:id : get the "id" deliveryMethods.
     *
     * @param id the id of the deliveryMethodsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deliveryMethodsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/delivery-methods/{id}")
    public ResponseEntity<DeliveryMethodsDTO> getDeliveryMethods(@PathVariable Long id) {
        log.debug("REST request to get DeliveryMethods : {}", id);
        Optional<DeliveryMethodsDTO> deliveryMethodsDTO = deliveryMethodsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deliveryMethodsDTO);
    }

    /**
     * DELETE  /delivery-methods/:id : delete the "id" deliveryMethods.
     *
     * @param id the id of the deliveryMethodsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/delivery-methods/{id}")
    public ResponseEntity<Void> deleteDeliveryMethods(@PathVariable Long id) {
        log.debug("REST request to delete DeliveryMethods : {}", id);
        deliveryMethodsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
