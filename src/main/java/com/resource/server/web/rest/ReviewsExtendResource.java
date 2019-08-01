package com.resource.server.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ReviewsExtendResource controller
 */
@RestController
@RequestMapping("/api/reviews-extend")
public class ReviewsExtendResource {

    private final Logger log = LoggerFactory.getLogger(ReviewsExtendResource.class);

    /**
    * GET defaultAction
    */
    @GetMapping("/default-action")
    public String defaultAction() {
        return "defaultAction";
    }

}
