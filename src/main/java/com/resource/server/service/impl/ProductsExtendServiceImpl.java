package com.resource.server.service.impl;

import com.resource.server.domain.*;
import com.resource.server.repository.ProductsExtendFilterRepository;
import com.resource.server.repository.ProductsExtendRepository;
import com.resource.server.service.*;
import com.resource.server.service.dto.*;
import com.resource.server.service.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductsExtendServiceImpl implements ProductsExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductsExtendServiceImpl.class);
    private final ProductsExtendRepository productsExtendRepository;
    private final ProductsExtendFilterRepository productsExtendFilterRepository;
    private final ProductCategoryMapper productCategoryMapper;
    private final ProductsService productsService;
    private final ProductModelService productModelService;
    private final ProductBrandService productBrandService;
    private final ProductCategoryService productCategoryService;
    private final ProductModelMapper productModelMapper;
    private final ProductBrandMapper productBrandMapper;
    private final ProductAttributeService productAttributeService;
    private final ProductOptionService productOptionService;
    private final ProductAttributeMapper productAttributeMapper;
    private final ProductOptionMapper productOptionMapper;
    private final WarrantyTypesService warrantyTypesService;
    private final WarrantyTypesMapper warrantyTypesMapper;

    @Autowired
    public ProductsExtendServiceImpl(ProductsExtendRepository productsExtendRepository, ProductsExtendFilterRepository productsExtendFilterRepository, ProductCategoryMapper productCategoryMapper, ProductsService productsService, ProductModelService productModelService, ProductBrandService productBrandService, ProductCategoryService productCategoryService, ProductModelMapper productModelMapper, ProductBrandMapper productBrandMapper, ProductAttributeService productAttributeService, ProductOptionService productOptionService, ProductAttributeMapper productAttributeMapper, ProductOptionMapper productOptionMapper, WarrantyTypesService warrantyTypesService, WarrantyTypesMapper warrantyTypesMapper) {
        this.productsExtendRepository = productsExtendRepository;
        this.productsExtendFilterRepository = productsExtendFilterRepository;
        this.productCategoryMapper = productCategoryMapper;
        this.productsService = productsService;
        this.productModelService = productModelService;
        this.productBrandService = productBrandService;
        this.productCategoryService = productCategoryService;
        this.productModelMapper = productModelMapper;
        this.productBrandMapper = productBrandMapper;
        this.productAttributeService = productAttributeService;
        this.productOptionService = productOptionService;
        this.productAttributeMapper = productAttributeMapper;
        this.productOptionMapper = productOptionMapper;
        this.warrantyTypesService = warrantyTypesService;
        this.warrantyTypesMapper = warrantyTypesMapper;
    }

    @Override
    @Cacheable(key = "{#pageable,#productCategoryId}")
    public List<Products> findAllByProductCategory(Pageable pageable, Long productCategoryId) {
        return productsExtendRepository.findAllByProductCategoryId(pageable, productCategoryId);
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
    public List<Products> getRelatedProducts(Long productCategoryId, Long id) {
        List<Products> returnList = productsExtendRepository.findTop12ByProductCategoryIdAndIdIsNotOrderBySellCountDesc(productCategoryId, id);
        if (returnList.size() < 8) {
            returnList.addAll(productsExtendRepository.findAllByProductCategoryIdIsNotOrderBySellCountDesc(productCategoryId, PageRequest.of(0, 8 - returnList.size())));
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
    public List<ProductCategoryDTO> getRelatedCategories(String keyword, Long category) {
        try {
            List<ProductCategoryDTO> returnList = productsExtendFilterRepository.selectCategoriesByTags(keyword == null ? "" : keyword, category == null ? 0 : category).stream()
                .map(productCategoryMapper::toDto)
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

    @Override
    public Products save(Products products) {
        Products saveProduct = new Products();
        Optional<ProductModelDTO> productModelDTO = productModelService.findOne(products.getProductModel().getId());
        saveProduct.setProductModel(productModelMapper.toEntity(productModelDTO.get()));

        Optional<ProductBrandDTO> productBrandDTO = productBrandService.findOne(products.getProductBrand().getId());
        saveProduct.setProductBrand(productBrandMapper.toEntity(productBrandDTO.get()));

        Optional<ProductCategoryDTO> productCategoryDTO = productCategoryService.findOne(products.getProductCategory().getId());
        saveProduct.setProductCategory(productCategoryMapper.toEntity(productCategoryDTO.get()));

        Optional<WarrantyTypesDTO> warrantyTypesDTO = warrantyTypesService.findOne(products.getWarrantyType().getId());
        saveProduct.setWarrantyType(warrantyTypesMapper.toEntity(warrantyTypesDTO.get()));

        saveProduct.setProductName(products.getProductName());
        saveProduct.setSearchDetails(products.getSearchDetails());
        saveProduct.setMerchant(products.getMerchant());
        saveProduct.setWarrantyPeriod(products.getWarrantyPeriod());
        saveProduct.setWarrantyPolicy(products.getWarrantyPolicy());
        saveProduct.setWhatInTheBox(products.getWhatInTheBox());

        for (StockItems _stockItems : products.getStockItemLists()) {
            StockItems stockItems = new StockItems();
            stockItems.setStockItemName(products.getProductName());
            stockItems.setQuantityPerOuter(_stockItems.getQuantityPerOuter());
            stockItems.setTypicalHeightPerUnit(_stockItems.getTypicalHeightPerUnit());
            stockItems.setTypicalLengthPerUnit(_stockItems.getTypicalLengthPerUnit());
            stockItems.setTypicalWeightPerUnit(_stockItems.getTypicalWeightPerUnit());
            stockItems.setTypicalWidthPerUnit(_stockItems.getTypicalWidthPerUnit());
            stockItems.setUnitPrice(_stockItems.getUnitPrice());
            stockItems.setRecommendedRetailPrice(_stockItems.getRecommendedRetailPrice());

            Optional<ProductAttributeDTO> productAttributeDTO = productAttributeService.findOne(_stockItems.getProductAttribute().getId());
            stockItems.setProductAttribute(productAttributeMapper.toEntity(productAttributeDTO.get()));

            Optional<ProductOptionDTO> productOptionDTO = productOptionService.findOne(_stockItems.getProductOption().getId());
            stockItems.setProductOption(productOptionMapper.toEntity(productOptionDTO.get()));

            for (Photos _photos : _stockItems.getPhotoLists()) {
                if (_photos.getOriginalPhotoBlob() != null) {
                    Photos photos = new Photos();
                    photos.originalPhotoBlob(_photos.getOriginalPhotoBlob());
                    photos.originalPhotoBlobContentType(_photos.getOriginalPhotoBlobContentType());
                    stockItems.getPhotoLists().add(photos);
                }

            }
            saveProduct.getStockItemLists().add(stockItems);
        }

        saveProduct = productsExtendRepository.save(saveProduct);

        return saveProduct;
    }

    private boolean isBlank(String param) {
        return param.isEmpty() || param.trim().equals("");
    }
}
