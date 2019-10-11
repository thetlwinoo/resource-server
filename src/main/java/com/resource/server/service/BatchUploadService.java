package com.resource.server.service;

import com.resource.server.domain.StockItemTemp;
import com.resource.server.domain.UploadTransactions;
import com.resource.server.service.dto.StockItemTempDTO;
import com.resource.server.service.dto.UploadTransactionsDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface BatchUploadService {
    UploadTransactionsDTO readDataFromExcel(MultipartFile file, String serverUrl);
    void importToSystem(Long transactionId);
}
