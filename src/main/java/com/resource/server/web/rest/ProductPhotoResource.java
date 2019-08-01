package com.resource.server.web.rest;
import com.resource.server.service.ProductPhotoService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ProductPhotoDTO;
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
 * REST controller for managing ProductPhoto.
 */
@RestController
@RequestMapping("/api")
public class ProductPhotoResource {

    private final Logger log = LoggerFactory.getLogger(ProductPhotoResource.class);

    private static final String ENTITY_NAME = "productPhoto";

    private final ProductPhotoService productPhotoService;

    public ProductPhotoResource(ProductPhotoService productPhotoService) {
        this.productPhotoService = productPhotoService;
    }

    /**
     * POST  /product-photos : Create a new productPhoto.
     *
     * @param productPhotoDTO the productPhotoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productPhotoDTO, or with status 400 (Bad Request) if the productPhoto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-photos")
    public ResponseEntity<ProductPhotoDTO> createProductPhoto(@Valid @RequestBody ProductPhotoDTO productPhotoDTO) throws URISyntaxException {
        log.debug("REST request to save ProductPhoto : {}", productPhotoDTO);
        if (productPhotoDTO.getId() != null) {
            throw new BadRequestAlertException("A new productPhoto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductPhotoDTO result = productPhotoService.save(productPhotoDTO);
        return ResponseEntity.created(new URI("/api/product-photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-photos : Updates an existing productPhoto.
     *
     * @param productPhotoDTO the productPhotoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productPhotoDTO,
     * or with status 400 (Bad Request) if the productPhotoDTO is not valid,
     * or with status 500 (Internal Server Error) if the productPhotoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-photos")
    public ResponseEntity<ProductPhotoDTO> updateProductPhoto(@Valid @RequestBody ProductPhotoDTO productPhotoDTO) throws URISyntaxException {
        log.debug("REST request to update ProductPhoto : {}", productPhotoDTO);
        if (productPhotoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductPhotoDTO result = productPhotoService.save(productPhotoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productPhotoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-photos : get all the productPhotos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productPhotos in body
     */
    @GetMapping("/product-photos")
    public List<ProductPhotoDTO> getAllProductPhotos() {
        log.debug("REST request to get all ProductPhotos");
        return productPhotoService.findAll();
    }

    /**
     * GET  /product-photos/:id : get the "id" productPhoto.
     *
     * @param id the id of the productPhotoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productPhotoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-photos/{id}")
    public ResponseEntity<ProductPhotoDTO> getProductPhoto(@PathVariable Long id) {
        log.debug("REST request to get ProductPhoto : {}", id);
        Optional<ProductPhotoDTO> productPhotoDTO = productPhotoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productPhotoDTO);
    }

    /**
     * DELETE  /product-photos/:id : delete the "id" productPhoto.
     *
     * @param id the id of the productPhotoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-photos/{id}")
    public ResponseEntity<Void> deleteProductPhoto(@PathVariable Long id) {
        log.debug("REST request to delete ProductPhoto : {}", id);
        productPhotoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
