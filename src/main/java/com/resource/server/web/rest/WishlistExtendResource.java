package com.resource.server.web.rest;

import com.resource.server.domain.Wishlists;
import com.resource.server.service.WishlistExtendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

/**
 * WishlistExtendResource controller
 */
@RestController
@RequestMapping("/api/wishlist-extend")
public class WishlistExtendResource {

    private final Logger log = LoggerFactory.getLogger(WishlistExtendResource.class);
    private final WishlistExtendService wishlistExtendService;

    public WishlistExtendResource(WishlistExtendService wishlistExtendService) {
        this.wishlistExtendService = wishlistExtendService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addToWishlist(@RequestBody Long id, Principal principal) throws IOException {
        Wishlists wishlists = wishlistExtendService.addToWishlist(principal, id);
        return new ResponseEntity<Wishlists>(wishlists, HttpStatus.OK);
    }

    @RequestMapping(value = "/fetch", method = RequestMethod.GET)
    public ResponseEntity fetchWishlist(Principal principal) {
        Wishlists wishlists = wishlistExtendService.fetchWishlist(principal);
        return new ResponseEntity<Wishlists>(wishlists, HttpStatus.OK);
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET, params = {"productId"})
    public ResponseEntity getAllOrders(@RequestParam("productId") Long productId, Principal principal) {
        Boolean isInWishlist = wishlistExtendService.isInWishlist(principal, productId);
        return new ResponseEntity<Boolean>(isInWishlist, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE, params = "id")
    public ResponseEntity removeFromCart(@RequestParam("id") Long id, Principal principal) {
        Wishlists wishlists = wishlistExtendService.removeFromWishlist(principal, id);
        return new ResponseEntity<Wishlists>(wishlists, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public ResponseEntity emptyCart(Principal principal) {
        wishlistExtendService.emptyWishlist(principal);
        return new ResponseEntity(HttpStatus.OK);
    }

}
