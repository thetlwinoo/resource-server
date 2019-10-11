package com.resource.server.web.rest;
import com.resource.server.service.UploadActionTypesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.UploadActionTypesDTO;
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
 * REST controller for managing UploadActionTypes.
 */
@RestController
@RequestMapping("/api")
public class UploadActionTypesResource {

    private final Logger log = LoggerFactory.getLogger(UploadActionTypesResource.class);

    private static final String ENTITY_NAME = "uploadActionTypes";

    private final UploadActionTypesService uploadActionTypesService;

    public UploadActionTypesResource(UploadActionTypesService uploadActionTypesService) {
        this.uploadActionTypesService = uploadActionTypesService;
    }

    /**
     * POST  /upload-action-types : Create a new uploadActionTypes.
     *
     * @param uploadActionTypesDTO the uploadActionTypesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uploadActionTypesDTO, or with status 400 (Bad Request) if the uploadActionTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/upload-action-types")
    public ResponseEntity<UploadActionTypesDTO> createUploadActionTypes(@RequestBody UploadActionTypesDTO uploadActionTypesDTO) throws URISyntaxException {
        log.debug("REST request to save UploadActionTypes : {}", uploadActionTypesDTO);
        if (uploadActionTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new uploadActionTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UploadActionTypesDTO result = uploadActionTypesService.save(uploadActionTypesDTO);
        return ResponseEntity.created(new URI("/api/upload-action-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /upload-action-types : Updates an existing uploadActionTypes.
     *
     * @param uploadActionTypesDTO the uploadActionTypesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uploadActionTypesDTO,
     * or with status 400 (Bad Request) if the uploadActionTypesDTO is not valid,
     * or with status 500 (Internal Server Error) if the uploadActionTypesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/upload-action-types")
    public ResponseEntity<UploadActionTypesDTO> updateUploadActionTypes(@RequestBody UploadActionTypesDTO uploadActionTypesDTO) throws URISyntaxException {
        log.debug("REST request to update UploadActionTypes : {}", uploadActionTypesDTO);
        if (uploadActionTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UploadActionTypesDTO result = uploadActionTypesService.save(uploadActionTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, uploadActionTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /upload-action-types : get all the uploadActionTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of uploadActionTypes in body
     */
    @GetMapping("/upload-action-types")
    public List<UploadActionTypesDTO> getAllUploadActionTypes() {
        log.debug("REST request to get all UploadActionTypes");
        return uploadActionTypesService.findAll();
    }

    /**
     * GET  /upload-action-types/:id : get the "id" uploadActionTypes.
     *
     * @param id the id of the uploadActionTypesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uploadActionTypesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/upload-action-types/{id}")
    public ResponseEntity<UploadActionTypesDTO> getUploadActionTypes(@PathVariable Long id) {
        log.debug("REST request to get UploadActionTypes : {}", id);
        Optional<UploadActionTypesDTO> uploadActionTypesDTO = uploadActionTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uploadActionTypesDTO);
    }

    /**
     * DELETE  /upload-action-types/:id : delete the "id" uploadActionTypes.
     *
     * @param id the id of the uploadActionTypesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/upload-action-types/{id}")
    public ResponseEntity<Void> deleteUploadActionTypes(@PathVariable Long id) {
        log.debug("REST request to delete UploadActionTypes : {}", id);
        uploadActionTypesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
