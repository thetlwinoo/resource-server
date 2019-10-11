package com.resource.server.web.rest;
import com.resource.server.service.ProductDocumentService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductDocumentDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing ProductDocument.
 */
@RestController
@RequestMapping("/api")
public class ProductDocumentResource {

    private final Logger log = LoggerFactory.getLogger(ProductDocumentResource.class);

    private static final String ENTITY_NAME = "productDocument";

    private final ProductDocumentService productDocumentService;

    public ProductDocumentResource(ProductDocumentService productDocumentService) {
        this.productDocumentService = productDocumentService;
    }

    /**
     * POST  /product-documents : Create a new productDocument.
     *
     * @param productDocumentDTO the productDocumentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productDocumentDTO, or with status 400 (Bad Request) if the productDocument has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-documents")
    public ResponseEntity<ProductDocumentDTO> createProductDocument(@RequestBody ProductDocumentDTO productDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save ProductDocument : {}", productDocumentDTO);
        if (productDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new productDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductDocumentDTO result = productDocumentService.save(productDocumentDTO);
        return ResponseEntity.created(new URI("/api/product-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-documents : Updates an existing productDocument.
     *
     * @param productDocumentDTO the productDocumentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productDocumentDTO,
     * or with status 400 (Bad Request) if the productDocumentDTO is not valid,
     * or with status 500 (Internal Server Error) if the productDocumentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-documents")
    public ResponseEntity<ProductDocumentDTO> updateProductDocument(@RequestBody ProductDocumentDTO productDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update ProductDocument : {}", productDocumentDTO);
        if (productDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductDocumentDTO result = productDocumentService.save(productDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-documents : get all the productDocuments.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of productDocuments in body
     */
    @GetMapping("/product-documents")
    public List<ProductDocumentDTO> getAllProductDocuments(@RequestParam(required = false) String filter) {
        if ("product-is-null".equals(filter)) {
            log.debug("REST request to get all ProductDocuments where product is null");
            return productDocumentService.findAllWhereProductIsNull();
        }
        log.debug("REST request to get all ProductDocuments");
        return productDocumentService.findAll();
    }

    /**
     * GET  /product-documents/:id : get the "id" productDocument.
     *
     * @param id the id of the productDocumentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productDocumentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-documents/{id}")
    public ResponseEntity<ProductDocumentDTO> getProductDocument(@PathVariable Long id) {
        log.debug("REST request to get ProductDocument : {}", id);
        Optional<ProductDocumentDTO> productDocumentDTO = productDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDocumentDTO);
    }

    /**
     * DELETE  /product-documents/:id : delete the "id" productDocument.
     *
     * @param id the id of the productDocumentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-documents/{id}")
    public ResponseEntity<Void> deleteProductDocument(@PathVariable Long id) {
        log.debug("REST request to delete ProductDocument : {}", id);
        productDocumentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
