package com.resource.server.service;

import com.resource.server.domain.Orders;
import com.resource.server.service.dto.ReviewsDTO;

import java.security.Principal;
import java.util.List;

public interface ReviewsExtendService {
    ReviewsDTO postReview(Principal principal, ReviewsDTO reviewsDTO, Long orderId);

    List<Orders> findAllOrderedProducts(Principal principal);
}
