package com.resource.server.web.rest;
import com.resource.server.service.CompareProductsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.CompareProductsDTO;
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
 * REST controller for managing CompareProducts.
 */
@RestController
@RequestMapping("/api")
public class CompareProductsResource {

    private final Logger log = LoggerFactory.getLogger(CompareProductsResource.class);

    private static final String ENTITY_NAME = "compareProducts";

    private final CompareProductsService compareProductsService;

    public CompareProductsResource(CompareProductsService compareProductsService) {
        this.compareProductsService = compareProductsService;
    }

    /**
     * POST  /compare-products : Create a new compareProducts.
     *
     * @param compareProductsDTO the compareProductsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compareProductsDTO, or with status 400 (Bad Request) if the compareProducts has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compare-products")
    public ResponseEntity<CompareProductsDTO> createCompareProducts(@RequestBody CompareProductsDTO compareProductsDTO) throws URISyntaxException {
        log.debug("REST request to save CompareProducts : {}", compareProductsDTO);
        if (compareProductsDTO.getId() != null) {
            throw new BadRequestAlertException("A new compareProducts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompareProductsDTO result = compareProductsService.save(compareProductsDTO);
        return ResponseEntity.created(new URI("/api/compare-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /compare-products : Updates an existing compareProducts.
     *
     * @param compareProductsDTO the compareProductsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compareProductsDTO,
     * or with status 400 (Bad Request) if the compareProductsDTO is not valid,
     * or with status 500 (Internal Server Error) if the compareProductsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compare-products")
    public ResponseEntity<CompareProductsDTO> updateCompareProducts(@RequestBody CompareProductsDTO compareProductsDTO) throws URISyntaxException {
        log.debug("REST request to update CompareProducts : {}", compareProductsDTO);
        if (compareProductsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompareProductsDTO result = compareProductsService.save(compareProductsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compareProductsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /compare-products : get all the compareProducts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of compareProducts in body
     */
    @GetMapping("/compare-products")
    public List<CompareProductsDTO> getAllCompareProducts() {
        log.debug("REST request to get all CompareProducts");
        return compareProductsService.findAll();
    }

    /**
     * GET  /compare-products/:id : get the "id" compareProducts.
     *
     * @param id the id of the compareProductsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compareProductsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/compare-products/{id}")
    public ResponseEntity<CompareProductsDTO> getCompareProducts(@PathVariable Long id) {
        log.debug("REST request to get CompareProducts : {}", id);
        Optional<CompareProductsDTO> compareProductsDTO = compareProductsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compareProductsDTO);
    }

    /**
     * DELETE  /compare-products/:id : delete the "id" compareProducts.
     *
     * @param id the id of the compareProductsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/compare-products/{id}")
    public ResponseEntity<Void> deleteCompareProducts(@PathVariable Long id) {
        log.debug("REST request to delete CompareProducts : {}", id);
        compareProductsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
