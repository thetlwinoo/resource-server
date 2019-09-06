package com.resource.server.repository;

import com.resource.server.domain.ProductSubCategory;
import com.resource.server.domain.Products;
import org.hibernate.annotations.Entity;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import java.io.Serializable;
import java.util.List;

public interface ProductsExtendFilterRepository extends JpaRepository<ProductSubCategory, Long> {
    @Query(value = "SELECT DISTINCT trim(tags) FROM products p WHERE p.tags <> '' AND p.tags LIKE %:keyword%", nativeQuery = true)
    List<String> getProductTags(@Param("keyword") String keyword);

    @Query(value = "SELECT DISTINCT A.* FROM product_sub_category A LEFT JOIN products B ON A.id = B.product_sub_category_id LEFT JOIN product_tags C ON B.id = C.product_id WHERE (:keyword = '' OR C.tag_name ILIKE :keyword) AND (:category = 0 OR A.product_category_id = :category)", nativeQuery = true)
    List<ProductSubCategory> selectCategoriesByTags(@Param("keyword") String keyword, @Param("category") Long category);

    @Query(value = "SELECT DISTINCT B.Color FROM product_sub_category A LEFT JOIN products B ON A.id = B.product_sub_category_id LEFT JOIN product_tags C ON B.id = C.product_id WHERE B.Color IS NOT NULL AND B.Color <> '' AND (:keyword = '' OR C.tag_name ILIKE :keyword) AND (:category = 0 OR A.product_category_id = :category)", nativeQuery = true)
    List<String> selectColorsByTags(@Param("keyword") String keyword, @Param("category") Long category);

    @Query(value = "SELECT MIN(B.unit_price) as minprice,MAX(B.unit_price) as maxprice FROM product_sub_category A LEFT JOIN products B ON A.id = B.product_sub_category_id LEFT JOIN product_tags C ON B.id = C.product_id WHERE (:keyword = '' OR C.tag_name ILIKE :keyword) AND (:category = 0 OR A.product_category_id = :category)", nativeQuery = true)
    Object selectPriceRangeByTags(@Param("keyword") String keyword, @Param("category") Long category);

    @Query(value = "SELECT DISTINCT B.brand FROM product_sub_category A LEFT JOIN products B ON A.id = B.product_sub_category_id LEFT JOIN product_tags C ON B.id = C.product_id WHERE B.brand IS NOT NULL AND B.brand <> '' AND (:keyword = '' OR C.tag_name ILIKE :keyword) AND (:category = 0 OR A.product_category_id = :category)", nativeQuery = true)
    List<String> selectBrandsByTags(@Param("keyword") String keyword, @Param("category") Long category);

    @Query(value = "select id from product_sub_category where product_category_id = :categoryId", nativeQuery = true)
    List<Long> getSubCategoryIds(@Param("categoryId") Long categoryId);
}

