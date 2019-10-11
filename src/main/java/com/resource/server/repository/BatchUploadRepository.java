package com.resource.server.repository;

import com.resource.server.domain.Products;
import com.resource.server.domain.StockItemTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BatchUploadRepository extends JpaRepository<StockItemTemp, Long> {
    @Query(value = "SELECT TRIM(split_part(stock_item_name,'-',1)) AS stock_item_name,product_category_id,product_brand_id FROM stock_item_temp WHERE upload_transaction_id = :id GROUP BY TRIM(split_part(stock_item_name,'-',1)),product_category_id,product_brand_id", nativeQuery = true)
    List<Object[]> getProductsFromTemp(@Param("id") Long id);

    @Query(value = "SELECT s.* FROM stock_item_temp s WHERE s.stock_item_name LIKE %:keyword%", nativeQuery = true)
    List<StockItemTemp> getStockItemTemp(@Param("keyword") String keyword);
}
