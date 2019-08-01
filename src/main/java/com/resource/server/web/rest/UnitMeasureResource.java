package com.resource.server.web.rest;
import com.resource.server.service.UnitMeasureService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.UnitMeasureDTO;
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
 * REST controller for managing UnitMeasure.
 */
@RestController
@RequestMapping("/api")
public class UnitMeasureResource {

    private final Logger log = LoggerFactory.getLogger(UnitMeasureResource.class);

    private static final String ENTITY_NAME = "unitMeasure";

    private final UnitMeasureService unitMeasureService;

    public UnitMeasureResource(UnitMeasureService unitMeasureService) {
        this.unitMeasureService = unitMeasureService;
    }

    /**
     * POST  /unit-measures : Create a new unitMeasure.
     *
     * @param unitMeasureDTO the unitMeasureDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new unitMeasureDTO, or with status 400 (Bad Request) if the unitMeasure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/unit-measures")
    public ResponseEntity<UnitMeasureDTO> createUnitMeasure(@Valid @RequestBody UnitMeasureDTO unitMeasureDTO) throws URISyntaxException {
        log.debug("REST request to save UnitMeasure : {}", unitMeasureDTO);
        if (unitMeasureDTO.getId() != null) {
            throw new BadRequestAlertException("A new unitMeasure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnitMeasureDTO result = unitMeasureService.save(unitMeasureDTO);
        return ResponseEntity.created(new URI("/api/unit-measures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /unit-measures : Updates an existing unitMeasure.
     *
     * @param unitMeasureDTO the unitMeasureDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated unitMeasureDTO,
     * or with status 400 (Bad Request) if the unitMeasureDTO is not valid,
     * or with status 500 (Internal Server Error) if the unitMeasureDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/unit-measures")
    public ResponseEntity<UnitMeasureDTO> updateUnitMeasure(@Valid @RequestBody UnitMeasureDTO unitMeasureDTO) throws URISyntaxException {
        log.debug("REST request to update UnitMeasure : {}", unitMeasureDTO);
        if (unitMeasureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnitMeasureDTO result = unitMeasureService.save(unitMeasureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, unitMeasureDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /unit-measures : get all the unitMeasures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of unitMeasures in body
     */
    @GetMapping("/unit-measures")
    public List<UnitMeasureDTO> getAllUnitMeasures() {
        log.debug("REST request to get all UnitMeasures");
        return unitMeasureService.findAll();
    }

    /**
     * GET  /unit-measures/:id : get the "id" unitMeasure.
     *
     * @param id the id of the unitMeasureDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the unitMeasureDTO, or with status 404 (Not Found)
     */
    @GetMapping("/unit-measures/{id}")
    public ResponseEntity<UnitMeasureDTO> getUnitMeasure(@PathVariable Long id) {
        log.debug("REST request to get UnitMeasure : {}", id);
        Optional<UnitMeasureDTO> unitMeasureDTO = unitMeasureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unitMeasureDTO);
    }

    /**
     * DELETE  /unit-measures/:id : delete the "id" unitMeasure.
     *
     * @param id the id of the unitMeasureDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/unit-measures/{id}")
    public ResponseEntity<Void> deleteUnitMeasure(@PathVariable Long id) {
        log.debug("REST request to delete UnitMeasure : {}", id);
        unitMeasureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
