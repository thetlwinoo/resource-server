package com.resource.server.web.rest;
import com.resource.server.service.PhotosService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.PhotosDTO;
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
 * REST controller for managing Photos.
 */
@RestController
@RequestMapping("/api")
public class PhotosResource {

    private final Logger log = LoggerFactory.getLogger(PhotosResource.class);

    private static final String ENTITY_NAME = "photos";

    private final PhotosService photosService;

    public PhotosResource(PhotosService photosService) {
        this.photosService = photosService;
    }

    /**
     * POST  /photos : Create a new photos.
     *
     * @param photosDTO the photosDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new photosDTO, or with status 400 (Bad Request) if the photos has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/photos")
    public ResponseEntity<PhotosDTO> createPhotos(@Valid @RequestBody PhotosDTO photosDTO) throws URISyntaxException {
        log.debug("REST request to save Photos : {}", photosDTO);
        if (photosDTO.getId() != null) {
            throw new BadRequestAlertException("A new photos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhotosDTO result = photosService.save(photosDTO);
        return ResponseEntity.created(new URI("/api/photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /photos : Updates an existing photos.
     *
     * @param photosDTO the photosDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated photosDTO,
     * or with status 400 (Bad Request) if the photosDTO is not valid,
     * or with status 500 (Internal Server Error) if the photosDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/photos")
    public ResponseEntity<PhotosDTO> updatePhotos(@Valid @RequestBody PhotosDTO photosDTO) throws URISyntaxException {
        log.debug("REST request to update Photos : {}", photosDTO);
        if (photosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhotosDTO result = photosService.save(photosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, photosDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /photos : get all the photos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of photos in body
     */
    @GetMapping("/photos")
    public List<PhotosDTO> getAllPhotos() {
        log.debug("REST request to get all Photos");
        return photosService.findAll();
    }

    /**
     * GET  /photos/:id : get the "id" photos.
     *
     * @param id the id of the photosDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the photosDTO, or with status 404 (Not Found)
     */
    @GetMapping("/photos/{id}")
    public ResponseEntity<PhotosDTO> getPhotos(@PathVariable Long id) {
        log.debug("REST request to get Photos : {}", id);
        Optional<PhotosDTO> photosDTO = photosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(photosDTO);
    }

    /**
     * DELETE  /photos/:id : delete the "id" photos.
     *
     * @param id the id of the photosDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/photos/{id}")
    public ResponseEntity<Void> deletePhotos(@PathVariable Long id) {
        log.debug("REST request to delete Photos : {}", id);
        photosService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
