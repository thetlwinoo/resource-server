package com.resource.server.web.rest;
import com.resource.server.service.ShoppingCartsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ShoppingCartsDTO;
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
 * REST controller for managing ShoppingCarts.
 */
@RestController
@RequestMapping("/api")
public class ShoppingCartsResource {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartsResource.class);

    private static final String ENTITY_NAME = "shoppingCarts";

    private final ShoppingCartsService shoppingCartsService;

    public ShoppingCartsResource(ShoppingCartsService shoppingCartsService) {
        this.shoppingCartsService = shoppingCartsService;
    }

    /**
     * POST  /shopping-carts : Create a new shoppingCarts.
     *
     * @param shoppingCartsDTO the shoppingCartsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shoppingCartsDTO, or with status 400 (Bad Request) if the shoppingCarts has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shopping-carts")
    public ResponseEntity<ShoppingCartsDTO> createShoppingCarts(@RequestBody ShoppingCartsDTO shoppingCartsDTO) throws URISyntaxException {
        log.debug("REST request to save ShoppingCarts : {}", shoppingCartsDTO);
        if (shoppingCartsDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoppingCarts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoppingCartsDTO result = shoppingCartsService.save(shoppingCartsDTO);
        return ResponseEntity.created(new URI("/api/shopping-carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shopping-carts : Updates an existing shoppingCarts.
     *
     * @param shoppingCartsDTO the shoppingCartsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shoppingCartsDTO,
     * or with status 400 (Bad Request) if the shoppingCartsDTO is not valid,
     * or with status 500 (Internal Server Error) if the shoppingCartsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shopping-carts")
    public ResponseEntity<ShoppingCartsDTO> updateShoppingCarts(@RequestBody ShoppingCartsDTO shoppingCartsDTO) throws URISyntaxException {
        log.debug("REST request to update ShoppingCarts : {}", shoppingCartsDTO);
        if (shoppingCartsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShoppingCartsDTO result = shoppingCartsService.save(shoppingCartsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shoppingCartsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shopping-carts : get all the shoppingCarts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shoppingCarts in body
     */
    @GetMapping("/shopping-carts")
    public List<ShoppingCartsDTO> getAllShoppingCarts() {
        log.debug("REST request to get all ShoppingCarts");
        return shoppingCartsService.findAll();
    }

    /**
     * GET  /shopping-carts/:id : get the "id" shoppingCarts.
     *
     * @param id the id of the shoppingCartsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shoppingCartsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shopping-carts/{id}")
    public ResponseEntity<ShoppingCartsDTO> getShoppingCarts(@PathVariable Long id) {
        log.debug("REST request to get ShoppingCarts : {}", id);
        Optional<ShoppingCartsDTO> shoppingCartsDTO = shoppingCartsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shoppingCartsDTO);
    }

    /**
     * DELETE  /shopping-carts/:id : delete the "id" shoppingCarts.
     *
     * @param id the id of the shoppingCartsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shopping-carts/{id}")
    public ResponseEntity<Void> deleteShoppingCarts(@PathVariable Long id) {
        log.debug("REST request to delete ShoppingCarts : {}", id);
        shoppingCartsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
