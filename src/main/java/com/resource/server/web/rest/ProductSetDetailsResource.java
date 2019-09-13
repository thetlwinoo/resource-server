package com.resource.server.web.rest;
import com.resource.server.service.ProductSetDetailsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductSetDetailsDTO;
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
 * REST controller for managing ProductSetDetails.
 */
@RestController
@RequestMapping("/api")
public class ProductSetDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ProductSetDetailsResource.class);

    private static final String ENTITY_NAME = "productSetDetails";

    private final ProductSetDetailsService productSetDetailsService;

    public ProductSetDetailsResource(ProductSetDetailsService productSetDetailsService) {
        this.productSetDetailsService = productSetDetailsService;
    }

    /**
     * POST  /product-set-details : Create a new productSetDetails.
     *
     * @param productSetDetailsDTO the productSetDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productSetDetailsDTO, or with status 400 (Bad Request) if the productSetDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-set-details")
    public ResponseEntity<ProductSetDetailsDTO> createProductSetDetails(@Valid @RequestBody ProductSetDetailsDTO productSetDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save ProductSetDetails : {}", productSetDetailsDTO);
        if (productSetDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new productSetDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductSetDetailsDTO result = productSetDetailsService.save(productSetDetailsDTO);
        return ResponseEntity.created(new URI("/api/product-set-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-set-details : Updates an existing productSetDetails.
     *
     * @param productSetDetailsDTO the productSetDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productSetDetailsDTO,
     * or with status 400 (Bad Request) if the productSetDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the productSetDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-set-details")
    public ResponseEntity<ProductSetDetailsDTO> updateProductSetDetails(@Valid @RequestBody ProductSetDetailsDTO productSetDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update ProductSetDetails : {}", productSetDetailsDTO);
        if (productSetDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductSetDetailsDTO result = productSetDetailsService.save(productSetDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productSetDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-set-details : get all the productSetDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productSetDetails in body
     */
    @GetMapping("/product-set-details")
    public List<ProductSetDetailsDTO> getAllProductSetDetails() {
        log.debug("REST request to get all ProductSetDetails");
        return productSetDetailsService.findAll();
    }

    /**
     * GET  /product-set-details/:id : get the "id" productSetDetails.
     *
     * @param id the id of the productSetDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productSetDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-set-details/{id}")
    public ResponseEntity<ProductSetDetailsDTO> getProductSetDetails(@PathVariable Long id) {
        log.debug("REST request to get ProductSetDetails : {}", id);
        Optional<ProductSetDetailsDTO> productSetDetailsDTO = productSetDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productSetDetailsDTO);
    }

    /**
     * DELETE  /product-set-details/:id : delete the "id" productSetDetails.
     *
     * @param id the id of the productSetDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-set-details/{id}")
    public ResponseEntity<Void> deleteProductSetDetails(@PathVariable Long id) {
        log.debug("REST request to delete ProductSetDetails : {}", id);
        productSetDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
