package com.resource.server.repository;

import com.resource.server.domain.ReviewLines;

import java.util.List;

public interface ReviewLinesExtendRepository extends ReviewLinesRepository {
    List<ReviewLines> findAllByProductId(Long productId);
}
