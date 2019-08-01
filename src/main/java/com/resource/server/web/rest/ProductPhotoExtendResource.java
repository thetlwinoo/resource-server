package com.resource.server.web.rest;

import com.resource.server.domain.ProductPhoto;
import com.resource.server.service.ProductPhotoExtendService;
import com.resource.server.service.dto.ProductPhotoDTO;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final ProductPhotoExtendService productPhotoExtendService;
    private static final String ENTITY_NAME = "productPhoto";

    public ProductPhotoExtendResource(ProductPhotoExtendService productPhotoExtendService) {
        this.productPhotoExtendService = productPhotoExtendService;
    }

    /**
     * GET defaultAction
     */
    @RequestMapping(value = "/default", method = RequestMethod.GET, params = "id")
    public ResponseEntity getDefaultProductPhoto(@RequestParam("id") Long id) {
        ProductPhoto productPhoto = productPhotoExtendService.findByProductAndAndDefaultIndIsTrue(id);

        if (productPhoto == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ProductPhoto>(productPhoto, HttpStatus.OK);
    }

    @RequestMapping(value = "/default", method = RequestMethod.POST)
    public ResponseEntity setDefault(@Valid @RequestBody ProductPhotoDTO productPhotoDTO) throws URISyntaxException {
        log.debug("REST request to save ProductPhoto : {}", productPhotoDTO);
        if (productPhotoDTO.getId() == null) {
            throw new BadRequestAlertException("No Product Photo Exist", ENTITY_NAME, "Not Found");
        }

        Optional<ProductPhoto> productPhoto = productPhotoExtendService.setDefault(productPhotoDTO.getId());

        if (productPhoto.isPresent()) {
            return new ResponseEntity<ProductPhoto>(productPhoto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/photos", method = RequestMethod.GET, params = "id")
    public ResponseEntity getByRelated(@RequestParam("id") Long id) {
        return new ResponseEntity<List>(productPhotoExtendService.getProductPhotosByProduct(id), HttpStatus.OK);
    }

}
