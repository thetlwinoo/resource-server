package com.resource.server.web.rest;
import com.resource.server.service.StockItemStockGroupsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.StockItemStockGroupsDTO;
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
 * REST controller for managing StockItemStockGroups.
 */
@RestController
@RequestMapping("/api")
public class StockItemStockGroupsResource {

    private final Logger log = LoggerFactory.getLogger(StockItemStockGroupsResource.class);

    private static final String ENTITY_NAME = "stockItemStockGroups";

    private final StockItemStockGroupsService stockItemStockGroupsService;

    public StockItemStockGroupsResource(StockItemStockGroupsService stockItemStockGroupsService) {
        this.stockItemStockGroupsService = stockItemStockGroupsService;
    }

    /**
     * POST  /stock-item-stock-groups : Create a new stockItemStockGroups.
     *
     * @param stockItemStockGroupsDTO the stockItemStockGroupsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stockItemStockGroupsDTO, or with status 400 (Bad Request) if the stockItemStockGroups has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stock-item-stock-groups")
    public ResponseEntity<StockItemStockGroupsDTO> createStockItemStockGroups(@RequestBody StockItemStockGroupsDTO stockItemStockGroupsDTO) throws URISyntaxException {
        log.debug("REST request to save StockItemStockGroups : {}", stockItemStockGroupsDTO);
        if (stockItemStockGroupsDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockItemStockGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockItemStockGroupsDTO result = stockItemStockGroupsService.save(stockItemStockGroupsDTO);
        return ResponseEntity.created(new URI("/api/stock-item-stock-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stock-item-stock-groups : Updates an existing stockItemStockGroups.
     *
     * @param stockItemStockGroupsDTO the stockItemStockGroupsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stockItemStockGroupsDTO,
     * or with status 400 (Bad Request) if the stockItemStockGroupsDTO is not valid,
     * or with status 500 (Internal Server Error) if the stockItemStockGroupsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stock-item-stock-groups")
    public ResponseEntity<StockItemStockGroupsDTO> updateStockItemStockGroups(@RequestBody StockItemStockGroupsDTO stockItemStockGroupsDTO) throws URISyntaxException {
        log.debug("REST request to update StockItemStockGroups : {}", stockItemStockGroupsDTO);
        if (stockItemStockGroupsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockItemStockGroupsDTO result = stockItemStockGroupsService.save(stockItemStockGroupsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stockItemStockGroupsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stock-item-stock-groups : get all the stockItemStockGroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stockItemStockGroups in body
     */
    @GetMapping("/stock-item-stock-groups")
    public List<StockItemStockGroupsDTO> getAllStockItemStockGroups() {
        log.debug("REST request to get all StockItemStockGroups");
        return stockItemStockGroupsService.findAll();
    }

    /**
     * GET  /stock-item-stock-groups/:id : get the "id" stockItemStockGroups.
     *
     * @param id the id of the stockItemStockGroupsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stockItemStockGroupsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stock-item-stock-groups/{id}")
    public ResponseEntity<StockItemStockGroupsDTO> getStockItemStockGroups(@PathVariable Long id) {
        log.debug("REST request to get StockItemStockGroups : {}", id);
        Optional<StockItemStockGroupsDTO> stockItemStockGroupsDTO = stockItemStockGroupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockItemStockGroupsDTO);
    }

    /**
     * DELETE  /stock-item-stock-groups/:id : delete the "id" stockItemStockGroups.
     *
     * @param id the id of the stockItemStockGroupsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stock-item-stock-groups/{id}")
    public ResponseEntity<Void> deleteStockItemStockGroups(@PathVariable Long id) {
        log.debug("REST request to delete StockItemStockGroups : {}", id);
        stockItemStockGroupsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
