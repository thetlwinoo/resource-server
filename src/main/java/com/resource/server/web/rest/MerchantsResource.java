package com.resource.server.web.rest;
import com.resource.server.service.MerchantsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.MerchantsDTO;
import com.resource.server.service.dto.MerchantsCriteria;
import com.resource.server.service.MerchantsQueryService;
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
 * REST controller for managing Merchants.
 */
@RestController
@RequestMapping("/api")
public class MerchantsResource {

    private final Logger log = LoggerFactory.getLogger(MerchantsResource.class);

    private static final String ENTITY_NAME = "merchants";

    private final MerchantsService merchantsService;

    private final MerchantsQueryService merchantsQueryService;

    public MerchantsResource(MerchantsService merchantsService, MerchantsQueryService merchantsQueryService) {
        this.merchantsService = merchantsService;
        this.merchantsQueryService = merchantsQueryService;
    }

    /**
     * POST  /merchants : Create a new merchants.
     *
     * @param merchantsDTO the merchantsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new merchantsDTO, or with status 400 (Bad Request) if the merchants has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/merchants")
    public ResponseEntity<MerchantsDTO> createMerchants(@Valid @RequestBody MerchantsDTO merchantsDTO) throws URISyntaxException {
        log.debug("REST request to save Merchants : {}", merchantsDTO);
        if (merchantsDTO.getId() != null) {
            throw new BadRequestAlertException("A new merchants cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MerchantsDTO result = merchantsService.save(merchantsDTO);
        return ResponseEntity.created(new URI("/api/merchants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /merchants : Updates an existing merchants.
     *
     * @param merchantsDTO the merchantsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated merchantsDTO,
     * or with status 400 (Bad Request) if the merchantsDTO is not valid,
     * or with status 500 (Internal Server Error) if the merchantsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/merchants")
    public ResponseEntity<MerchantsDTO> updateMerchants(@Valid @RequestBody MerchantsDTO merchantsDTO) throws URISyntaxException {
        log.debug("REST request to update Merchants : {}", merchantsDTO);
        if (merchantsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MerchantsDTO result = merchantsService.save(merchantsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, merchantsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /merchants : get all the merchants.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of merchants in body
     */
    @GetMapping("/merchants")
    public ResponseEntity<List<MerchantsDTO>> getAllMerchants(MerchantsCriteria criteria) {
        log.debug("REST request to get Merchants by criteria: {}", criteria);
        List<MerchantsDTO> entityList = merchantsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /merchants/count : count all the merchants.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/merchants/count")
    public ResponseEntity<Long> countMerchants(MerchantsCriteria criteria) {
        log.debug("REST request to count Merchants by criteria: {}", criteria);
        return ResponseEntity.ok().body(merchantsQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /merchants/:id : get the "id" merchants.
     *
     * @param id the id of the merchantsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the merchantsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/merchants/{id}")
    public ResponseEntity<MerchantsDTO> getMerchants(@PathVariable Long id) {
        log.debug("REST request to get Merchants : {}", id);
        Optional<MerchantsDTO> merchantsDTO = merchantsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(merchantsDTO);
    }

    /**
     * DELETE  /merchants/:id : delete the "id" merchants.
     *
     * @param id the id of the merchantsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/merchants/{id}")
    public ResponseEntity<Void> deleteMerchants(@PathVariable Long id) {
        log.debug("REST request to delete Merchants : {}", id);
        merchantsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
