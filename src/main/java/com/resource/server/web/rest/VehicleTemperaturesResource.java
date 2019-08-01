package com.resource.server.web.rest;
import com.resource.server.service.VehicleTemperaturesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.VehicleTemperaturesDTO;
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
 * REST controller for managing VehicleTemperatures.
 */
@RestController
@RequestMapping("/api")
public class VehicleTemperaturesResource {

    private final Logger log = LoggerFactory.getLogger(VehicleTemperaturesResource.class);

    private static final String ENTITY_NAME = "vehicleTemperatures";

    private final VehicleTemperaturesService vehicleTemperaturesService;

    public VehicleTemperaturesResource(VehicleTemperaturesService vehicleTemperaturesService) {
        this.vehicleTemperaturesService = vehicleTemperaturesService;
    }

    /**
     * POST  /vehicle-temperatures : Create a new vehicleTemperatures.
     *
     * @param vehicleTemperaturesDTO the vehicleTemperaturesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehicleTemperaturesDTO, or with status 400 (Bad Request) if the vehicleTemperatures has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicle-temperatures")
    public ResponseEntity<VehicleTemperaturesDTO> createVehicleTemperatures(@Valid @RequestBody VehicleTemperaturesDTO vehicleTemperaturesDTO) throws URISyntaxException {
        log.debug("REST request to save VehicleTemperatures : {}", vehicleTemperaturesDTO);
        if (vehicleTemperaturesDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleTemperatures cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleTemperaturesDTO result = vehicleTemperaturesService.save(vehicleTemperaturesDTO);
        return ResponseEntity.created(new URI("/api/vehicle-temperatures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicle-temperatures : Updates an existing vehicleTemperatures.
     *
     * @param vehicleTemperaturesDTO the vehicleTemperaturesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehicleTemperaturesDTO,
     * or with status 400 (Bad Request) if the vehicleTemperaturesDTO is not valid,
     * or with status 500 (Internal Server Error) if the vehicleTemperaturesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicle-temperatures")
    public ResponseEntity<VehicleTemperaturesDTO> updateVehicleTemperatures(@Valid @RequestBody VehicleTemperaturesDTO vehicleTemperaturesDTO) throws URISyntaxException {
        log.debug("REST request to update VehicleTemperatures : {}", vehicleTemperaturesDTO);
        if (vehicleTemperaturesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VehicleTemperaturesDTO result = vehicleTemperaturesService.save(vehicleTemperaturesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehicleTemperaturesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicle-temperatures : get all the vehicleTemperatures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vehicleTemperatures in body
     */
    @GetMapping("/vehicle-temperatures")
    public List<VehicleTemperaturesDTO> getAllVehicleTemperatures() {
        log.debug("REST request to get all VehicleTemperatures");
        return vehicleTemperaturesService.findAll();
    }

    /**
     * GET  /vehicle-temperatures/:id : get the "id" vehicleTemperatures.
     *
     * @param id the id of the vehicleTemperaturesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehicleTemperaturesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vehicle-temperatures/{id}")
    public ResponseEntity<VehicleTemperaturesDTO> getVehicleTemperatures(@PathVariable Long id) {
        log.debug("REST request to get VehicleTemperatures : {}", id);
        Optional<VehicleTemperaturesDTO> vehicleTemperaturesDTO = vehicleTemperaturesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleTemperaturesDTO);
    }

    /**
     * DELETE  /vehicle-temperatures/:id : delete the "id" vehicleTemperatures.
     *
     * @param id the id of the vehicleTemperaturesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicle-temperatures/{id}")
    public ResponseEntity<Void> deleteVehicleTemperatures(@PathVariable Long id) {
        log.debug("REST request to delete VehicleTemperatures : {}", id);
        vehicleTemperaturesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
