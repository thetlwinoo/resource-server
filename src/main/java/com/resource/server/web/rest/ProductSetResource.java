package com.resource.server.web.rest;
import com.resource.server.service.ProductSetService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductSetDTO;
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
 * REST controller for managing ProductSet.
 */
@RestController
@RequestMapping("/api")
public class ProductSetResource {

    private final Logger log = LoggerFactory.getLogger(ProductSetResource.class);

    private static final String ENTITY_NAME = "productSet";

    private final ProductSetService productSetService;

    public ProductSetResource(ProductSetService productSetService) {
        this.productSetService = productSetService;
    }

    /**
     * POST  /product-sets : Create a new productSet.
     *
     * @param productSetDTO the productSetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productSetDTO, or with status 400 (Bad Request) if the productSet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-sets")
    public ResponseEntity<ProductSetDTO> createProductSet(@Valid @RequestBody ProductSetDTO productSetDTO) throws URISyntaxException {
        log.debug("REST request to save ProductSet : {}", productSetDTO);
        if (productSetDTO.getId() != null) {
            throw new BadRequestAlertException("A new productSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductSetDTO result = productSetService.save(productSetDTO);
        return ResponseEntity.created(new URI("/api/product-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-sets : Updates an existing productSet.
     *
     * @param productSetDTO the productSetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productSetDTO,
     * or with status 400 (Bad Request) if the productSetDTO is not valid,
     * or with status 500 (Internal Server Error) if the productSetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-sets")
    public ResponseEntity<ProductSetDTO> updateProductSet(@Valid @RequestBody ProductSetDTO productSetDTO) throws URISyntaxException {
        log.debug("REST request to update ProductSet : {}", productSetDTO);
        if (productSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductSetDTO result = productSetService.save(productSetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productSetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-sets : get all the productSets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productSets in body
     */
    @GetMapping("/product-sets")
    public List<ProductSetDTO> getAllProductSets() {
        log.debug("REST request to get all ProductSets");
        return productSetService.findAll();
    }

    /**
     * GET  /product-sets/:id : get the "id" productSet.
     *
     * @param id the id of the productSetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productSetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-sets/{id}")
    public ResponseEntity<ProductSetDTO> getProductSet(@PathVariable Long id) {
        log.debug("REST request to get ProductSet : {}", id);
        Optional<ProductSetDTO> productSetDTO = productSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productSetDTO);
    }

    /**
     * DELETE  /product-sets/:id : delete the "id" productSet.
     *
     * @param id the id of the productSetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-sets/{id}")
    public ResponseEntity<Void> deleteProductSet(@PathVariable Long id) {
        log.debug("REST request to delete ProductSet : {}", id);
        productSetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
