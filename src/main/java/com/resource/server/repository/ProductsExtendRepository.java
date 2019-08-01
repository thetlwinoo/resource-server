package com.resource.server.repository;

import com.resource.server.domain.Products;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

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


}
