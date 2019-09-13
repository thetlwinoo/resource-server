package com.resource.server.web.rest;

import com.resource.server.domain.ProductPhoto;
import com.resource.server.service.PhotosExtendService;
import com.resource.server.service.dto.PhotosDTO;
import com.resource.server.service.dto.ProductPhotoDTO;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * ProductPhotoExtendResource controller
 */
@RestController
@RequestMapping("/api/product-photo-extend")
public class ProductPhotoExtendResource {

    private final Logger log = LoggerFactory.getLogger(ProductPhotoExtendResource.class);
    private final PhotosExtendService photosExtendService;
    private static final String ENTITY_NAME = "productPhoto";

    public ProductPhotoExtendResource(PhotosExtendService photosExtendService) {
        this.photosExtendService = photosExtendService;
    }

    /**
     * GET defaultAction
     */
    @RequestMapping(value = "/default", method = RequestMethod.GET, params = "id")
    public ResponseEntity getDefaultProductPhoto(@RequestParam("id") Long id) {
        Optional<PhotosDTO> photosDTO = photosExtendService.findByStockItemsAndAndDefaultIndIsTrue(id);
        return ResponseUtil.wrapOrNotFound(photosDTO);
    }

    @RequestMapping(value = "/default", method = RequestMethod.POST)
    public ResponseEntity setDefault(@Valid @RequestBody PhotosDTO photosDTO) throws URISyntaxException {
        log.debug("REST request to save ProductPhoto : {}", photosDTO);
        if (photosDTO.getId() == null) {
            throw new BadRequestAlertException("No Product Photo Exist", ENTITY_NAME, "Not Found");
        }

        Optional<PhotosDTO> defaultPhotosDTO = photosExtendService.setDefault(photosDTO.getId());

        return ResponseUtil.wrapOrNotFound(defaultPhotosDTO);
    }

    @RequestMapping(value = "/photos", method = RequestMethod.GET, params = "id")
    public List<PhotosDTO> getByRelated(@RequestParam("id") Long id) {
        return photosExtendService.getPhotosByStockItem(id);
    }

}
