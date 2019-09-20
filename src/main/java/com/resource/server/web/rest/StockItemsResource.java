package com.resource.server.web.rest;
import com.resource.server.domain.StockItems;
import com.resource.server.service.StockItemsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.web.rest.util.PaginationUtil;
import com.resource.server.service.dto.StockItemsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing StockItems.
 */
@RestController
@RequestMapping("/api")
public class StockItemsResource {

    private final Logger log = LoggerFactory.getLogger(StockItemsResource.class);

    private static final String ENTITY_NAME = "stockItems";

    private final StockItemsService stockItemsService;

    public StockItemsResource(StockItemsService stockItemsService) {
        this.stockItemsService = stockItemsService;
    }

    /**
     * POST  /stock-items : Create a new stockItems.
     *
     * @param stockItemsDTO the stockItemsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stockItemsDTO, or with status 400 (Bad Request) if the stockItems has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stock-items")
    public ResponseEntity<StockItemsDTO> createStockItems(@Valid @RequestBody StockItemsDTO stockItemsDTO) throws URISyntaxException {
        log.debug("REST request to save StockItems : {}", stockItemsDTO);
        if (stockItemsDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockItemsDTO result = stockItemsService.save(stockItemsDTO);
        return ResponseEntity.created(new URI("/api/stock-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stock-items : Updates an existing stockItems.
     *
     * @param stockItemsDTO the stockItemsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stockItemsDTO,
     * or with status 400 (Bad Request) if the stockItemsDTO is not valid,
     * or with status 500 (Internal Server Error) if the stockItemsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stock-items")
    public ResponseEntity<StockItemsDTO> updateStockItems(@Valid @RequestBody StockItemsDTO stockItemsDTO) throws URISyntaxException {
        log.debug("REST request to update StockItems : {}", stockItemsDTO);
        if (stockItemsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockItemsDTO result = stockItemsService.save(stockItemsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stockItemsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stock-items : get all the stockItems.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of stockItems in body
     */
    @GetMapping("/stock-items")
    public ResponseEntity<List<StockItemsDTO>> getAllStockItems(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("stockitemholding-is-null".equals(filter)) {
            log.debug("REST request to get all StockItemss where stockItemHolding is null");
            return new ResponseEntity<>(stockItemsService.findAllWhereStockItemHoldingIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of StockItems");
        Page<StockItemsDTO> page = stockItemsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stock-items");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /stock-items/:id : get the "id" stockItems.
     *
     * @param id the id of the stockItemsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stockItemsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stock-items/{id}")
    public ResponseEntity<StockItemsDTO> getStockItems(@PathVariable Long id) {
        log.debug("REST request to get StockItems : {}", id);
        Optional<StockItemsDTO> stockItemsDTO = stockItemsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockItemsDTO);
    }

    /**
     * DELETE  /stock-items/:id : delete the "id" stockItems.
     *
     * @param id the id of the stockItemsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stock-items/{id}")
    public ResponseEntity<Void> deleteStockItems(@PathVariable Long id) {
        log.debug("REST request to delete StockItems : {}", id);
        stockItemsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
