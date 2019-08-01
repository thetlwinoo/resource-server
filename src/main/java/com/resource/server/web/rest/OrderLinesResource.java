package com.resource.server.web.rest;
import com.resource.server.service.OrderLinesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.OrderLinesDTO;
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
 * REST controller for managing OrderLines.
 */
@RestController
@RequestMapping("/api")
public class OrderLinesResource {

    private final Logger log = LoggerFactory.getLogger(OrderLinesResource.class);

    private static final String ENTITY_NAME = "orderLines";

    private final OrderLinesService orderLinesService;

    public OrderLinesResource(OrderLinesService orderLinesService) {
        this.orderLinesService = orderLinesService;
    }

    /**
     * POST  /order-lines : Create a new orderLines.
     *
     * @param orderLinesDTO the orderLinesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderLinesDTO, or with status 400 (Bad Request) if the orderLines has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-lines")
    public ResponseEntity<OrderLinesDTO> createOrderLines(@Valid @RequestBody OrderLinesDTO orderLinesDTO) throws URISyntaxException {
        log.debug("REST request to save OrderLines : {}", orderLinesDTO);
        if (orderLinesDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderLines cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderLinesDTO result = orderLinesService.save(orderLinesDTO);
        return ResponseEntity.created(new URI("/api/order-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-lines : Updates an existing orderLines.
     *
     * @param orderLinesDTO the orderLinesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderLinesDTO,
     * or with status 400 (Bad Request) if the orderLinesDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderLinesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-lines")
    public ResponseEntity<OrderLinesDTO> updateOrderLines(@Valid @RequestBody OrderLinesDTO orderLinesDTO) throws URISyntaxException {
        log.debug("REST request to update OrderLines : {}", orderLinesDTO);
        if (orderLinesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderLinesDTO result = orderLinesService.save(orderLinesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderLinesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-lines : get all the orderLines.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderLines in body
     */
    @GetMapping("/order-lines")
    public List<OrderLinesDTO> getAllOrderLines() {
        log.debug("REST request to get all OrderLines");
        return orderLinesService.findAll();
    }

    /**
     * GET  /order-lines/:id : get the "id" orderLines.
     *
     * @param id the id of the orderLinesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderLinesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/order-lines/{id}")
    public ResponseEntity<OrderLinesDTO> getOrderLines(@PathVariable Long id) {
        log.debug("REST request to get OrderLines : {}", id);
        Optional<OrderLinesDTO> orderLinesDTO = orderLinesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderLinesDTO);
    }

    /**
     * DELETE  /order-lines/:id : delete the "id" orderLines.
     *
     * @param id the id of the orderLinesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-lines/{id}")
    public ResponseEntity<Void> deleteOrderLines(@PathVariable Long id) {
        log.debug("REST request to delete OrderLines : {}", id);
        orderLinesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
