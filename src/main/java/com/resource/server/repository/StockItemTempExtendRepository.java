package com.resource.server.repository;

import com.resource.server.domain.StockItemTemp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StockItemTempExtendRepository extends StockItemTempRepository {
    Page<StockItemTemp> findAllByUploadTransactionId(Long id, Pageable pageable);
}
