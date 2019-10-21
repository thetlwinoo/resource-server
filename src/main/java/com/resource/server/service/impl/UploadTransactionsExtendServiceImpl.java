package com.resource.server.service.impl;

import com.resource.server.domain.StockItemTemp;
import com.resource.server.domain.UploadTransactions;
import com.resource.server.repository.UploadTransactionsExtendRepository;
import com.resource.server.service.StockItemsExtendService;
import com.resource.server.service.StockItemsService;
import com.resource.server.service.SuppliersExtendService;
import com.resource.server.service.UploadTransactionsExtendService;
import com.resource.server.service.dto.StockItemsDTO;
import com.resource.server.service.dto.SuppliersDTO;
import com.resource.server.service.dto.UploadTransactionsDTO;
import com.resource.server.service.mapper.UploadTransactionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UploadTransactionsExtendServiceImpl implements UploadTransactionsExtendService {

    private final Logger log = LoggerFactory.getLogger(UploadTransactionsExtendServiceImpl.class);
    private final UploadTransactionsExtendRepository uploadTransactionsExtendRepository;
    private final StockItemsService stockItemsService;
    private final SuppliersExtendService suppliersExtendService;
    private final UploadTransactionsMapper uploadTransactionsMapper;

    public UploadTransactionsExtendServiceImpl(UploadTransactionsExtendRepository uploadTransactionsExtendRepository, StockItemsService stockItemsService, SuppliersExtendService suppliersExtendService, UploadTransactionsMapper uploadTransactionsMapper) {
        this.uploadTransactionsExtendRepository = uploadTransactionsExtendRepository;
        this.stockItemsService = stockItemsService;
        this.suppliersExtendService = suppliersExtendService;
        this.uploadTransactionsMapper = uploadTransactionsMapper;
    }

    @Override
    public void clearStockItemTemp(Long transactionId) {
        UploadTransactions uploadTransactions = uploadTransactionsExtendRepository.getOne(transactionId);
        uploadTransactions.setStockItemTempLists(null);
        uploadTransactionsExtendRepository.save(uploadTransactions);
    }

    @Override
    public List<UploadTransactionsDTO> findAll(Principal principal) {
        Optional<SuppliersDTO> suppliersDTOOptional = suppliersExtendService.getSupplierByPrincipal(principal);
        return uploadTransactionsExtendRepository.findAllBySupplierId(suppliersDTOOptional.get().getId()).stream()
            .map(uploadTransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
