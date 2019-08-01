package com.resource.server.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductPhotoExtendResource controller
 */
@RestController
@RequestMapping("/api/product-photo-extend")
public class ProductPhotoExtendResource {

    private final Logger log = LoggerFactory.getLogger(ProductPhotoExtendResource.class);

    /**
    * GET defaultAction
    */
    @GetMapping("/default-action")
    public String defaultAction() {
        return "defaultAction";
    }

}
