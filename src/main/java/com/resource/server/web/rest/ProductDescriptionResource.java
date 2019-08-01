package com.resource.server.web.rest;
import com.resource.server.service.ProductDescriptionService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductDescriptionDTO;
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
 * REST controller for managing ProductDescription.
 */
@RestController
@RequestMapping("/api")
public class ProductDescriptionResource {

    private final Logger log = LoggerFactory.getLogger(ProductDescriptionResource.class);

    private static final String ENTITY_NAME = "productDescription";

    private final ProductDescriptionService productDescriptionService;

    public ProductDescriptionResource(ProductDescriptionService productDescriptionService) {
        this.productDescriptionService = productDescriptionService;
    }

    /**
     * POST  /product-descriptions : Create a new productDescription.
     *
     * @param productDescriptionDTO the productDescriptionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productDescriptionDTO, or with status 400 (Bad Request) if the productDescription has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-descriptions")
    public ResponseEntity<ProductDescriptionDTO> createProductDescription(@Valid @RequestBody ProductDescriptionDTO productDescriptionDTO) throws URISyntaxException {
        log.debug("REST request to save ProductDescription : {}", productDescriptionDTO);
        if (productDescriptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new productDescription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductDescriptionDTO result = productDescriptionService.save(productDescriptionDTO);
        return ResponseEntity.created(new URI("/api/product-descriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-descriptions : Updates an existing productDescription.
     *
     * @param productDescriptionDTO the productDescriptionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productDescriptionDTO,
     * or with status 400 (Bad Request) if the productDescriptionDTO is not valid,
     * or with status 500 (Internal Server Error) if the productDescriptionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-descriptions")
    public ResponseEntity<ProductDescriptionDTO> updateProductDescription(@Valid @RequestBody ProductDescriptionDTO productDescriptionDTO) throws URISyntaxException {
        log.debug("REST request to update ProductDescription : {}", productDescriptionDTO);
        if (productDescriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductDescriptionDTO result = productDescriptionService.save(productDescriptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productDescriptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-descriptions : get all the productDescriptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productDescriptions in body
     */
    @GetMapping("/product-descriptions")
    public List<ProductDescriptionDTO> getAllProductDescriptions() {
        log.debug("REST request to get all ProductDescriptions");
        return productDescriptionService.findAll();
    }

    /**
     * GET  /product-descriptions/:id : get the "id" productDescription.
     *
     * @param id the id of the productDescriptionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productDescriptionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-descriptions/{id}")
    public ResponseEntity<ProductDescriptionDTO> getProductDescription(@PathVariable Long id) {
        log.debug("REST request to get ProductDescription : {}", id);
        Optional<ProductDescriptionDTO> productDescriptionDTO = productDescriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDescriptionDTO);
    }

    /**
     * DELETE  /product-descriptions/:id : delete the "id" productDescription.
     *
     * @param id the id of the productDescriptionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-descriptions/{id}")
    public ResponseEntity<Void> deleteProductDescription(@PathVariable Long id) {
        log.debug("REST request to delete ProductDescription : {}", id);
        productDescriptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
