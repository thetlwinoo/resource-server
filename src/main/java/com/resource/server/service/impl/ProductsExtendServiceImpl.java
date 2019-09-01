package com.resource.server.service.impl;

import com.resource.server.domain.Products;
import com.resource.server.repository.ProductsExtendRepository;
import com.resource.server.service.ProductsExtendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductsExtendServiceImpl implements ProductsExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductsExtendServiceImpl.class);
    private final ProductsExtendRepository productsExtendRepository;

    @Autowired
    public ProductsExtendServiceImpl(ProductsExtendRepository productsExtendRepository) {
        this.productsExtendRepository = productsExtendRepository;
    }

    @Override
    @Cacheable(key = "{#pageable,#productSubCategoryId}")
    public List<Products> findAllByProductCategory(Pageable pageable, Long productSubCategoryId) {
        return productsExtendRepository.findAllByProductSubCategoryId(pageable, productSubCategoryId);
    }

    @Override
//    @Cacheable(key = "#root.methodName")
    public List<Products> findTop12ByOrderByCreatedDateDesc() {
        return productsExtendRepository.findTop12ByOrderByCreatedDateDesc();
    }

    @Override
//    @Cacheable(key = "#root.methodName")
    public List<Products> findTop18ByOrderByLastModifiedDateDesc() {
        return productsExtendRepository.findTop18ByOrderByLastModifiedDateDesc();
    }

    @Override
//    @Cacheable(key = "#root.methodName")
    public List<Products> findTop12ByOrderBySellCountDesc() {
        return productsExtendRepository.findTop12ByOrderBySellCountDesc();
    }

    @Override
//    @CachePut(key = "'findTop12ByOrderBySellCountDesc'")
    public List<Products> findTop12ByOrderBySellCountDescCacheRefresh() {
        return productsExtendRepository.findTop12ByOrderBySellCountDesc();
    }

    @Override
//    @Cacheable(key = "#root.methodName")
    public List<Products> getRelatedProducts(Long productSubCategoryId, Long id) {
        List<Products> returnList = productsExtendRepository.findTop12ByProductSubCategoryIdAndIdIsNotOrderBySellCountDesc(productSubCategoryId, id);
        if (returnList.size() < 8) {
            returnList.addAll(productsExtendRepository.findAllByProductSubCategoryIdIsNotOrderBySellCountDesc(productSubCategoryId, PageRequest.of(0, 8 - returnList.size())));
        }
        return returnList;
    }

    @Override
    public List<Products> searchProducts(String keyword, Integer page, Integer size) {
        if (page == null || size == null) {
            throw new IllegalArgumentException("Page and size parameters are required");
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        return productsExtendRepository.findAllByProductNameContainingIgnoreCase(keyword, pageRequest);
    }

    @Override
    public List<Products> searchProductsAll(String keyword) {
        return productsExtendRepository.findAllByProductNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<String> getProductTags(String keyword) {
        return productsExtendRepository.getProductTags(keyword);
    }
}
