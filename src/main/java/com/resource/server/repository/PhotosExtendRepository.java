package com.resource.server.repository;

import com.resource.server.domain.Photos;
import com.resource.server.domain.ProductPhoto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface PhotosExtendRepository extends PhotosRepository {
    Optional<Photos> findByStockItemAndDefaultIndIsTrue(Long stockItemId);

    Optional<Photos> findFirstByStockItemIdAndThumbnailPhotoBlobIsNotNull(Long stockItemId);

    List<Photos> findAllByStockItemId(Long stockItemId);

    @Modifying
    @Query("delete from Photos p where p.id=:id")
    void deletePhotos(@Param("id") Long id);
}
