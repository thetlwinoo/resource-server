package com.resource.server.service.impl;

import com.resource.server.service.CompareExtendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompareExtendServiceImpl implements CompareExtendService {

    private final Logger log = LoggerFactory.getLogger(CompareExtendServiceImpl.class);

}
