package com.resource.server.web.rest;
import com.resource.server.service.WishlistsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.WishlistsDTO;
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
 * REST controller for managing Wishlists.
 */
@RestController
@RequestMapping("/api")
public class WishlistsResource {

    private final Logger log = LoggerFactory.getLogger(WishlistsResource.class);

    private static final String ENTITY_NAME = "wishlists";

    private final WishlistsService wishlistsService;

    public WishlistsResource(WishlistsService wishlistsService) {
        this.wishlistsService = wishlistsService;
    }

    /**
     * POST  /wishlists : Create a new wishlists.
     *
     * @param wishlistsDTO the wishlistsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wishlistsDTO, or with status 400 (Bad Request) if the wishlists has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wishlists")
    public ResponseEntity<WishlistsDTO> createWishlists(@RequestBody WishlistsDTO wishlistsDTO) throws URISyntaxException {
        log.debug("REST request to save Wishlists : {}", wishlistsDTO);
        if (wishlistsDTO.getId() != null) {
            throw new BadRequestAlertException("A new wishlists cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WishlistsDTO result = wishlistsService.save(wishlistsDTO);
        return ResponseEntity.created(new URI("/api/wishlists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wishlists : Updates an existing wishlists.
     *
     * @param wishlistsDTO the wishlistsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wishlistsDTO,
     * or with status 400 (Bad Request) if the wishlistsDTO is not valid,
     * or with status 500 (Internal Server Error) if the wishlistsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wishlists")
    public ResponseEntity<WishlistsDTO> updateWishlists(@RequestBody WishlistsDTO wishlistsDTO) throws URISyntaxException {
        log.debug("REST request to update Wishlists : {}", wishlistsDTO);
        if (wishlistsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WishlistsDTO result = wishlistsService.save(wishlistsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wishlistsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wishlists : get all the wishlists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of wishlists in body
     */
    @GetMapping("/wishlists")
    public List<WishlistsDTO> getAllWishlists() {
        log.debug("REST request to get all Wishlists");
        return wishlistsService.findAll();
    }

    /**
     * GET  /wishlists/:id : get the "id" wishlists.
     *
     * @param id the id of the wishlistsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wishlistsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wishlists/{id}")
    public ResponseEntity<WishlistsDTO> getWishlists(@PathVariable Long id) {
        log.debug("REST request to get Wishlists : {}", id);
        Optional<WishlistsDTO> wishlistsDTO = wishlistsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wishlistsDTO);
    }

    /**
     * DELETE  /wishlists/:id : delete the "id" wishlists.
     *
     * @param id the id of the wishlistsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wishlists/{id}")
    public ResponseEntity<Void> deleteWishlists(@PathVariable Long id) {
        log.debug("REST request to delete Wishlists : {}", id);
        wishlistsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
