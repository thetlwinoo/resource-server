package com.resource.server.web.rest;
import com.resource.server.service.BuyingGroupsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.BuyingGroupsDTO;
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
 * REST controller for managing BuyingGroups.
 */
@RestController
@RequestMapping("/api")
public class BuyingGroupsResource {

    private final Logger log = LoggerFactory.getLogger(BuyingGroupsResource.class);

    private static final String ENTITY_NAME = "buyingGroups";

    private final BuyingGroupsService buyingGroupsService;

    public BuyingGroupsResource(BuyingGroupsService buyingGroupsService) {
        this.buyingGroupsService = buyingGroupsService;
    }

    /**
     * POST  /buying-groups : Create a new buyingGroups.
     *
     * @param buyingGroupsDTO the buyingGroupsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new buyingGroupsDTO, or with status 400 (Bad Request) if the buyingGroups has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/buying-groups")
    public ResponseEntity<BuyingGroupsDTO> createBuyingGroups(@Valid @RequestBody BuyingGroupsDTO buyingGroupsDTO) throws URISyntaxException {
        log.debug("REST request to save BuyingGroups : {}", buyingGroupsDTO);
        if (buyingGroupsDTO.getId() != null) {
            throw new BadRequestAlertException("A new buyingGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuyingGroupsDTO result = buyingGroupsService.save(buyingGroupsDTO);
        return ResponseEntity.created(new URI("/api/buying-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /buying-groups : Updates an existing buyingGroups.
     *
     * @param buyingGroupsDTO the buyingGroupsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated buyingGroupsDTO,
     * or with status 400 (Bad Request) if the buyingGroupsDTO is not valid,
     * or with status 500 (Internal Server Error) if the buyingGroupsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/buying-groups")
    public ResponseEntity<BuyingGroupsDTO> updateBuyingGroups(@Valid @RequestBody BuyingGroupsDTO buyingGroupsDTO) throws URISyntaxException {
        log.debug("REST request to update BuyingGroups : {}", buyingGroupsDTO);
        if (buyingGroupsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BuyingGroupsDTO result = buyingGroupsService.save(buyingGroupsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, buyingGroupsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /buying-groups : get all the buyingGroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of buyingGroups in body
     */
    @GetMapping("/buying-groups")
    public List<BuyingGroupsDTO> getAllBuyingGroups() {
        log.debug("REST request to get all BuyingGroups");
        return buyingGroupsService.findAll();
    }

    /**
     * GET  /buying-groups/:id : get the "id" buyingGroups.
     *
     * @param id the id of the buyingGroupsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the buyingGroupsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/buying-groups/{id}")
    public ResponseEntity<BuyingGroupsDTO> getBuyingGroups(@PathVariable Long id) {
        log.debug("REST request to get BuyingGroups : {}", id);
        Optional<BuyingGroupsDTO> buyingGroupsDTO = buyingGroupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buyingGroupsDTO);
    }

    /**
     * DELETE  /buying-groups/:id : delete the "id" buyingGroups.
     *
     * @param id the id of the buyingGroupsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/buying-groups/{id}")
    public ResponseEntity<Void> deleteBuyingGroups(@PathVariable Long id) {
        log.debug("REST request to delete BuyingGroups : {}", id);
        buyingGroupsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
