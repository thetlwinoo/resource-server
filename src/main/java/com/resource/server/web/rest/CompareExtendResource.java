package com.resource.server.web.rest;

import com.resource.server.domain.Compares;
import com.resource.server.service.CompareExtendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

/**
 * CompareExtendResource controller
 */
@RestController
@RequestMapping("/api/compare-extend")
public class CompareExtendResource {

    private final Logger log = LoggerFactory.getLogger(CompareExtendResource.class);
    private final CompareExtendService compareExtendService;

    public CompareExtendResource(CompareExtendService compareExtendService) {
        this.compareExtendService = compareExtendService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addToCompare(@RequestBody Long id, Principal principal) throws IOException {
        Compares compares = compareExtendService.addToCompare(principal, id);
        return new ResponseEntity<Compares>(compares, HttpStatus.OK);
    }

    @RequestMapping(value = "/fetch", method = RequestMethod.GET)
    public ResponseEntity fetchCompare(Principal principal) {
        Compares compares = compareExtendService.fetchCompare(principal);
        return new ResponseEntity<Compares>(compares, HttpStatus.OK);
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET, params = {"productId"})
    public ResponseEntity getAllOrders(@RequestParam("productId") Long productId, Principal principal) {
        Boolean isInCompare = compareExtendService.isInCompare(principal, productId);
        return new ResponseEntity<Boolean>(isInCompare, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE, params = "id")
    public ResponseEntity removeFromCart(@RequestParam("id") Long id, Principal principal) {
        Compares compares = compareExtendService.removeFromCompare(principal, id);
        return new ResponseEntity<Compares>(compares, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public ResponseEntity emptyCart(Principal principal) {
        compareExtendService.emptyCompare(principal);
        return new ResponseEntity(HttpStatus.OK);
    }

}
