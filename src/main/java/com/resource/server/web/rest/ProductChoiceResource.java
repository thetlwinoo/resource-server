package com.resource.server.web.rest;
import com.resource.server.service.ProductChoiceService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductChoiceDTO;
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
 * REST controller for managing ProductChoice.
 */
@RestController
@RequestMapping("/api")
public class ProductChoiceResource {

    private final Logger log = LoggerFactory.getLogger(ProductChoiceResource.class);

    private static final String ENTITY_NAME = "productChoice";

    private final ProductChoiceService productChoiceService;

    public ProductChoiceResource(ProductChoiceService productChoiceService) {
        this.productChoiceService = productChoiceService;
    }

    /**
     * POST  /product-choices : Create a new productChoice.
     *
     * @param productChoiceDTO the productChoiceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productChoiceDTO, or with status 400 (Bad Request) if the productChoice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-choices")
    public ResponseEntity<ProductChoiceDTO> createProductChoice(@Valid @RequestBody ProductChoiceDTO productChoiceDTO) throws URISyntaxException {
        log.debug("REST request to save ProductChoice : {}", productChoiceDTO);
        if (productChoiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new productChoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductChoiceDTO result = productChoiceService.save(productChoiceDTO);
        return ResponseEntity.created(new URI("/api/product-choices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-choices : Updates an existing productChoice.
     *
     * @param productChoiceDTO the productChoiceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productChoiceDTO,
     * or with status 400 (Bad Request) if the productChoiceDTO is not valid,
     * or with status 500 (Internal Server Error) if the productChoiceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-choices")
    public ResponseEntity<ProductChoiceDTO> updateProductChoice(@Valid @RequestBody ProductChoiceDTO productChoiceDTO) throws URISyntaxException {
        log.debug("REST request to update ProductChoice : {}", productChoiceDTO);
        if (productChoiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductChoiceDTO result = productChoiceService.save(productChoiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productChoiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-choices : get all the productChoices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productChoices in body
     */
    @GetMapping("/product-choices")
    public List<ProductChoiceDTO> getAllProductChoices() {
        log.debug("REST request to get all ProductChoices");
        return productChoiceService.findAll();
    }

    /**
     * GET  /product-choices/:id : get the "id" productChoice.
     *
     * @param id the id of the productChoiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productChoiceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-choices/{id}")
    public ResponseEntity<ProductChoiceDTO> getProductChoice(@PathVariable Long id) {
        log.debug("REST request to get ProductChoice : {}", id);
        Optional<ProductChoiceDTO> productChoiceDTO = productChoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productChoiceDTO);
    }

    /**
     * DELETE  /product-choices/:id : delete the "id" productChoice.
     *
     * @param id the id of the productChoiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-choices/{id}")
    public ResponseEntity<Void> deleteProductChoice(@PathVariable Long id) {
        log.debug("REST request to delete ProductChoice : {}", id);
        productChoiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}