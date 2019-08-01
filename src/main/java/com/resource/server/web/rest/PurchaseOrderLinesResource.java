package com.resource.server.web.rest;
import com.resource.server.service.PurchaseOrderLinesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.PurchaseOrderLinesDTO;
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
 * REST controller for managing PurchaseOrderLines.
 */
@RestController
@RequestMapping("/api")
public class PurchaseOrderLinesResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderLinesResource.class);

    private static final String ENTITY_NAME = "purchaseOrderLines";

    private final PurchaseOrderLinesService purchaseOrderLinesService;

    public PurchaseOrderLinesResource(PurchaseOrderLinesService purchaseOrderLinesService) {
        this.purchaseOrderLinesService = purchaseOrderLinesService;
    }

    /**
     * POST  /purchase-order-lines : Create a new purchaseOrderLines.
     *
     * @param purchaseOrderLinesDTO the purchaseOrderLinesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new purchaseOrderLinesDTO, or with status 400 (Bad Request) if the purchaseOrderLines has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchase-order-lines")
    public ResponseEntity<PurchaseOrderLinesDTO> createPurchaseOrderLines(@Valid @RequestBody PurchaseOrderLinesDTO purchaseOrderLinesDTO) throws URISyntaxException {
        log.debug("REST request to save PurchaseOrderLines : {}", purchaseOrderLinesDTO);
        if (purchaseOrderLinesDTO.getId() != null) {
            throw new BadRequestAlertException("A new purchaseOrderLines cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseOrderLinesDTO result = purchaseOrderLinesService.save(purchaseOrderLinesDTO);
        return ResponseEntity.created(new URI("/api/purchase-order-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchase-order-lines : Updates an existing purchaseOrderLines.
     *
     * @param purchaseOrderLinesDTO the purchaseOrderLinesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchaseOrderLinesDTO,
     * or with status 400 (Bad Request) if the purchaseOrderLinesDTO is not valid,
     * or with status 500 (Internal Server Error) if the purchaseOrderLinesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchase-order-lines")
    public ResponseEntity<PurchaseOrderLinesDTO> updatePurchaseOrderLines(@Valid @RequestBody PurchaseOrderLinesDTO purchaseOrderLinesDTO) throws URISyntaxException {
        log.debug("REST request to update PurchaseOrderLines : {}", purchaseOrderLinesDTO);
        if (purchaseOrderLinesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseOrderLinesDTO result = purchaseOrderLinesService.save(purchaseOrderLinesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, purchaseOrderLinesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchase-order-lines : get all the purchaseOrderLines.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of purchaseOrderLines in body
     */
    @GetMapping("/purchase-order-lines")
    public List<PurchaseOrderLinesDTO> getAllPurchaseOrderLines() {
        log.debug("REST request to get all PurchaseOrderLines");
        return purchaseOrderLinesService.findAll();
    }

    /**
     * GET  /purchase-order-lines/:id : get the "id" purchaseOrderLines.
     *
     * @param id the id of the purchaseOrderLinesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchaseOrderLinesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/purchase-order-lines/{id}")
    public ResponseEntity<PurchaseOrderLinesDTO> getPurchaseOrderLines(@PathVariable Long id) {
        log.debug("REST request to get PurchaseOrderLines : {}", id);
        Optional<PurchaseOrderLinesDTO> purchaseOrderLinesDTO = purchaseOrderLinesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseOrderLinesDTO);
    }

    /**
     * DELETE  /purchase-order-lines/:id : delete the "id" purchaseOrderLines.
     *
     * @param id the id of the purchaseOrderLinesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchase-order-lines/{id}")
    public ResponseEntity<Void> deletePurchaseOrderLines(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseOrderLines : {}", id);
        purchaseOrderLinesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
