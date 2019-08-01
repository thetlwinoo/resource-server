package com.resource.server.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductsExtendResource controller
 */
@RestController
@RequestMapping("/api/products-extend")
public class ProductsExtendResource {

    private final Logger log = LoggerFactory.getLogger(ProductsExtendResource.class);

    /**
    * GET defaultAction
    */
    @GetMapping("/default-action")
    public String defaultAction() {
        return "defaultAction";
    }

}
