package com.resource.server.service;

import com.resource.server.domain.StockItemTemp;
import com.resource.server.service.dto.StockItemTempDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StockItemTempExtendService {
    Page<StockItemTempDTO> getAllStockItemTempByTransactionId(Long transactionId, Pageable pageable);
}
