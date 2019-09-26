package com.resource.server.repository;

import com.resource.server.domain.Photos;
import com.resource.server.domain.ProductPhoto;
import java.util.Optional;
import java.util.List;

public interface PhotosExtendRepository extends PhotosRepository {
    Optional<Photos> findByStockItemAndDefaultIndIsTrue(Long stockItemId);
    Optional<Photos> findFirstByStockItemIdAAndThumbnailPhotoBlobIsNotNull(Long stockItemId);
    List<Photos> findAllByStockItemId(Long stockItemId);
}
