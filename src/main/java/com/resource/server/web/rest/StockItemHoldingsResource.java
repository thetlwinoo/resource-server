package com.resource.server.web.rest;
import com.resource.server.service.StockItemHoldingsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.StockItemHoldingsDTO;
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
 * REST controller for managing StockItemHoldings.
 */
@RestController
@RequestMapping("/api")
public class StockItemHoldingsResource {

    private final Logger log = LoggerFactory.getLogger(StockItemHoldingsResource.class);

    private static final String ENTITY_NAME = "stockItemHoldings";

    private final StockItemHoldingsService stockItemHoldingsService;

    public StockItemHoldingsResource(StockItemHoldingsService stockItemHoldingsService) {
        this.stockItemHoldingsService = stockItemHoldingsService;
    }

    /**
     * POST  /stock-item-holdings : Create a new stockItemHoldings.
     *
     * @param stockItemHoldingsDTO the stockItemHoldingsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stockItemHoldingsDTO, or with status 400 (Bad Request) if the stockItemHoldings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stock-item-holdings")
    public ResponseEntity<StockItemHoldingsDTO> createStockItemHoldings(@Valid @RequestBody StockItemHoldingsDTO stockItemHoldingsDTO) throws URISyntaxException {
        log.debug("REST request to save StockItemHoldings : {}", stockItemHoldingsDTO);
        if (stockItemHoldingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockItemHoldings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockItemHoldingsDTO result = stockItemHoldingsService.save(stockItemHoldingsDTO);
        return ResponseEntity.created(new URI("/api/stock-item-holdings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stock-item-holdings : Updates an existing stockItemHoldings.
     *
     * @param stockItemHoldingsDTO the stockItemHoldingsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stockItemHoldingsDTO,
     * or with status 400 (Bad Request) if the stockItemHoldingsDTO is not valid,
     * or with status 500 (Internal Server Error) if the stockItemHoldingsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stock-item-holdings")
    public ResponseEntity<StockItemHoldingsDTO> updateStockItemHoldings(@Valid @RequestBody StockItemHoldingsDTO stockItemHoldingsDTO) throws URISyntaxException {
        log.debug("REST request to update StockItemHoldings : {}", stockItemHoldingsDTO);
        if (stockItemHoldingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockItemHoldingsDTO result = stockItemHoldingsService.save(stockItemHoldingsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stockItemHoldingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stock-item-holdings : get all the stockItemHoldings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stockItemHoldings in body
     */
    @GetMapping("/stock-item-holdings")
    public List<StockItemHoldingsDTO> getAllStockItemHoldings() {
        log.debug("REST request to get all StockItemHoldings");
        return stockItemHoldingsService.findAll();
    }

    /**
     * GET  /stock-item-holdings/:id : get the "id" stockItemHoldings.
     *
     * @param id the id of the stockItemHoldingsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stockItemHoldingsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stock-item-holdings/{id}")
    public ResponseEntity<StockItemHoldingsDTO> getStockItemHoldings(@PathVariable Long id) {
        log.debug("REST request to get StockItemHoldings : {}", id);
        Optional<StockItemHoldingsDTO> stockItemHoldingsDTO = stockItemHoldingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockItemHoldingsDTO);
    }

    /**
     * DELETE  /stock-item-holdings/:id : delete the "id" stockItemHoldings.
     *
     * @param id the id of the stockItemHoldingsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stock-item-holdings/{id}")
    public ResponseEntity<Void> deleteStockItemHoldings(@PathVariable Long id) {
        log.debug("REST request to delete StockItemHoldings : {}", id);
        stockItemHoldingsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
