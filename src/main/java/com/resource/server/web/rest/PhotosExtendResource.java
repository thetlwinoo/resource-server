package com.resource.server.web.rest;

import com.resource.server.domain.Photos;
import com.resource.server.domain.StockItems;
import com.resource.server.repository.PhotosExtendRepository;
import com.resource.server.repository.StockItemsExtendRepository;
import com.resource.server.repository.StockItemsRepository;
import com.resource.server.service.PhotosExtendService;
import com.resource.server.service.PhotosService;
import com.resource.server.service.StockItemsService;
import com.resource.server.service.dto.StockItemsDTO;
import com.resource.server.service.mapper.PhotosMapper;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.resource.server.service.dto.PhotosDTO;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * PhotosExtendResource controller
 */
@RestController
@RequestMapping("/api/photos-extend")
public class PhotosExtendResource {

    private final Logger log = LoggerFactory.getLogger(PhotosExtendResource.class);
    private final PhotosExtendService photosExtendService;
    private final PhotosExtendRepository photosExtendRepository;
    private static final String ENTITY_NAME = "photos-extend";

    public PhotosExtendResource(PhotosExtendService photosExtendService, PhotosExtendRepository photosExtendRepository) {
        this.photosExtendService = photosExtendService;
        this.photosExtendRepository = photosExtendRepository;
    }

    @RequestMapping(value = "/stockitem/{id}/{handle}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(@PathVariable Long id, @PathVariable String handle) {
        Optional<PhotosDTO> photos = photosExtendService.getOneByStockItem(id);
        byte[] photo;
        HttpHeaders header = new HttpHeaders();
        switch (handle) {
            case "thumbnail":
                header.setContentType(MediaType.valueOf(photos.get().getThumbnailPhotoBlobContentType()));
                header.setContentLength(photos.get().getThumbnailPhotoBlob().length);
                photo = photos.get().getThumbnailPhotoBlob();
                break;
            case "original":
                header.setContentType(MediaType.valueOf(photos.get().getOriginalPhotoBlobContentType()));
                header.setContentLength(photos.get().getOriginalPhotoBlob().length);
                photo = photos.get().getOriginalPhotoBlob();
                break;
            default:
                header.setContentType(MediaType.valueOf(photos.get().getThumbnailPhotoBlobContentType()));
                header.setContentLength(photos.get().getThumbnailPhotoBlob().length);
                photo = photos.get().getThumbnailPhotoBlob();
        }

//        header.set("Content-Disposition", "attachment; filename=" + stockitem.toString() + ".png");

        return new ResponseEntity<>(photo, header, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/photos/{id}")
    public ResponseEntity<Void> deletePhotos(@PathVariable Long id) {
        photosExtendRepository.deletePhotos(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
