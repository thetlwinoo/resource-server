package com.resource.server.web.rest;
import com.resource.server.service.ProductModelService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductModelDTO;
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
 * REST controller for managing ProductModel.
 */
@RestController
@RequestMapping("/api")
public class ProductModelResource {

    private final Logger log = LoggerFactory.getLogger(ProductModelResource.class);

    private static final String ENTITY_NAME = "productModel";

    private final ProductModelService productModelService;

    public ProductModelResource(ProductModelService productModelService) {
        this.productModelService = productModelService;
    }

    /**
     * POST  /product-models : Create a new productModel.
     *
     * @param productModelDTO the productModelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productModelDTO, or with status 400 (Bad Request) if the productModel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-models")
    public ResponseEntity<ProductModelDTO> createProductModel(@Valid @RequestBody ProductModelDTO productModelDTO) throws URISyntaxException {
        log.debug("REST request to save ProductModel : {}", productModelDTO);
        if (productModelDTO.getId() != null) {
            throw new BadRequestAlertException("A new productModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductModelDTO result = productModelService.save(productModelDTO);
        return ResponseEntity.created(new URI("/api/product-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-models : Updates an existing productModel.
     *
     * @param productModelDTO the productModelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productModelDTO,
     * or with status 400 (Bad Request) if the productModelDTO is not valid,
     * or with status 500 (Internal Server Error) if the productModelDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-models")
    public ResponseEntity<ProductModelDTO> updateProductModel(@Valid @RequestBody ProductModelDTO productModelDTO) throws URISyntaxException {
        log.debug("REST request to update ProductModel : {}", productModelDTO);
        if (productModelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductModelDTO result = productModelService.save(productModelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productModelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-models : get all the productModels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productModels in body
     */
    @GetMapping("/product-models")
    public List<ProductModelDTO> getAllProductModels() {
        log.debug("REST request to get all ProductModels");
        return productModelService.findAll();
    }

    /**
     * GET  /product-models/:id : get the "id" productModel.
     *
     * @param id the id of the productModelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productModelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-models/{id}")
    public ResponseEntity<ProductModelDTO> getProductModel(@PathVariable Long id) {
        log.debug("REST request to get ProductModel : {}", id);
        Optional<ProductModelDTO> productModelDTO = productModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productModelDTO);
    }

    /**
     * DELETE  /product-models/:id : delete the "id" productModel.
     *
     * @param id the id of the productModelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-models/{id}")
    public ResponseEntity<Void> deleteProductModel(@PathVariable Long id) {
        log.debug("REST request to delete ProductModel : {}", id);
        productModelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
