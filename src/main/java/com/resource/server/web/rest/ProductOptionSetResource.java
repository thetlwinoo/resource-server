package com.resource.server.web.rest;
import com.resource.server.service.ProductOptionSetService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductOptionSetDTO;
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
 * REST controller for managing ProductOptionSet.
 */
@RestController
@RequestMapping("/api")
public class ProductOptionSetResource {

    private final Logger log = LoggerFactory.getLogger(ProductOptionSetResource.class);

    private static final String ENTITY_NAME = "productOptionSet";

    private final ProductOptionSetService productOptionSetService;

    public ProductOptionSetResource(ProductOptionSetService productOptionSetService) {
        this.productOptionSetService = productOptionSetService;
    }

    /**
     * POST  /product-option-sets : Create a new productOptionSet.
     *
     * @param productOptionSetDTO the productOptionSetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productOptionSetDTO, or with status 400 (Bad Request) if the productOptionSet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-option-sets")
    public ResponseEntity<ProductOptionSetDTO> createProductOptionSet(@Valid @RequestBody ProductOptionSetDTO productOptionSetDTO) throws URISyntaxException {
        log.debug("REST request to save ProductOptionSet : {}", productOptionSetDTO);
        if (productOptionSetDTO.getId() != null) {
            throw new BadRequestAlertException("A new productOptionSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductOptionSetDTO result = productOptionSetService.save(productOptionSetDTO);
        return ResponseEntity.created(new URI("/api/product-option-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-option-sets : Updates an existing productOptionSet.
     *
     * @param productOptionSetDTO the productOptionSetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productOptionSetDTO,
     * or with status 400 (Bad Request) if the productOptionSetDTO is not valid,
     * or with status 500 (Internal Server Error) if the productOptionSetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-option-sets")
    public ResponseEntity<ProductOptionSetDTO> updateProductOptionSet(@Valid @RequestBody ProductOptionSetDTO productOptionSetDTO) throws URISyntaxException {
        log.debug("REST request to update ProductOptionSet : {}", productOptionSetDTO);
        if (productOptionSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductOptionSetDTO result = productOptionSetService.save(productOptionSetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productOptionSetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-option-sets : get all the productOptionSets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productOptionSets in body
     */
    @GetMapping("/product-option-sets")
    public List<ProductOptionSetDTO> getAllProductOptionSets() {
        log.debug("REST request to get all ProductOptionSets");
        return productOptionSetService.findAll();
    }

    /**
     * GET  /product-option-sets/:id : get the "id" productOptionSet.
     *
     * @param id the id of the productOptionSetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productOptionSetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-option-sets/{id}")
    public ResponseEntity<ProductOptionSetDTO> getProductOptionSet(@PathVariable Long id) {
        log.debug("REST request to get ProductOptionSet : {}", id);
        Optional<ProductOptionSetDTO> productOptionSetDTO = productOptionSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productOptionSetDTO);
    }

    /**
     * DELETE  /product-option-sets/:id : delete the "id" productOptionSet.
     *
     * @param id the id of the productOptionSetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-option-sets/{id}")
    public ResponseEntity<Void> deleteProductOptionSet(@PathVariable Long id) {
        log.debug("REST request to delete ProductOptionSet : {}", id);
        productOptionSetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
