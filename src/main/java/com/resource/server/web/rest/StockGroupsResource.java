package com.resource.server.web.rest;
import com.resource.server.service.StockGroupsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.StockGroupsDTO;
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
 * REST controller for managing StockGroups.
 */
@RestController
@RequestMapping("/api")
public class StockGroupsResource {

    private final Logger log = LoggerFactory.getLogger(StockGroupsResource.class);

    private static final String ENTITY_NAME = "stockGroups";

    private final StockGroupsService stockGroupsService;

    public StockGroupsResource(StockGroupsService stockGroupsService) {
        this.stockGroupsService = stockGroupsService;
    }

    /**
     * POST  /stock-groups : Create a new stockGroups.
     *
     * @param stockGroupsDTO the stockGroupsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stockGroupsDTO, or with status 400 (Bad Request) if the stockGroups has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stock-groups")
    public ResponseEntity<StockGroupsDTO> createStockGroups(@Valid @RequestBody StockGroupsDTO stockGroupsDTO) throws URISyntaxException {
        log.debug("REST request to save StockGroups : {}", stockGroupsDTO);
        if (stockGroupsDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockGroupsDTO result = stockGroupsService.save(stockGroupsDTO);
        return ResponseEntity.created(new URI("/api/stock-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stock-groups : Updates an existing stockGroups.
     *
     * @param stockGroupsDTO the stockGroupsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stockGroupsDTO,
     * or with status 400 (Bad Request) if the stockGroupsDTO is not valid,
     * or with status 500 (Internal Server Error) if the stockGroupsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stock-groups")
    public ResponseEntity<StockGroupsDTO> updateStockGroups(@Valid @RequestBody StockGroupsDTO stockGroupsDTO) throws URISyntaxException {
        log.debug("REST request to update StockGroups : {}", stockGroupsDTO);
        if (stockGroupsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockGroupsDTO result = stockGroupsService.save(stockGroupsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stockGroupsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stock-groups : get all the stockGroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stockGroups in body
     */
    @GetMapping("/stock-groups")
    public List<StockGroupsDTO> getAllStockGroups() {
        log.debug("REST request to get all StockGroups");
        return stockGroupsService.findAll();
    }

    /**
     * GET  /stock-groups/:id : get the "id" stockGroups.
     *
     * @param id the id of the stockGroupsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stockGroupsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stock-groups/{id}")
    public ResponseEntity<StockGroupsDTO> getStockGroups(@PathVariable Long id) {
        log.debug("REST request to get StockGroups : {}", id);
        Optional<StockGroupsDTO> stockGroupsDTO = stockGroupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockGroupsDTO);
    }

    /**
     * DELETE  /stock-groups/:id : delete the "id" stockGroups.
     *
     * @param id the id of the stockGroupsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stock-groups/{id}")
    public ResponseEntity<Void> deleteStockGroups(@PathVariable Long id) {
        log.debug("REST request to delete StockGroups : {}", id);
        stockGroupsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
