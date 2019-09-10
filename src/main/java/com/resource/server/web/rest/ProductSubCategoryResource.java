package com.resource.server.web.rest;
import com.resource.server.service.ProductSubCategoryService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.web.rest.util.PaginationUtil;
import com.resource.server.service.dto.ProductSubCategoryDTO;
import com.resource.server.service.dto.ProductSubCategoryCriteria;
import com.resource.server.service.ProductSubCategoryQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProductSubCategory.
 */
@RestController
@RequestMapping("/api")
public class ProductSubCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ProductSubCategoryResource.class);

    private static final String ENTITY_NAME = "productSubCategory";

    private final ProductSubCategoryService productSubCategoryService;

    private final ProductSubCategoryQueryService productSubCategoryQueryService;

    public ProductSubCategoryResource(ProductSubCategoryService productSubCategoryService, ProductSubCategoryQueryService productSubCategoryQueryService) {
        this.productSubCategoryService = productSubCategoryService;
        this.productSubCategoryQueryService = productSubCategoryQueryService;
    }

    /**
     * POST  /product-sub-categories : Create a new productSubCategory.
     *
     * @param productSubCategoryDTO the productSubCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productSubCategoryDTO, or with status 400 (Bad Request) if the productSubCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-sub-categories")
    public ResponseEntity<ProductSubCategoryDTO> createProductSubCategory(@Valid @RequestBody ProductSubCategoryDTO productSubCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save ProductSubCategory : {}", productSubCategoryDTO);
        if (productSubCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new productSubCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductSubCategoryDTO result = productSubCategoryService.save(productSubCategoryDTO);
        return ResponseEntity.created(new URI("/api/product-sub-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-sub-categories : Updates an existing productSubCategory.
     *
     * @param productSubCategoryDTO the productSubCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productSubCategoryDTO,
     * or with status 400 (Bad Request) if the productSubCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the productSubCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-sub-categories")
    public ResponseEntity<ProductSubCategoryDTO> updateProductSubCategory(@Valid @RequestBody ProductSubCategoryDTO productSubCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update ProductSubCategory : {}", productSubCategoryDTO);
        if (productSubCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductSubCategoryDTO result = productSubCategoryService.save(productSubCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productSubCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-sub-categories : get all the productSubCategories.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of productSubCategories in body
     */
    @GetMapping("/product-sub-categories")
    public ResponseEntity<List<ProductSubCategoryDTO>> getAllProductSubCategories(ProductSubCategoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductSubCategories by criteria: {}", criteria);
        Page<ProductSubCategoryDTO> page = productSubCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-sub-categories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /product-sub-categories/count : count all the productSubCategories.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/product-sub-categories/count")
    public ResponseEntity<Long> countProductSubCategories(ProductSubCategoryCriteria criteria) {
        log.debug("REST request to count ProductSubCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(productSubCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /product-sub-categories/:id : get the "id" productSubCategory.
     *
     * @param id the id of the productSubCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productSubCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-sub-categories/{id}")
    public ResponseEntity<ProductSubCategoryDTO> getProductSubCategory(@PathVariable Long id) {
        log.debug("REST request to get ProductSubCategory : {}", id);
        Optional<ProductSubCategoryDTO> productSubCategoryDTO = productSubCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productSubCategoryDTO);
    }

    /**
     * DELETE  /product-sub-categories/:id : delete the "id" productSubCategory.
     *
     * @param id the id of the productSubCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-sub-categories/{id}")
    public ResponseEntity<Void> deleteProductSubCategory(@PathVariable Long id) {
        log.debug("REST request to delete ProductSubCategory : {}", id);
        productSubCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
