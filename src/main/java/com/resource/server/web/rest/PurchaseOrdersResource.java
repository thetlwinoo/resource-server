package com.resource.server.web.rest;
import com.resource.server.service.PurchaseOrdersService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.PurchaseOrdersDTO;
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
 * REST controller for managing PurchaseOrders.
 */
@RestController
@RequestMapping("/api")
public class PurchaseOrdersResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrdersResource.class);

    private static final String ENTITY_NAME = "purchaseOrders";

    private final PurchaseOrdersService purchaseOrdersService;

    public PurchaseOrdersResource(PurchaseOrdersService purchaseOrdersService) {
        this.purchaseOrdersService = purchaseOrdersService;
    }

    /**
     * POST  /purchase-orders : Create a new purchaseOrders.
     *
     * @param purchaseOrdersDTO the purchaseOrdersDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new purchaseOrdersDTO, or with status 400 (Bad Request) if the purchaseOrders has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchase-orders")
    public ResponseEntity<PurchaseOrdersDTO> createPurchaseOrders(@Valid @RequestBody PurchaseOrdersDTO purchaseOrdersDTO) throws URISyntaxException {
        log.debug("REST request to save PurchaseOrders : {}", purchaseOrdersDTO);
        if (purchaseOrdersDTO.getId() != null) {
            throw new BadRequestAlertException("A new purchaseOrders cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseOrdersDTO result = purchaseOrdersService.save(purchaseOrdersDTO);
        return ResponseEntity.created(new URI("/api/purchase-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchase-orders : Updates an existing purchaseOrders.
     *
     * @param purchaseOrdersDTO the purchaseOrdersDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchaseOrdersDTO,
     * or with status 400 (Bad Request) if the purchaseOrdersDTO is not valid,
     * or with status 500 (Internal Server Error) if the purchaseOrdersDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchase-orders")
    public ResponseEntity<PurchaseOrdersDTO> updatePurchaseOrders(@Valid @RequestBody PurchaseOrdersDTO purchaseOrdersDTO) throws URISyntaxException {
        log.debug("REST request to update PurchaseOrders : {}", purchaseOrdersDTO);
        if (purchaseOrdersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseOrdersDTO result = purchaseOrdersService.save(purchaseOrdersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, purchaseOrdersDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchase-orders : get all the purchaseOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of purchaseOrders in body
     */
    @GetMapping("/purchase-orders")
    public List<PurchaseOrdersDTO> getAllPurchaseOrders() {
        log.debug("REST request to get all PurchaseOrders");
        return purchaseOrdersService.findAll();
    }

    /**
     * GET  /purchase-orders/:id : get the "id" purchaseOrders.
     *
     * @param id the id of the purchaseOrdersDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchaseOrdersDTO, or with status 404 (Not Found)
     */
    @GetMapping("/purchase-orders/{id}")
    public ResponseEntity<PurchaseOrdersDTO> getPurchaseOrders(@PathVariable Long id) {
        log.debug("REST request to get PurchaseOrders : {}", id);
        Optional<PurchaseOrdersDTO> purchaseOrdersDTO = purchaseOrdersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseOrdersDTO);
    }

    /**
     * DELETE  /purchase-orders/:id : delete the "id" purchaseOrders.
     *
     * @param id the id of the purchaseOrdersDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchase-orders/{id}")
    public ResponseEntity<Void> deletePurchaseOrders(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseOrders : {}", id);
        purchaseOrdersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
