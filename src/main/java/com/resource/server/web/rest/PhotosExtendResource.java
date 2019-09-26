package com.resource.server.web.rest;

import com.resource.server.domain.Photos;
import com.resource.server.service.PhotosExtendService;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.resource.server.service.dto.PhotosDTO;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

    public PhotosExtendResource(PhotosExtendService photosExtendService) {
        this.photosExtendService = photosExtendService;
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

}
