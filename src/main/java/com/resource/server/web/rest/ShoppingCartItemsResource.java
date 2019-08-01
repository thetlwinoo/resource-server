package com.resource.server.web.rest;
import com.resource.server.service.ShoppingCartItemsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ShoppingCartItemsDTO;
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
 * REST controller for managing ShoppingCartItems.
 */
@RestController
@RequestMapping("/api")
public class ShoppingCartItemsResource {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartItemsResource.class);

    private static final String ENTITY_NAME = "shoppingCartItems";

    private final ShoppingCartItemsService shoppingCartItemsService;

    public ShoppingCartItemsResource(ShoppingCartItemsService shoppingCartItemsService) {
        this.shoppingCartItemsService = shoppingCartItemsService;
    }

    /**
     * POST  /shopping-cart-items : Create a new shoppingCartItems.
     *
     * @param shoppingCartItemsDTO the shoppingCartItemsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shoppingCartItemsDTO, or with status 400 (Bad Request) if the shoppingCartItems has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shopping-cart-items")
    public ResponseEntity<ShoppingCartItemsDTO> createShoppingCartItems(@RequestBody ShoppingCartItemsDTO shoppingCartItemsDTO) throws URISyntaxException {
        log.debug("REST request to save ShoppingCartItems : {}", shoppingCartItemsDTO);
        if (shoppingCartItemsDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoppingCartItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoppingCartItemsDTO result = shoppingCartItemsService.save(shoppingCartItemsDTO);
        return ResponseEntity.created(new URI("/api/shopping-cart-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shopping-cart-items : Updates an existing shoppingCartItems.
     *
     * @param shoppingCartItemsDTO the shoppingCartItemsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shoppingCartItemsDTO,
     * or with status 400 (Bad Request) if the shoppingCartItemsDTO is not valid,
     * or with status 500 (Internal Server Error) if the shoppingCartItemsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shopping-cart-items")
    public ResponseEntity<ShoppingCartItemsDTO> updateShoppingCartItems(@RequestBody ShoppingCartItemsDTO shoppingCartItemsDTO) throws URISyntaxException {
        log.debug("REST request to update ShoppingCartItems : {}", shoppingCartItemsDTO);
        if (shoppingCartItemsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShoppingCartItemsDTO result = shoppingCartItemsService.save(shoppingCartItemsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shoppingCartItemsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shopping-cart-items : get all the shoppingCartItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shoppingCartItems in body
     */
    @GetMapping("/shopping-cart-items")
    public List<ShoppingCartItemsDTO> getAllShoppingCartItems() {
        log.debug("REST request to get all ShoppingCartItems");
        return shoppingCartItemsService.findAll();
    }

    /**
     * GET  /shopping-cart-items/:id : get the "id" shoppingCartItems.
     *
     * @param id the id of the shoppingCartItemsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shoppingCartItemsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shopping-cart-items/{id}")
    public ResponseEntity<ShoppingCartItemsDTO> getShoppingCartItems(@PathVariable Long id) {
        log.debug("REST request to get ShoppingCartItems : {}", id);
        Optional<ShoppingCartItemsDTO> shoppingCartItemsDTO = shoppingCartItemsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shoppingCartItemsDTO);
    }

    /**
     * DELETE  /shopping-cart-items/:id : delete the "id" shoppingCartItems.
     *
     * @param id the id of the shoppingCartItemsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shopping-cart-items/{id}")
    public ResponseEntity<Void> deleteShoppingCartItems(@PathVariable Long id) {
        log.debug("REST request to delete ShoppingCartItems : {}", id);
        shoppingCartItemsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
