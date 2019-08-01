package com.resource.server.web.rest;
import com.resource.server.service.ProductTransactionsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductTransactionsDTO;
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
 * REST controller for managing ProductTransactions.
 */
@RestController
@RequestMapping("/api")
public class ProductTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(ProductTransactionsResource.class);

    private static final String ENTITY_NAME = "productTransactions";

    private final ProductTransactionsService productTransactionsService;

    public ProductTransactionsResource(ProductTransactionsService productTransactionsService) {
        this.productTransactionsService = productTransactionsService;
    }

    /**
     * POST  /product-transactions : Create a new productTransactions.
     *
     * @param productTransactionsDTO the productTransactionsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productTransactionsDTO, or with status 400 (Bad Request) if the productTransactions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-transactions")
    public ResponseEntity<ProductTransactionsDTO> createProductTransactions(@Valid @RequestBody ProductTransactionsDTO productTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to save ProductTransactions : {}", productTransactionsDTO);
        if (productTransactionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new productTransactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductTransactionsDTO result = productTransactionsService.save(productTransactionsDTO);
        return ResponseEntity.created(new URI("/api/product-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-transactions : Updates an existing productTransactions.
     *
     * @param productTransactionsDTO the productTransactionsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productTransactionsDTO,
     * or with status 400 (Bad Request) if the productTransactionsDTO is not valid,
     * or with status 500 (Internal Server Error) if the productTransactionsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-transactions")
    public ResponseEntity<ProductTransactionsDTO> updateProductTransactions(@Valid @RequestBody ProductTransactionsDTO productTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to update ProductTransactions : {}", productTransactionsDTO);
        if (productTransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductTransactionsDTO result = productTransactionsService.save(productTransactionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productTransactionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-transactions : get all the productTransactions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productTransactions in body
     */
    @GetMapping("/product-transactions")
    public List<ProductTransactionsDTO> getAllProductTransactions() {
        log.debug("REST request to get all ProductTransactions");
        return productTransactionsService.findAll();
    }

    /**
     * GET  /product-transactions/:id : get the "id" productTransactions.
     *
     * @param id the id of the productTransactionsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productTransactionsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-transactions/{id}")
    public ResponseEntity<ProductTransactionsDTO> getProductTransactions(@PathVariable Long id) {
        log.debug("REST request to get ProductTransactions : {}", id);
        Optional<ProductTransactionsDTO> productTransactionsDTO = productTransactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productTransactionsDTO);
    }

    /**
     * DELETE  /product-transactions/:id : delete the "id" productTransactions.
     *
     * @param id the id of the productTransactionsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-transactions/{id}")
    public ResponseEntity<Void> deleteProductTransactions(@PathVariable Long id) {
        log.debug("REST request to delete ProductTransactions : {}", id);
        productTransactionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
