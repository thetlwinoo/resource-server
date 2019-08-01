package com.resource.server.web.rest;
import com.resource.server.service.WishlistProductsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.WishlistProductsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WishlistProducts.
 */
@RestController
@RequestMapping("/api")
public class WishlistProductsResource {

    private final Logger log = LoggerFactory.getLogger(WishlistProductsResource.class);

    private static final String ENTITY_NAME = "wishlistProducts";

    private final WishlistProductsService wishlistProductsService;

    public WishlistProductsResource(WishlistProductsService wishlistProductsService) {
        this.wishlistProductsService = wishlistProductsService;
    }

    /**
     * POST  /wishlist-products : Create a new wishlistProducts.
     *
     * @param wishlistProductsDTO the wishlistProductsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wishlistProductsDTO, or with status 400 (Bad Request) if the wishlistProducts has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wishlist-products")
    public ResponseEntity<WishlistProductsDTO> createWishlistProducts(@RequestBody WishlistProductsDTO wishlistProductsDTO) throws URISyntaxException {
        log.debug("REST request to save WishlistProducts : {}", wishlistProductsDTO);
        if (wishlistProductsDTO.getId() != null) {
            throw new BadRequestAlertException("A new wishlistProducts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WishlistProductsDTO result = wishlistProductsService.save(wishlistProductsDTO);
        return ResponseEntity.created(new URI("/api/wishlist-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wishlist-products : Updates an existing wishlistProducts.
     *
     * @param wishlistProductsDTO the wishlistProductsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wishlistProductsDTO,
     * or with status 400 (Bad Request) if the wishlistProductsDTO is not valid,
     * or with status 500 (Internal Server Error) if the wishlistProductsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wishlist-products")
    public ResponseEntity<WishlistProductsDTO> updateWishlistProducts(@RequestBody WishlistProductsDTO wishlistProductsDTO) throws URISyntaxException {
        log.debug("REST request to update WishlistProducts : {}", wishlistProductsDTO);
        if (wishlistProductsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WishlistProductsDTO result = wishlistProductsService.save(wishlistProductsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wishlistProductsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wishlist-products : get all the wishlistProducts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of wishlistProducts in body
     */
    @GetMapping("/wishlist-products")
    public List<WishlistProductsDTO> getAllWishlistProducts() {
        log.debug("REST request to get all WishlistProducts");
        return wishlistProductsService.findAll();
    }

    /**
     * GET  /wishlist-products/:id : get the "id" wishlistProducts.
     *
     * @param id the id of the wishlistProductsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wishlistProductsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wishlist-products/{id}")
    public ResponseEntity<WishlistProductsDTO> getWishlistProducts(@PathVariable Long id) {
        log.debug("REST request to get WishlistProducts : {}", id);
        Optional<WishlistProductsDTO> wishlistProductsDTO = wishlistProductsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wishlistProductsDTO);
    }

    /**
     * DELETE  /wishlist-products/:id : delete the "id" wishlistProducts.
     *
     * @param id the id of the wishlistProductsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wishlist-products/{id}")
    public ResponseEntity<Void> deleteWishlistProducts(@PathVariable Long id) {
        log.debug("REST request to delete WishlistProducts : {}", id);
        wishlistProductsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
