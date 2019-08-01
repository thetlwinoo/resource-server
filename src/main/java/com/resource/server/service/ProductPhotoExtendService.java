package com.resource.server.service;

import com.resource.server.domain.ProductPhoto;

import java.util.List;
import java.util.Optional;

public interface ProductPhotoExtendService {
    ProductPhoto findByProductAndAndDefaultIndIsTrue(Long productId);

    Optional<ProductPhoto> setDefault(Long productPhotoId);

    List<ProductPhoto> getProductPhotosByProduct(Long productId);
}
