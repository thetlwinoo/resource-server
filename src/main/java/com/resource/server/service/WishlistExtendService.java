package com.resource.server.service;

import com.resource.server.domain.Wishlists;

import java.security.Principal;

public interface WishlistExtendService {
    Wishlists addToWishlist(Principal principal, Long id);

    Wishlists fetchWishlist(Principal principal);

    Wishlists removeFromWishlist(Principal principal, Long id);

    void emptyWishlist(Principal principal);

    Boolean isInWishlist(Principal principal, Long id);
}
