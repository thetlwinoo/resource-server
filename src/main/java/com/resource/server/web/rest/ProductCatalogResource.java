package com.resource.server.web.rest;
import com.resource.server.service.ProductCatalogService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductCatalogDTO;
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
 * REST controller for managing ProductCatalog.
 */
@RestController
@RequestMapping("/api")
public class ProductCatalogResource {

    private final Logger log = LoggerFactory.getLogger(ProductCatalogResource.class);

    private static final String ENTITY_NAME = "productCatalog";

    private final ProductCatalogService productCatalogService;

    public ProductCatalogResource(ProductCatalogService productCatalogService) {
        this.productCatalogService = productCatalogService;
    }

    /**
     * POST  /product-catalogs : Create a new productCatalog.
     *
     * @param productCatalogDTO the productCatalogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productCatalogDTO, or with status 400 (Bad Request) if the productCatalog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-catalogs")
    public ResponseEntity<ProductCatalogDTO> createProductCatalog(@RequestBody ProductCatalogDTO productCatalogDTO) throws URISyntaxException {
        log.debug("REST request to save ProductCatalog : {}", productCatalogDTO);
        if (productCatalogDTO.getId() != null) {
            throw new BadRequestAlertException("A new productCatalog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductCatalogDTO result = productCatalogService.save(productCatalogDTO);
        return ResponseEntity.created(new URI("/api/product-catalogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-catalogs : Updates an existing productCatalog.
     *
     * @param productCatalogDTO the productCatalogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productCatalogDTO,
     * or with status 400 (Bad Request) if the productCatalogDTO is not valid,
     * or with status 500 (Internal Server Error) if the productCatalogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-catalogs")
    public ResponseEntity<ProductCatalogDTO> updateProductCatalog(@RequestBody ProductCatalogDTO productCatalogDTO) throws URISyntaxException {
        log.debug("REST request to update ProductCatalog : {}", productCatalogDTO);
        if (productCatalogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductCatalogDTO result = productCatalogService.save(productCatalogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productCatalogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-catalogs : get all the productCatalogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productCatalogs in body
     */
    @GetMapping("/product-catalogs")
    public List<ProductCatalogDTO> getAllProductCatalogs() {
        log.debug("REST request to get all ProductCatalogs");
        return productCatalogService.findAll();
    }

    /**
     * GET  /product-catalogs/:id : get the "id" productCatalog.
     *
     * @param id the id of the productCatalogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productCatalogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-catalogs/{id}")
    public ResponseEntity<ProductCatalogDTO> getProductCatalog(@PathVariable Long id) {
        log.debug("REST request to get ProductCatalog : {}", id);
        Optional<ProductCatalogDTO> productCatalogDTO = productCatalogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productCatalogDTO);
    }

    /**
     * DELETE  /product-catalogs/:id : delete the "id" productCatalog.
     *
     * @param id the id of the productCatalogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-catalogs/{id}")
    public ResponseEntity<Void> deleteProductCatalog(@PathVariable Long id) {
        log.debug("REST request to delete ProductCatalog : {}", id);
        productCatalogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
