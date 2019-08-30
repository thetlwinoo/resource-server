package com.resource.server.service;

import com.resource.server.service.dto.ProductCategoryDTO;

import java.util.List;

public interface ProductCategoryExtendService {
    List<ProductCategoryDTO> getRelatedCategories(String keyword);
}
