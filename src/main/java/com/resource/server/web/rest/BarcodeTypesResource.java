package com.resource.server.web.rest;
import com.resource.server.service.BarcodeTypesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.BarcodeTypesDTO;
import com.resource.server.service.dto.BarcodeTypesCriteria;
import com.resource.server.service.BarcodeTypesQueryService;
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
 * REST controller for managing BarcodeTypes.
 */
@RestController
@RequestMapping("/api")
public class BarcodeTypesResource {

    private final Logger log = LoggerFactory.getLogger(BarcodeTypesResource.class);

    private static final String ENTITY_NAME = "barcodeTypes";

    private final BarcodeTypesService barcodeTypesService;

    private final BarcodeTypesQueryService barcodeTypesQueryService;

    public BarcodeTypesResource(BarcodeTypesService barcodeTypesService, BarcodeTypesQueryService barcodeTypesQueryService) {
        this.barcodeTypesService = barcodeTypesService;
        this.barcodeTypesQueryService = barcodeTypesQueryService;
    }

    /**
     * POST  /barcode-types : Create a new barcodeTypes.
     *
     * @param barcodeTypesDTO the barcodeTypesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new barcodeTypesDTO, or with status 400 (Bad Request) if the barcodeTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/barcode-types")
    public ResponseEntity<BarcodeTypesDTO> createBarcodeTypes(@Valid @RequestBody BarcodeTypesDTO barcodeTypesDTO) throws URISyntaxException {
        log.debug("REST request to save BarcodeTypes : {}", barcodeTypesDTO);
        if (barcodeTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new barcodeTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BarcodeTypesDTO result = barcodeTypesService.save(barcodeTypesDTO);
        return ResponseEntity.created(new URI("/api/barcode-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /barcode-types : Updates an existing barcodeTypes.
     *
     * @param barcodeTypesDTO the barcodeTypesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated barcodeTypesDTO,
     * or with status 400 (Bad Request) if the barcodeTypesDTO is not valid,
     * or with status 500 (Internal Server Error) if the barcodeTypesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/barcode-types")
    public ResponseEntity<BarcodeTypesDTO> updateBarcodeTypes(@Valid @RequestBody BarcodeTypesDTO barcodeTypesDTO) throws URISyntaxException {
        log.debug("REST request to update BarcodeTypes : {}", barcodeTypesDTO);
        if (barcodeTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BarcodeTypesDTO result = barcodeTypesService.save(barcodeTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, barcodeTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /barcode-types : get all the barcodeTypes.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of barcodeTypes in body
     */
    @GetMapping("/barcode-types")
    public ResponseEntity<List<BarcodeTypesDTO>> getAllBarcodeTypes(BarcodeTypesCriteria criteria) {
        log.debug("REST request to get BarcodeTypes by criteria: {}", criteria);
        List<BarcodeTypesDTO> entityList = barcodeTypesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /barcode-types/count : count all the barcodeTypes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/barcode-types/count")
    public ResponseEntity<Long> countBarcodeTypes(BarcodeTypesCriteria criteria) {
        log.debug("REST request to count BarcodeTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(barcodeTypesQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /barcode-types/:id : get the "id" barcodeTypes.
     *
     * @param id the id of the barcodeTypesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the barcodeTypesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/barcode-types/{id}")
    public ResponseEntity<BarcodeTypesDTO> getBarcodeTypes(@PathVariable Long id) {
        log.debug("REST request to get BarcodeTypes : {}", id);
        Optional<BarcodeTypesDTO> barcodeTypesDTO = barcodeTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(barcodeTypesDTO);
    }

    /**
     * DELETE  /barcode-types/:id : delete the "id" barcodeTypes.
     *
     * @param id the id of the barcodeTypesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/barcode-types/{id}")
    public ResponseEntity<Void> deleteBarcodeTypes(@PathVariable Long id) {
        log.debug("REST request to delete BarcodeTypes : {}", id);
        barcodeTypesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
