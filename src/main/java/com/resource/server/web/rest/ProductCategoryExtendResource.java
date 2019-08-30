package com.resource.server.web.rest;

import com.resource.server.service.ProductCategoryExtendService;
import com.resource.server.service.dto.ProductCategoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProductCategoryExtendResource controller
 */
@RestController
@RequestMapping("/api/product-category-extend")
public class ProductCategoryExtendResource {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryExtendResource.class);
    private final ProductCategoryExtendService productCategoryExtendService;

    public ProductCategoryExtendResource(ProductCategoryExtendService productCategoryExtendService) {
        this.productCategoryExtendService = productCategoryExtendService;
    }

    @RequestMapping(value = "/related", method = RequestMethod.GET, params = {"keyword"})
    public ResponseEntity<List<ProductCategoryDTO>> getRelatedCategories(@RequestParam(value = "keyword", required = false) String keyword) {
        List<ProductCategoryDTO> entityList = this.productCategoryExtendService.getRelatedCategories(keyword);
        return ResponseEntity.ok().body(entityList);
    }

}
