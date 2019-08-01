package com.resource.server.service.impl;

import com.resource.server.service.ProductsExtendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductsExtendServiceImpl implements ProductsExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductsExtendServiceImpl.class);

}
