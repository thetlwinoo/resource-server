package com.resource.server.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PayPalClientResource controller
 */
@RestController
@RequestMapping("/api/pay-pal-client")
public class PayPalClientResource {

    private final Logger log = LoggerFactory.getLogger(PayPalClientResource.class);

    /**
    * GET defaultAction
    */
    @GetMapping("/default-action")
    public String defaultAction() {
        return "defaultAction";
    }

}
