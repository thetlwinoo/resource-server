package com.resource.server.service.impl;

import com.resource.server.domain.StockItemTemp;
import com.resource.server.domain.UploadTransactions;
import com.resource.server.repository.UploadTransactionsExtendRepository;
import com.resource.server.service.StockItemsExtendService;
import com.resource.server.service.StockItemsService;
import com.resource.server.service.UploadTransactionsExtendService;
import com.resource.server.service.dto.StockItemsDTO;
import com.resource.server.service.dto.UploadTransactionsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UploadTransactionsExtendServiceImpl implements UploadTransactionsExtendService {

    private final Logger log = LoggerFactory.getLogger(UploadTransactionsExtendServiceImpl.class);
    private final UploadTransactionsExtendRepository uploadTransactionsExtendRepository;
    private final StockItemsService stockItemsService;

    public UploadTransactionsExtendServiceImpl(UploadTransactionsExtendRepository uploadTransactionsExtendRepository, StockItemsService stockItemsService) {
        this.uploadTransactionsExtendRepository = uploadTransactionsExtendRepository;
        this.stockItemsService = stockItemsService;
    }

    @Override
    public void clearStockItemTemp(Long transactionId) {
        UploadTransactions uploadTransactions = uploadTransactionsExtendRepository.getOne(transactionId);
        uploadTransactions.setStockItemTempLists(null);
        uploadTransactionsExtendRepository.save(uploadTransactions);
    }

}
