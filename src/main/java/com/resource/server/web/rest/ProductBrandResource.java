package com.resource.server.web.rest;
import com.resource.server.service.ProductBrandService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductBrandDTO;
import com.resource.server.service.dto.ProductBrandCriteria;
import com.resource.server.service.ProductBrandQueryService;
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
 * REST controller for managing ProductBrand.
 */
@RestController
@RequestMapping("/api")
public class ProductBrandResource {

    private final Logger log = LoggerFactory.getLogger(ProductBrandResource.class);

    private static final String ENTITY_NAME = "productBrand";

    private final ProductBrandService productBrandService;

    private final ProductBrandQueryService productBrandQueryService;

    public ProductBrandResource(ProductBrandService productBrandService, ProductBrandQueryService productBrandQueryService) {
        this.productBrandService = productBrandService;
        this.productBrandQueryService = productBrandQueryService;
    }

    /**
     * POST  /product-brands : Create a new productBrand.
     *
     * @param productBrandDTO the productBrandDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productBrandDTO, or with status 400 (Bad Request) if the productBrand has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-brands")
    public ResponseEntity<ProductBrandDTO> createProductBrand(@Valid @RequestBody ProductBrandDTO productBrandDTO) throws URISyntaxException {
        log.debug("REST request to save ProductBrand : {}", productBrandDTO);
        if (productBrandDTO.getId() != null) {
            throw new BadRequestAlertException("A new productBrand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductBrandDTO result = productBrandService.save(productBrandDTO);
        return ResponseEntity.created(new URI("/api/product-brands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-brands : Updates an existing productBrand.
     *
     * @param productBrandDTO the productBrandDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productBrandDTO,
     * or with status 400 (Bad Request) if the productBrandDTO is not valid,
     * or with status 500 (Internal Server Error) if the productBrandDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-brands")
    public ResponseEntity<ProductBrandDTO> updateProductBrand(@Valid @RequestBody ProductBrandDTO productBrandDTO) throws URISyntaxException {
        log.debug("REST request to update ProductBrand : {}", productBrandDTO);
        if (productBrandDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductBrandDTO result = productBrandService.save(productBrandDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productBrandDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-brands : get all the productBrands.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of productBrands in body
     */
    @GetMapping("/product-brands")
    public ResponseEntity<List<ProductBrandDTO>> getAllProductBrands(ProductBrandCriteria criteria) {
        log.debug("REST request to get ProductBrands by criteria: {}", criteria);
        List<ProductBrandDTO> entityList = productBrandQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /product-brands/count : count all the productBrands.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/product-brands/count")
    public ResponseEntity<Long> countProductBrands(ProductBrandCriteria criteria) {
        log.debug("REST request to count ProductBrands by criteria: {}", criteria);
        return ResponseEntity.ok().body(productBrandQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /product-brands/:id : get the "id" productBrand.
     *
     * @param id the id of the productBrandDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productBrandDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-brands/{id}")
    public ResponseEntity<ProductBrandDTO> getProductBrand(@PathVariable Long id) {
        log.debug("REST request to get ProductBrand : {}", id);
        Optional<ProductBrandDTO> productBrandDTO = productBrandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productBrandDTO);
    }

    /**
     * DELETE  /product-brands/:id : delete the "id" productBrand.
     *
     * @param id the id of the productBrandDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-brands/{id}")
    public ResponseEntity<Void> deleteProductBrand(@PathVariable Long id) {
        log.debug("REST request to delete ProductBrand : {}", id);
        productBrandService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
