package com.resource.server.service;

import com.resource.server.domain.Products;
import com.resource.server.service.dto.ProductSubCategoryDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductsExtendService {
    List<Products> findAllByProductCategory(Pageable pageable, Long productSubCategoryId);

    List<Products> findTop12ByOrderByCreatedDateDesc();

    List<Products> findTop18ByOrderByLastModifiedDateDesc();

    List<Products> findTop12ByOrderBySellCountDesc();

    List<Products> findTop12ByOrderBySellCountDescCacheRefresh();

    List<Products> getRelatedProducts(Long productSubCategoryId, Long id);

    List<Products> searchProducts(String keyword, Integer page, Integer size);

    List<Products> searchProductsAll(String keyword);

    List<Long> getSubCategoryList(Long categoryId);

    List<ProductSubCategoryDTO> getRelatedCategories(String keyword, Long category);

    List<String> getRelatedColors(String keyword, Long category);

    Object getRelatedPriceRange(String keyword, Long category);

    List<String> getRelatedBrands(String keyword, Long category);
}
