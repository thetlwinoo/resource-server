package com.resource.server.web.rest;

import com.resource.server.service.StockItemsExtendService;
import com.resource.server.service.dto.PhotosDTO;
import com.resource.server.service.dto.StockItemsDTO;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * StockItemsExtendResource controller
 */
@RestController
@RequestMapping("/api/stock-items-extend")
public class StockItemsExtendResource {

    private final Logger log = LoggerFactory.getLogger(StockItemsExtendResource.class);
    private final StockItemsExtendService stockItemsExtendService;
    private static final String ENTITY_NAME = "stockItemsExtend";

    public StockItemsExtendResource(StockItemsExtendService stockItemsExtendService) {
        this.stockItemsExtendService = stockItemsExtendService;
    }

    @PostMapping("/photos")
    public ResponseEntity<PhotosDTO> addPhotos(@Valid @RequestBody PhotosDTO photosDTO) throws URISyntaxException {
        log.debug("REST request to save Photos : {}", photosDTO);
        if (photosDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhotosDTO result = stockItemsExtendService.addPhotos(photosDTO);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/photos")
    public ResponseEntity<PhotosDTO> updatePhotos(@Valid @RequestBody PhotosDTO photosDTO) throws URISyntaxException {
        log.debug("REST request to update photos : {}", photosDTO);
        if (photosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhotosDTO result = stockItemsExtendService.updatePhotos(photosDTO);
        return ResponseEntity.ok().body(result);
    }

}
