package com.resource.server.web.rest;

import com.resource.server.domain.UploadTransactions;
import com.resource.server.service.UploadTransactionsExtendService;
import com.resource.server.service.dto.StockItemsDTO;
import com.resource.server.service.dto.UploadTransactionsDTO;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;

/**
 * UploadTransactionsExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class UploadTransactionsExtendResource {
    private static final String ENTITY_NAME = "uploadTransactionsExtend";
    private final Logger log = LoggerFactory.getLogger(UploadTransactionsExtendResource.class);
    private final UploadTransactionsExtendService uploadTransactionsExtendService;

    public UploadTransactionsExtendResource(UploadTransactionsExtendService uploadTransactionsExtendService) {
        this.uploadTransactionsExtendService = uploadTransactionsExtendService;
    }

    @DeleteMapping("/upload-transactions-extend/{id}")
    public ResponseEntity<Void> deleteStockItemTemps(@PathVariable Long id) {
        log.debug("REST request to delete UploadTransactions : {}", id);
        try{
            uploadTransactionsExtendService.clearStockItemTemp(id);
        }
        catch(Exception ex){
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "error");
        }

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/upload-transactions-extend")
    public List<UploadTransactionsDTO> getAllUploadTransactions(Principal principal) {
        log.debug("REST request to get all UploadTransactions");
        return uploadTransactionsExtendService.findAll(principal);
    }
}
