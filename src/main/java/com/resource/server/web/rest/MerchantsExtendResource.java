package com.resource.server.web.rest;

import com.resource.server.service.MerchantsExtendService;
import com.resource.server.service.MerchantsService;
import com.resource.server.service.dto.MerchantsCriteria;
import com.resource.server.service.dto.MerchantsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * MerchantsExtendResource controller
 */
@RestController
@RequestMapping("/api/merchants-extend")
public class MerchantsExtendResource {

    private final Logger log = LoggerFactory.getLogger(MerchantsExtendResource.class);

    private static final String ENTITY_NAME = "merchants";

    private final MerchantsExtendService merchantsExtendService;

    public MerchantsExtendResource(MerchantsExtendService merchantsExtendService) {
        this.merchantsExtendService = merchantsExtendService;
    }

    @GetMapping("/merchants/principal")
    public ResponseEntity<MerchantsDTO> getOneByPrincipal(Principal principal) {
        Optional<MerchantsDTO> merchantsDTO = merchantsExtendService.getOneByPrincipal(principal);
        return ResponseUtil.wrapOrNotFound(merchantsDTO);
    }
}
