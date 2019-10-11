package com.resource.server.web.rest;
import com.resource.server.service.ProductOptionService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductOptionDTO;
import com.resource.server.service.dto.ProductOptionCriteria;
import com.resource.server.service.ProductOptionQueryService;
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
 * REST controller for managing ProductOption.
 */
@RestController
@RequestMapping("/api")
public class ProductOptionResource {

    private final Logger log = LoggerFactory.getLogger(ProductOptionResource.class);

    private static final String ENTITY_NAME = "productOption";

    private final ProductOptionService productOptionService;

    private final ProductOptionQueryService productOptionQueryService;

    public ProductOptionResource(ProductOptionService productOptionService, ProductOptionQueryService productOptionQueryService) {
        this.productOptionService = productOptionService;
        this.productOptionQueryService = productOptionQueryService;
    }

    /**
     * POST  /product-options : Create a new productOption.
     *
     * @param productOptionDTO the productOptionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productOptionDTO, or with status 400 (Bad Request) if the productOption has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-options")
    public ResponseEntity<ProductOptionDTO> createProductOption(@Valid @RequestBody ProductOptionDTO productOptionDTO) throws URISyntaxException {
        log.debug("REST request to save ProductOption : {}", productOptionDTO);
        if (productOptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new productOption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductOptionDTO result = productOptionService.save(productOptionDTO);
        return ResponseEntity.created(new URI("/api/product-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-options : Updates an existing productOption.
     *
     * @param productOptionDTO the productOptionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productOptionDTO,
     * or with status 400 (Bad Request) if the productOptionDTO is not valid,
     * or with status 500 (Internal Server Error) if the productOptionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-options")
    public ResponseEntity<ProductOptionDTO> updateProductOption(@Valid @RequestBody ProductOptionDTO productOptionDTO) throws URISyntaxException {
        log.debug("REST request to update ProductOption : {}", productOptionDTO);
        if (productOptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductOptionDTO result = productOptionService.save(productOptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productOptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-options : get all the productOptions.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of productOptions in body
     */
    @GetMapping("/product-options")
    public ResponseEntity<List<ProductOptionDTO>> getAllProductOptions(ProductOptionCriteria criteria) {
        log.debug("REST request to get ProductOptions by criteria: {}", criteria);
        List<ProductOptionDTO> entityList = productOptionQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /product-options/count : count all the productOptions.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/product-options/count")
    public ResponseEntity<Long> countProductOptions(ProductOptionCriteria criteria) {
        log.debug("REST request to count ProductOptions by criteria: {}", criteria);
        return ResponseEntity.ok().body(productOptionQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /product-options/:id : get the "id" productOption.
     *
     * @param id the id of the productOptionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productOptionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-options/{id}")
    public ResponseEntity<ProductOptionDTO> getProductOption(@PathVariable Long id) {
        log.debug("REST request to get ProductOption : {}", id);
        Optional<ProductOptionDTO> productOptionDTO = productOptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productOptionDTO);
    }

    /**
     * DELETE  /product-options/:id : delete the "id" productOption.
     *
     * @param id the id of the productOptionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-options/{id}")
    public ResponseEntity<Void> deleteProductOption(@PathVariable Long id) {
        log.debug("REST request to delete ProductOption : {}", id);
        productOptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
