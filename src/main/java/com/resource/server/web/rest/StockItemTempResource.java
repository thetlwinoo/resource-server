package com.resource.server.web.rest;
import com.resource.server.service.StockItemTempService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.StockItemTempDTO;
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
 * REST controller for managing StockItemTemp.
 */
@RestController
@RequestMapping("/api")
public class StockItemTempResource {

    private final Logger log = LoggerFactory.getLogger(StockItemTempResource.class);

    private static final String ENTITY_NAME = "stockItemTemp";

    private final StockItemTempService stockItemTempService;

    public StockItemTempResource(StockItemTempService stockItemTempService) {
        this.stockItemTempService = stockItemTempService;
    }

    /**
     * POST  /stock-item-temps : Create a new stockItemTemp.
     *
     * @param stockItemTempDTO the stockItemTempDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stockItemTempDTO, or with status 400 (Bad Request) if the stockItemTemp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stock-item-temps")
    public ResponseEntity<StockItemTempDTO> createStockItemTemp(@Valid @RequestBody StockItemTempDTO stockItemTempDTO) throws URISyntaxException {
        log.debug("REST request to save StockItemTemp : {}", stockItemTempDTO);
        if (stockItemTempDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockItemTemp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockItemTempDTO result = stockItemTempService.save(stockItemTempDTO);
        return ResponseEntity.created(new URI("/api/stock-item-temps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stock-item-temps : Updates an existing stockItemTemp.
     *
     * @param stockItemTempDTO the stockItemTempDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stockItemTempDTO,
     * or with status 400 (Bad Request) if the stockItemTempDTO is not valid,
     * or with status 500 (Internal Server Error) if the stockItemTempDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stock-item-temps")
    public ResponseEntity<StockItemTempDTO> updateStockItemTemp(@Valid @RequestBody StockItemTempDTO stockItemTempDTO) throws URISyntaxException {
        log.debug("REST request to update StockItemTemp : {}", stockItemTempDTO);
        if (stockItemTempDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockItemTempDTO result = stockItemTempService.save(stockItemTempDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stockItemTempDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stock-item-temps : get all the stockItemTemps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stockItemTemps in body
     */
    @GetMapping("/stock-item-temps")
    public List<StockItemTempDTO> getAllStockItemTemps() {
        log.debug("REST request to get all StockItemTemps");
        return stockItemTempService.findAll();
    }

    /**
     * GET  /stock-item-temps/:id : get the "id" stockItemTemp.
     *
     * @param id the id of the stockItemTempDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stockItemTempDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stock-item-temps/{id}")
    public ResponseEntity<StockItemTempDTO> getStockItemTemp(@PathVariable Long id) {
        log.debug("REST request to get StockItemTemp : {}", id);
        Optional<StockItemTempDTO> stockItemTempDTO = stockItemTempService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockItemTempDTO);
    }

    /**
     * DELETE  /stock-item-temps/:id : delete the "id" stockItemTemp.
     *
     * @param id the id of the stockItemTempDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stock-item-temps/{id}")
    public ResponseEntity<Void> deleteStockItemTemp(@PathVariable Long id) {
        log.debug("REST request to delete StockItemTemp : {}", id);
        stockItemTempService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
