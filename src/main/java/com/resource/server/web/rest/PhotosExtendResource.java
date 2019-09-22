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
@RequestMapping("/api")
public class PhotosExtendResource {

    private final Logger log = LoggerFactory.getLogger(PhotosExtendResource.class);
    private final PhotosExtendService photosExtendService;

    public PhotosExtendResource(PhotosExtendService photosExtendService) {
        this.photosExtendService = photosExtendService;
    }

    @RequestMapping(value = "/photos-extend", method = RequestMethod.GET, params = {"stockitem"})
    public ResponseEntity<byte[]> download(@RequestParam(value = "stockitem", required = true) Long stockitem, HttpServletResponse response) {
        Optional<PhotosDTO> photos = photosExtendService.getOneByStockItem(stockitem);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(photos.get().getOriginalPhotoBlobContentType()));
        header.setContentLength(photos.get().getOriginalPhotoBlob().length);
//        header.set("Content-Disposition", "attachment; filename=" + stockitem.toString() + ".png");

        return new ResponseEntity<>(photos.get().getOriginalPhotoBlob(), header, HttpStatus.OK);
    }

}
