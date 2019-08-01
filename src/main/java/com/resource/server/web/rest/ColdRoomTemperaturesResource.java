package com.resource.server.web.rest;
import com.resource.server.service.ColdRoomTemperaturesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.ColdRoomTemperaturesDTO;
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
 * REST controller for managing ColdRoomTemperatures.
 */
@RestController
@RequestMapping("/api")
public class ColdRoomTemperaturesResource {

    private final Logger log = LoggerFactory.getLogger(ColdRoomTemperaturesResource.class);

    private static final String ENTITY_NAME = "coldRoomTemperatures";

    private final ColdRoomTemperaturesService coldRoomTemperaturesService;

    public ColdRoomTemperaturesResource(ColdRoomTemperaturesService coldRoomTemperaturesService) {
        this.coldRoomTemperaturesService = coldRoomTemperaturesService;
    }

    /**
     * POST  /cold-room-temperatures : Create a new coldRoomTemperatures.
     *
     * @param coldRoomTemperaturesDTO the coldRoomTemperaturesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coldRoomTemperaturesDTO, or with status 400 (Bad Request) if the coldRoomTemperatures has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cold-room-temperatures")
    public ResponseEntity<ColdRoomTemperaturesDTO> createColdRoomTemperatures(@Valid @RequestBody ColdRoomTemperaturesDTO coldRoomTemperaturesDTO) throws URISyntaxException {
        log.debug("REST request to save ColdRoomTemperatures : {}", coldRoomTemperaturesDTO);
        if (coldRoomTemperaturesDTO.getId() != null) {
            throw new BadRequestAlertException("A new coldRoomTemperatures cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ColdRoomTemperaturesDTO result = coldRoomTemperaturesService.save(coldRoomTemperaturesDTO);
        return ResponseEntity.created(new URI("/api/cold-room-temperatures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cold-room-temperatures : Updates an existing coldRoomTemperatures.
     *
     * @param coldRoomTemperaturesDTO the coldRoomTemperaturesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coldRoomTemperaturesDTO,
     * or with status 400 (Bad Request) if the coldRoomTemperaturesDTO is not valid,
     * or with status 500 (Internal Server Error) if the coldRoomTemperaturesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cold-room-temperatures")
    public ResponseEntity<ColdRoomTemperaturesDTO> updateColdRoomTemperatures(@Valid @RequestBody ColdRoomTemperaturesDTO coldRoomTemperaturesDTO) throws URISyntaxException {
        log.debug("REST request to update ColdRoomTemperatures : {}", coldRoomTemperaturesDTO);
        if (coldRoomTemperaturesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ColdRoomTemperaturesDTO result = coldRoomTemperaturesService.save(coldRoomTemperaturesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coldRoomTemperaturesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cold-room-temperatures : get all the coldRoomTemperatures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of coldRoomTemperatures in body
     */
    @GetMapping("/cold-room-temperatures")
    public List<ColdRoomTemperaturesDTO> getAllColdRoomTemperatures() {
        log.debug("REST request to get all ColdRoomTemperatures");
        return coldRoomTemperaturesService.findAll();
    }

    /**
     * GET  /cold-room-temperatures/:id : get the "id" coldRoomTemperatures.
     *
     * @param id the id of the coldRoomTemperaturesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coldRoomTemperaturesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cold-room-temperatures/{id}")
    public ResponseEntity<ColdRoomTemperaturesDTO> getColdRoomTemperatures(@PathVariable Long id) {
        log.debug("REST request to get ColdRoomTemperatures : {}", id);
        Optional<ColdRoomTemperaturesDTO> coldRoomTemperaturesDTO = coldRoomTemperaturesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coldRoomTemperaturesDTO);
    }

    /**
     * DELETE  /cold-room-temperatures/:id : delete the "id" coldRoomTemperatures.
     *
     * @param id the id of the coldRoomTemperaturesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cold-room-temperatures/{id}")
    public ResponseEntity<Void> deleteColdRoomTemperatures(@PathVariable Long id) {
        log.debug("REST request to delete ColdRoomTemperatures : {}", id);
        coldRoomTemperaturesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
