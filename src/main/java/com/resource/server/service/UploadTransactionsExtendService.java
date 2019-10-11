package com.resource.server.service;

import com.resource.server.service.dto.StockItemTempDTO;
import com.resource.server.service.dto.StockItemsDTO;
import com.resource.server.service.dto.UploadTransactionsDTO;

import java.util.List;

public interface UploadTransactionsExtendService {
    void clearStockItemTemp(Long transactionId);
}
