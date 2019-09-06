package com.resource.server.web.rest;
import com.resource.server.service.ProductTagsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductTagsDTO;
import com.resource.server.service.dto.ProductTagsCriteria;
import com.resource.server.service.ProductTagsQueryService;
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
 * REST controller for managing ProductTags.
 */
@RestController
@RequestMapping("/api")
public class ProductTagsResource {

    private final Logger log = LoggerFactory.getLogger(ProductTagsResource.class);

    private static final String ENTITY_NAME = "productTags";

    private final ProductTagsService productTagsService;

    private final ProductTagsQueryService productTagsQueryService;

    public ProductTagsResource(ProductTagsService productTagsService, ProductTagsQueryService productTagsQueryService) {
        this.productTagsService = productTagsService;
        this.productTagsQueryService = productTagsQueryService;
    }

    /**
     * POST  /product-tags : Create a new productTags.
     *
     * @param productTagsDTO the productTagsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productTagsDTO, or with status 400 (Bad Request) if the productTags has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-tags")
    public ResponseEntity<ProductTagsDTO> createProductTags(@Valid @RequestBody ProductTagsDTO productTagsDTO) throws URISyntaxException {
        log.debug("REST request to save ProductTags : {}", productTagsDTO);
        if (productTagsDTO.getId() != null) {
            throw new BadRequestAlertException("A new productTags cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductTagsDTO result = productTagsService.save(productTagsDTO);
        return ResponseEntity.created(new URI("/api/product-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-tags : Updates an existing productTags.
     *
     * @param productTagsDTO the productTagsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productTagsDTO,
     * or with status 400 (Bad Request) if the productTagsDTO is not valid,
     * or with status 500 (Internal Server Error) if the productTagsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-tags")
    public ResponseEntity<ProductTagsDTO> updateProductTags(@Valid @RequestBody ProductTagsDTO productTagsDTO) throws URISyntaxException {
        log.debug("REST request to update ProductTags : {}", productTagsDTO);
        if (productTagsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductTagsDTO result = productTagsService.save(productTagsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productTagsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-tags : get all the productTags.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of productTags in body
     */
    @GetMapping("/product-tags")
    public ResponseEntity<List<ProductTagsDTO>> getAllProductTags(ProductTagsCriteria criteria) {
        log.debug("REST request to get ProductTags by criteria: {}", criteria);
        List<ProductTagsDTO> entityList = productTagsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /product-tags/count : count all the productTags.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/product-tags/count")
    public ResponseEntity<Long> countProductTags(ProductTagsCriteria criteria) {
        log.debug("REST request to count ProductTags by criteria: {}", criteria);
        return ResponseEntity.ok().body(productTagsQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /product-tags/:id : get the "id" productTags.
     *
     * @param id the id of the productTagsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productTagsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-tags/{id}")
    public ResponseEntity<ProductTagsDTO> getProductTags(@PathVariable Long id) {
        log.debug("REST request to get ProductTags : {}", id);
        Optional<ProductTagsDTO> productTagsDTO = productTagsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productTagsDTO);
    }

    /**
     * DELETE  /product-tags/:id : delete the "id" productTags.
     *
     * @param id the id of the productTagsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-tags/{id}")
    public ResponseEntity<Void> deleteProductTags(@PathVariable Long id) {
        log.debug("REST request to delete ProductTags : {}", id);
        productTagsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
