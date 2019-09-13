package com.resource.server.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.resource.server.domain.ProductCategory;
import com.resource.server.service.util.NodeUtil;

import java.util.List;

public interface ProductCategoryExtendService {
    JsonNode getAllProductCategories();
}
