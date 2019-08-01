package com.resource.server.web.rest;
import com.resource.server.service.PackageTypesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.PackageTypesDTO;
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
 * REST controller for managing PackageTypes.
 */
@RestController
@RequestMapping("/api")
public class PackageTypesResource {

    private final Logger log = LoggerFactory.getLogger(PackageTypesResource.class);

    private static final String ENTITY_NAME = "packageTypes";

    private final PackageTypesService packageTypesService;

    public PackageTypesResource(PackageTypesService packageTypesService) {
        this.packageTypesService = packageTypesService;
    }

    /**
     * POST  /package-types : Create a new packageTypes.
     *
     * @param packageTypesDTO the packageTypesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new packageTypesDTO, or with status 400 (Bad Request) if the packageTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/package-types")
    public ResponseEntity<PackageTypesDTO> createPackageTypes(@Valid @RequestBody PackageTypesDTO packageTypesDTO) throws URISyntaxException {
        log.debug("REST request to save PackageTypes : {}", packageTypesDTO);
        if (packageTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new packageTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PackageTypesDTO result = packageTypesService.save(packageTypesDTO);
        return ResponseEntity.created(new URI("/api/package-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /package-types : Updates an existing packageTypes.
     *
     * @param packageTypesDTO the packageTypesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated packageTypesDTO,
     * or with status 400 (Bad Request) if the packageTypesDTO is not valid,
     * or with status 500 (Internal Server Error) if the packageTypesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/package-types")
    public ResponseEntity<PackageTypesDTO> updatePackageTypes(@Valid @RequestBody PackageTypesDTO packageTypesDTO) throws URISyntaxException {
        log.debug("REST request to update PackageTypes : {}", packageTypesDTO);
        if (packageTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PackageTypesDTO result = packageTypesService.save(packageTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, packageTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /package-types : get all the packageTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of packageTypes in body
     */
    @GetMapping("/package-types")
    public List<PackageTypesDTO> getAllPackageTypes() {
        log.debug("REST request to get all PackageTypes");
        return packageTypesService.findAll();
    }

    /**
     * GET  /package-types/:id : get the "id" packageTypes.
     *
     * @param id the id of the packageTypesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the packageTypesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/package-types/{id}")
    public ResponseEntity<PackageTypesDTO> getPackageTypes(@PathVariable Long id) {
        log.debug("REST request to get PackageTypes : {}", id);
        Optional<PackageTypesDTO> packageTypesDTO = packageTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(packageTypesDTO);
    }

    /**
     * DELETE  /package-types/:id : delete the "id" packageTypes.
     *
     * @param id the id of the packageTypesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/package-types/{id}")
    public ResponseEntity<Void> deletePackageTypes(@PathVariable Long id) {
        log.debug("REST request to delete PackageTypes : {}", id);
        packageTypesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
