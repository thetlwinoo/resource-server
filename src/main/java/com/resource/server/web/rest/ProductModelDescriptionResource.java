package com.resource.server.web.rest;
import com.resource.server.service.ProductModelDescriptionService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductModelDescriptionDTO;
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
 * REST controller for managing ProductModelDescription.
 */
@RestController
@RequestMapping("/api")
public class ProductModelDescriptionResource {

    private final Logger log = LoggerFactory.getLogger(ProductModelDescriptionResource.class);

    private static final String ENTITY_NAME = "productModelDescription";

    private final ProductModelDescriptionService productModelDescriptionService;

    public ProductModelDescriptionResource(ProductModelDescriptionService productModelDescriptionService) {
        this.productModelDescriptionService = productModelDescriptionService;
    }

    /**
     * POST  /product-model-descriptions : Create a new productModelDescription.
     *
     * @param productModelDescriptionDTO the productModelDescriptionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productModelDescriptionDTO, or with status 400 (Bad Request) if the productModelDescription has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-model-descriptions")
    public ResponseEntity<ProductModelDescriptionDTO> createProductModelDescription(@Valid @RequestBody ProductModelDescriptionDTO productModelDescriptionDTO) throws URISyntaxException {
        log.debug("REST request to save ProductModelDescription : {}", productModelDescriptionDTO);
        if (productModelDescriptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new productModelDescription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductModelDescriptionDTO result = productModelDescriptionService.save(productModelDescriptionDTO);
        return ResponseEntity.created(new URI("/api/product-model-descriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-model-descriptions : Updates an existing productModelDescription.
     *
     * @param productModelDescriptionDTO the productModelDescriptionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productModelDescriptionDTO,
     * or with status 400 (Bad Request) if the productModelDescriptionDTO is not valid,
     * or with status 500 (Internal Server Error) if the productModelDescriptionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-model-descriptions")
    public ResponseEntity<ProductModelDescriptionDTO> updateProductModelDescription(@Valid @RequestBody ProductModelDescriptionDTO productModelDescriptionDTO) throws URISyntaxException {
        log.debug("REST request to update ProductModelDescription : {}", productModelDescriptionDTO);
        if (productModelDescriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductModelDescriptionDTO result = productModelDescriptionService.save(productModelDescriptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productModelDescriptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-model-descriptions : get all the productModelDescriptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productModelDescriptions in body
     */
    @GetMapping("/product-model-descriptions")
    public List<ProductModelDescriptionDTO> getAllProductModelDescriptions() {
        log.debug("REST request to get all ProductModelDescriptions");
        return productModelDescriptionService.findAll();
    }

    /**
     * GET  /product-model-descriptions/:id : get the "id" productModelDescription.
     *
     * @param id the id of the productModelDescriptionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productModelDescriptionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-model-descriptions/{id}")
    public ResponseEntity<ProductModelDescriptionDTO> getProductModelDescription(@PathVariable Long id) {
        log.debug("REST request to get ProductModelDescription : {}", id);
        Optional<ProductModelDescriptionDTO> productModelDescriptionDTO = productModelDescriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productModelDescriptionDTO);
    }

    /**
     * DELETE  /product-model-descriptions/:id : delete the "id" productModelDescription.
     *
     * @param id the id of the productModelDescriptionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-model-descriptions/{id}")
    public ResponseEntity<Void> deleteProductModelDescription(@PathVariable Long id) {
        log.debug("REST request to delete ProductModelDescription : {}", id);
        productModelDescriptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
