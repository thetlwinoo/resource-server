package com.resource.server.repository;

import com.resource.server.domain.Products;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductsExtendRepository extends PagingAndSortingRepository<Products, Long> {
    List<Products> findAllByProductCategoryId(Pageable pageable, Long productCategoryId);

    List<Products> findTop12ByOrderByCreatedDateDesc();

    List<Products> findTop18ByOrderByLastModifiedDateDesc();

    List<Products> findTop12ByOrderBySellCountDesc();

    List<Products> findTop12ByProductCategoryIdAndIdIsNotOrderBySellCountDesc(Long productCategoryId, Long id);

    List<Products> findAllByProductCategoryIdIsNotOrderBySellCountDesc(Long productCategoryId, Pageable pageable);

    List<Products> findAllByProductNameContainingIgnoreCase(String name, Pageable pageable);

    List<Products> findAllByProductNameContainingIgnoreCase(String name);

}
