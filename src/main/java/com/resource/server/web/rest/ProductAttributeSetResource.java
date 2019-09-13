package com.resource.server.web.rest;
import com.resource.server.service.ProductAttributeSetService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductAttributeSetDTO;
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
 * REST controller for managing ProductAttributeSet.
 */
@RestController
@RequestMapping("/api")
public class ProductAttributeSetResource {

    private final Logger log = LoggerFactory.getLogger(ProductAttributeSetResource.class);

    private static final String ENTITY_NAME = "productAttributeSet";

    private final ProductAttributeSetService productAttributeSetService;

    public ProductAttributeSetResource(ProductAttributeSetService productAttributeSetService) {
        this.productAttributeSetService = productAttributeSetService;
    }

    /**
     * POST  /product-attribute-sets : Create a new productAttributeSet.
     *
     * @param productAttributeSetDTO the productAttributeSetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productAttributeSetDTO, or with status 400 (Bad Request) if the productAttributeSet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-attribute-sets")
    public ResponseEntity<ProductAttributeSetDTO> createProductAttributeSet(@Valid @RequestBody ProductAttributeSetDTO productAttributeSetDTO) throws URISyntaxException {
        log.debug("REST request to save ProductAttributeSet : {}", productAttributeSetDTO);
        if (productAttributeSetDTO.getId() != null) {
            throw new BadRequestAlertException("A new productAttributeSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductAttributeSetDTO result = productAttributeSetService.save(productAttributeSetDTO);
        return ResponseEntity.created(new URI("/api/product-attribute-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-attribute-sets : Updates an existing productAttributeSet.
     *
     * @param productAttributeSetDTO the productAttributeSetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productAttributeSetDTO,
     * or with status 400 (Bad Request) if the productAttributeSetDTO is not valid,
     * or with status 500 (Internal Server Error) if the productAttributeSetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-attribute-sets")
    public ResponseEntity<ProductAttributeSetDTO> updateProductAttributeSet(@Valid @RequestBody ProductAttributeSetDTO productAttributeSetDTO) throws URISyntaxException {
        log.debug("REST request to update ProductAttributeSet : {}", productAttributeSetDTO);
        if (productAttributeSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductAttributeSetDTO result = productAttributeSetService.save(productAttributeSetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productAttributeSetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-attribute-sets : get all the productAttributeSets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productAttributeSets in body
     */
    @GetMapping("/product-attribute-sets")
    public List<ProductAttributeSetDTO> getAllProductAttributeSets() {
        log.debug("REST request to get all ProductAttributeSets");
        return productAttributeSetService.findAll();
    }

    /**
     * GET  /product-attribute-sets/:id : get the "id" productAttributeSet.
     *
     * @param id the id of the productAttributeSetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productAttributeSetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-attribute-sets/{id}")
    public ResponseEntity<ProductAttributeSetDTO> getProductAttributeSet(@PathVariable Long id) {
        log.debug("REST request to get ProductAttributeSet : {}", id);
        Optional<ProductAttributeSetDTO> productAttributeSetDTO = productAttributeSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productAttributeSetDTO);
    }

    /**
     * DELETE  /product-attribute-sets/:id : delete the "id" productAttributeSet.
     *
     * @param id the id of the productAttributeSetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-attribute-sets/{id}")
    public ResponseEntity<Void> deleteProductAttributeSet(@PathVariable Long id) {
        log.debug("REST request to delete ProductAttributeSet : {}", id);
        productAttributeSetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
