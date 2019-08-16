package com.resource.server.service;

import com.resource.server.domain.ProductPhoto;
import com.resource.server.service.dto.ProductPhotoDTO;

import java.util.List;
import java.util.Optional;

public interface ProductPhotoExtendService {
    ProductPhoto findByProductAndAndDefaultIndIsTrue(Long productId);

    Optional<ProductPhoto> setDefault(Long productPhotoId);

    List<ProductPhotoDTO> getProductPhotosByProduct(Long productId);
}
