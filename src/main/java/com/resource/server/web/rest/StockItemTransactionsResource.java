package com.resource.server.web.rest;
import com.resource.server.service.StockItemTransactionsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.StockItemTransactionsDTO;
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
 * REST controller for managing StockItemTransactions.
 */
@RestController
@RequestMapping("/api")
public class StockItemTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(StockItemTransactionsResource.class);

    private static final String ENTITY_NAME = "stockItemTransactions";

    private final StockItemTransactionsService stockItemTransactionsService;

    public StockItemTransactionsResource(StockItemTransactionsService stockItemTransactionsService) {
        this.stockItemTransactionsService = stockItemTransactionsService;
    }

    /**
     * POST  /stock-item-transactions : Create a new stockItemTransactions.
     *
     * @param stockItemTransactionsDTO the stockItemTransactionsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stockItemTransactionsDTO, or with status 400 (Bad Request) if the stockItemTransactions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stock-item-transactions")
    public ResponseEntity<StockItemTransactionsDTO> createStockItemTransactions(@Valid @RequestBody StockItemTransactionsDTO stockItemTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to save StockItemTransactions : {}", stockItemTransactionsDTO);
        if (stockItemTransactionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockItemTransactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockItemTransactionsDTO result = stockItemTransactionsService.save(stockItemTransactionsDTO);
        return ResponseEntity.created(new URI("/api/stock-item-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stock-item-transactions : Updates an existing stockItemTransactions.
     *
     * @param stockItemTransactionsDTO the stockItemTransactionsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stockItemTransactionsDTO,
     * or with status 400 (Bad Request) if the stockItemTransactionsDTO is not valid,
     * or with status 500 (Internal Server Error) if the stockItemTransactionsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stock-item-transactions")
    public ResponseEntity<StockItemTransactionsDTO> updateStockItemTransactions(@Valid @RequestBody StockItemTransactionsDTO stockItemTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to update StockItemTransactions : {}", stockItemTransactionsDTO);
        if (stockItemTransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockItemTransactionsDTO result = stockItemTransactionsService.save(stockItemTransactionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stockItemTransactionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stock-item-transactions : get all the stockItemTransactions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stockItemTransactions in body
     */
    @GetMapping("/stock-item-transactions")
    public List<StockItemTransactionsDTO> getAllStockItemTransactions() {
        log.debug("REST request to get all StockItemTransactions");
        return stockItemTransactionsService.findAll();
    }

    /**
     * GET  /stock-item-transactions/:id : get the "id" stockItemTransactions.
     *
     * @param id the id of the stockItemTransactionsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stockItemTransactionsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stock-item-transactions/{id}")
    public ResponseEntity<StockItemTransactionsDTO> getStockItemTransactions(@PathVariable Long id) {
        log.debug("REST request to get StockItemTransactions : {}", id);
        Optional<StockItemTransactionsDTO> stockItemTransactionsDTO = stockItemTransactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockItemTransactionsDTO);
    }

    /**
     * DELETE  /stock-item-transactions/:id : delete the "id" stockItemTransactions.
     *
     * @param id the id of the stockItemTransactionsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stock-item-transactions/{id}")
    public ResponseEntity<Void> deleteStockItemTransactions(@PathVariable Long id) {
        log.debug("REST request to delete StockItemTransactions : {}", id);
        stockItemTransactionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
