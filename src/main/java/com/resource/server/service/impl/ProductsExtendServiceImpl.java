package com.resource.server.service.impl;

import com.resource.server.domain.ProductSubCategory;
import com.resource.server.domain.Products;
import com.resource.server.repository.ProductsExtendFilterRepository;
import com.resource.server.repository.ProductsExtendRepository;
import com.resource.server.service.ProductsExtendService;
import com.resource.server.service.dto.ProductSubCategoryDTO;
import com.resource.server.service.mapper.ProductSubCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductsExtendServiceImpl implements ProductsExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductsExtendServiceImpl.class);
    private final ProductsExtendRepository productsExtendRepository;
    private final ProductSubCategoryMapper productSubCategoryMapper;
    private final ProductsExtendFilterRepository productsExtendFilterRepository;

    @Autowired
    public ProductsExtendServiceImpl(ProductsExtendRepository productsExtendRepository, ProductSubCategoryMapper productSubCategoryMapper, ProductsExtendFilterRepository productsExtendFilterRepository) {
        this.productsExtendRepository = productsExtendRepository;
        this.productSubCategoryMapper = productSubCategoryMapper;
        this.productsExtendFilterRepository = productsExtendFilterRepository;
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
    public List<Long> getSubCategoryList(Long categoryId) {
        return productsExtendFilterRepository.getSubCategoryIds(categoryId);
    }

    @Override
    public List<ProductSubCategoryDTO> getRelatedCategories(String keyword, Long category) {
        try {
            List<ProductSubCategoryDTO> returnList = productsExtendFilterRepository.selectCategoriesByTags(keyword == null ? "" : keyword, category == null ? 0 : category).stream()
                .map(productSubCategoryMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
//            List<ProductSubCategory> aa = productsExtendFilterRepository.selectCategoriesByTags(keyword,Long.valueOf(category));
            return returnList;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

    }

    @Override
    public List<String> getRelatedColors(String keyword, Long category) {
        try {
            return productsExtendFilterRepository.selectColorsByTags(keyword == null ? "" : keyword, category == null ? 0 : category);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public List<String> getRelatedBrands(String keyword, Long category) {
        return productsExtendFilterRepository.selectBrandsByTags(keyword == null ? "" : keyword, category == null ? 0 : category);
    }

    @Override
    public Object getRelatedPriceRange(String keyword, Long category) {
        try {
            return productsExtendFilterRepository.selectPriceRangeByTags(keyword == null ? "" : keyword, category == null ? 0 : category);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
}
