package com.resource.server.repository;

import com.resource.server.domain.ProductPhoto;

import java.util.List;

public interface ProductPhotoExtendRepository extends ProductPhotoRepository {
    ProductPhoto findByProductAndAndDefaultIndIsTrue(Long productId);
    List<ProductPhoto> findAllByProductId(Long productId);
}
