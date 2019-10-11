package com.resource.server.web.rest;
import com.resource.server.service.DangerousGoodsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.DangerousGoodsDTO;
import com.resource.server.service.dto.DangerousGoodsCriteria;
import com.resource.server.service.DangerousGoodsQueryService;
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
 * REST controller for managing DangerousGoods.
 */
@RestController
@RequestMapping("/api")
public class DangerousGoodsResource {

    private final Logger log = LoggerFactory.getLogger(DangerousGoodsResource.class);

    private static final String ENTITY_NAME = "dangerousGoods";

    private final DangerousGoodsService dangerousGoodsService;

    private final DangerousGoodsQueryService dangerousGoodsQueryService;

    public DangerousGoodsResource(DangerousGoodsService dangerousGoodsService, DangerousGoodsQueryService dangerousGoodsQueryService) {
        this.dangerousGoodsService = dangerousGoodsService;
        this.dangerousGoodsQueryService = dangerousGoodsQueryService;
    }

    /**
     * POST  /dangerous-goods : Create a new dangerousGoods.
     *
     * @param dangerousGoodsDTO the dangerousGoodsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dangerousGoodsDTO, or with status 400 (Bad Request) if the dangerousGoods has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dangerous-goods")
    public ResponseEntity<DangerousGoodsDTO> createDangerousGoods(@Valid @RequestBody DangerousGoodsDTO dangerousGoodsDTO) throws URISyntaxException {
        log.debug("REST request to save DangerousGoods : {}", dangerousGoodsDTO);
        if (dangerousGoodsDTO.getId() != null) {
            throw new BadRequestAlertException("A new dangerousGoods cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DangerousGoodsDTO result = dangerousGoodsService.save(dangerousGoodsDTO);
        return ResponseEntity.created(new URI("/api/dangerous-goods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dangerous-goods : Updates an existing dangerousGoods.
     *
     * @param dangerousGoodsDTO the dangerousGoodsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dangerousGoodsDTO,
     * or with status 400 (Bad Request) if the dangerousGoodsDTO is not valid,
     * or with status 500 (Internal Server Error) if the dangerousGoodsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dangerous-goods")
    public ResponseEntity<DangerousGoodsDTO> updateDangerousGoods(@Valid @RequestBody DangerousGoodsDTO dangerousGoodsDTO) throws URISyntaxException {
        log.debug("REST request to update DangerousGoods : {}", dangerousGoodsDTO);
        if (dangerousGoodsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DangerousGoodsDTO result = dangerousGoodsService.save(dangerousGoodsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dangerousGoodsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dangerous-goods : get all the dangerousGoods.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of dangerousGoods in body
     */
    @GetMapping("/dangerous-goods")
    public ResponseEntity<List<DangerousGoodsDTO>> getAllDangerousGoods(DangerousGoodsCriteria criteria) {
        log.debug("REST request to get DangerousGoods by criteria: {}", criteria);
        List<DangerousGoodsDTO> entityList = dangerousGoodsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /dangerous-goods/count : count all the dangerousGoods.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/dangerous-goods/count")
    public ResponseEntity<Long> countDangerousGoods(DangerousGoodsCriteria criteria) {
        log.debug("REST request to count DangerousGoods by criteria: {}", criteria);
        return ResponseEntity.ok().body(dangerousGoodsQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /dangerous-goods/:id : get the "id" dangerousGoods.
     *
     * @param id the id of the dangerousGoodsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dangerousGoodsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dangerous-goods/{id}")
    public ResponseEntity<DangerousGoodsDTO> getDangerousGoods(@PathVariable Long id) {
        log.debug("REST request to get DangerousGoods : {}", id);
        Optional<DangerousGoodsDTO> dangerousGoodsDTO = dangerousGoodsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dangerousGoodsDTO);
    }

    /**
     * DELETE  /dangerous-goods/:id : delete the "id" dangerousGoods.
     *
     * @param id the id of the dangerousGoodsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dangerous-goods/{id}")
    public ResponseEntity<Void> deleteDangerousGoods(@PathVariable Long id) {
        log.debug("REST request to delete DangerousGoods : {}", id);
        dangerousGoodsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
