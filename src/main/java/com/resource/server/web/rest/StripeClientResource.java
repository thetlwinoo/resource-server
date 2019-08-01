package com.resource.server.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * StripeClientResource controller
 */
@RestController
@RequestMapping("/api/stripe-client")
public class StripeClientResource {

    private final Logger log = LoggerFactory.getLogger(StripeClientResource.class);

    /**
    * GET defaultAction
    */
    @GetMapping("/default-action")
    public String defaultAction() {
        return "defaultAction";
    }

}
