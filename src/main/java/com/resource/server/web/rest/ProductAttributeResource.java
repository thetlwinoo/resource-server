package com.resource.server.web.rest;
import com.resource.server.service.ProductAttributeService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductAttributeDTO;
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
 * REST controller for managing ProductAttribute.
 */
@RestController
@RequestMapping("/api")
public class ProductAttributeResource {

    private final Logger log = LoggerFactory.getLogger(ProductAttributeResource.class);

    private static final String ENTITY_NAME = "productAttribute";

    private final ProductAttributeService productAttributeService;

    public ProductAttributeResource(ProductAttributeService productAttributeService) {
        this.productAttributeService = productAttributeService;
    }

    /**
     * POST  /product-attributes : Create a new productAttribute.
     *
     * @param productAttributeDTO the productAttributeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productAttributeDTO, or with status 400 (Bad Request) if the productAttribute has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-attributes")
    public ResponseEntity<ProductAttributeDTO> createProductAttribute(@Valid @RequestBody ProductAttributeDTO productAttributeDTO) throws URISyntaxException {
        log.debug("REST request to save ProductAttribute : {}", productAttributeDTO);
        if (productAttributeDTO.getId() != null) {
            throw new BadRequestAlertException("A new productAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductAttributeDTO result = productAttributeService.save(productAttributeDTO);
        return ResponseEntity.created(new URI("/api/product-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-attributes : Updates an existing productAttribute.
     *
     * @param productAttributeDTO the productAttributeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productAttributeDTO,
     * or with status 400 (Bad Request) if the productAttributeDTO is not valid,
     * or with status 500 (Internal Server Error) if the productAttributeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-attributes")
    public ResponseEntity<ProductAttributeDTO> updateProductAttribute(@Valid @RequestBody ProductAttributeDTO productAttributeDTO) throws URISyntaxException {
        log.debug("REST request to update ProductAttribute : {}", productAttributeDTO);
        if (productAttributeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductAttributeDTO result = productAttributeService.save(productAttributeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productAttributeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-attributes : get all the productAttributes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productAttributes in body
     */
    @GetMapping("/product-attributes")
    public List<ProductAttributeDTO> getAllProductAttributes() {
        log.debug("REST request to get all ProductAttributes");
        return productAttributeService.findAll();
    }

    /**
     * GET  /product-attributes/:id : get the "id" productAttribute.
     *
     * @param id the id of the productAttributeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productAttributeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-attributes/{id}")
    public ResponseEntity<ProductAttributeDTO> getProductAttribute(@PathVariable Long id) {
        log.debug("REST request to get ProductAttribute : {}", id);
        Optional<ProductAttributeDTO> productAttributeDTO = productAttributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productAttributeDTO);
    }

    /**
     * DELETE  /product-attributes/:id : delete the "id" productAttribute.
     *
     * @param id the id of the productAttributeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-attributes/{id}")
    public ResponseEntity<Void> deleteProductAttribute(@PathVariable Long id) {
        log.debug("REST request to delete ProductAttribute : {}", id);
        productAttributeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
