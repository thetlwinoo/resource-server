package com.resource.server.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ShoppingCartsExtendResource controller
 */
@RestController
@RequestMapping("/api/shopping-carts-extend")
public class ShoppingCartsExtendResource {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartsExtendResource.class);

    /**
    * GET defaultAction
    */
    @GetMapping("/default-action")
    public String defaultAction() {
        return "defaultAction";
    }

}
