package com.resource.server.service.impl;

import com.resource.server.domain.ProductCategory;
import com.resource.server.service.ProductCategoryExtendService;
import com.resource.server.service.dto.ProductCategoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductCategoryExtendServiceImpl implements ProductCategoryExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryExtendServiceImpl.class);

    @Override
    public List<ProductCategoryDTO> getRelatedCategories(String keyword){
        List<ProductCategoryDTO> productCategoryDTOList= new ArrayList();

        return productCategoryDTOList;
    }
}
