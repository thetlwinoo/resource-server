package com.resource.server.repository;

import com.resource.server.domain.Products;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductsExtendRepository extends PagingAndSortingRepository<Products, Long> {
    List<Products> findAllByProductSubCategoryId(Pageable pageable, Long productSubCategoryId);

    List<Products> findTop12ByOrderByCreatedDateDesc();

    List<Products> findTop18ByOrderByLastModifiedDateDesc();

    List<Products> findTop12ByOrderBySellCountDesc();

    List<Products> findTop12ByProductSubCategoryIdAndIdIsNotOrderBySellCountDesc(Long productSubCategoryId, Long id);

    List<Products> findAllByProductSubCategoryIdIsNotOrderBySellCountDesc(Long productSubCategoryId, Pageable pageable);

    List<Products> findAllByProductNameContainingIgnoreCase(String name, Pageable pageable);

    List<Products> findAllByProductNameContainingIgnoreCase(String name);

    @Query(value = "SELECT DISTINCT trim(tags) FROM products p WHERE (p.tags = '') IS NOT TRUE AND p.tags LIKE %:keyword%", nativeQuery = true)
    List<String> getProductTags(@Param("keyword") String keyword);
}
