package com.resource.server.web.rest;
import com.resource.server.service.ProductInventoryService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductInventoryDTO;
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
 * REST controller for managing ProductInventory.
 */
@RestController
@RequestMapping("/api")
public class ProductInventoryResource {

    private final Logger log = LoggerFactory.getLogger(ProductInventoryResource.class);

    private static final String ENTITY_NAME = "productInventory";

    private final ProductInventoryService productInventoryService;

    public ProductInventoryResource(ProductInventoryService productInventoryService) {
        this.productInventoryService = productInventoryService;
    }

    /**
     * POST  /product-inventories : Create a new productInventory.
     *
     * @param productInventoryDTO the productInventoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productInventoryDTO, or with status 400 (Bad Request) if the productInventory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-inventories")
    public ResponseEntity<ProductInventoryDTO> createProductInventory(@Valid @RequestBody ProductInventoryDTO productInventoryDTO) throws URISyntaxException {
        log.debug("REST request to save ProductInventory : {}", productInventoryDTO);
        if (productInventoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new productInventory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductInventoryDTO result = productInventoryService.save(productInventoryDTO);
        return ResponseEntity.created(new URI("/api/product-inventories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-inventories : Updates an existing productInventory.
     *
     * @param productInventoryDTO the productInventoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productInventoryDTO,
     * or with status 400 (Bad Request) if the productInventoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the productInventoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-inventories")
    public ResponseEntity<ProductInventoryDTO> updateProductInventory(@Valid @RequestBody ProductInventoryDTO productInventoryDTO) throws URISyntaxException {
        log.debug("REST request to update ProductInventory : {}", productInventoryDTO);
        if (productInventoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductInventoryDTO result = productInventoryService.save(productInventoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productInventoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-inventories : get all the productInventories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productInventories in body
     */
    @GetMapping("/product-inventories")
    public List<ProductInventoryDTO> getAllProductInventories() {
        log.debug("REST request to get all ProductInventories");
        return productInventoryService.findAll();
    }

    /**
     * GET  /product-inventories/:id : get the "id" productInventory.
     *
     * @param id the id of the productInventoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productInventoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-inventories/{id}")
    public ResponseEntity<ProductInventoryDTO> getProductInventory(@PathVariable Long id) {
        log.debug("REST request to get ProductInventory : {}", id);
        Optional<ProductInventoryDTO> productInventoryDTO = productInventoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productInventoryDTO);
    }

    /**
     * DELETE  /product-inventories/:id : delete the "id" productInventory.
     *
     * @param id the id of the productInventoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-inventories/{id}")
    public ResponseEntity<Void> deleteProductInventory(@PathVariable Long id) {
        log.debug("REST request to delete ProductInventory : {}", id);
        productInventoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
