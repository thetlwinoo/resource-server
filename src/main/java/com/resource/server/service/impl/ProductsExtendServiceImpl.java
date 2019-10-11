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

import java.net.URI;
import java.net.URL;
import java.util.*;
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
    private final StockItemsService stockItemsService;
    private final ProductsMapper productsMapper;

    @Autowired
    public ProductsExtendServiceImpl(ProductsExtendRepository productsExtendRepository, ProductsExtendFilterRepository productsExtendFilterRepository, ProductCategoryMapper productCategoryMapper, ProductsService productsService, ProductModelService productModelService, ProductBrandService productBrandService, ProductCategoryService productCategoryService, ProductModelMapper productModelMapper, ProductBrandMapper productBrandMapper, ProductAttributeService productAttributeService, ProductOptionService productOptionService, ProductAttributeMapper productAttributeMapper, ProductOptionMapper productOptionMapper, WarrantyTypesService warrantyTypesService, WarrantyTypesMapper warrantyTypesMapper, StockItemsService stockItemsService, ProductsMapper productsMapper) {
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
        this.stockItemsService = stockItemsService;
        this.productsMapper = productsMapper;
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
    @Transactional
    public ProductsDTO save(Products products, String serverUrl) {
        Products saveProduct = new Products();

        try {
            if (products.getId() != null) {
                saveProduct = products;
                for (StockItems _stockItems : products.getStockItemLists()) {
                    _stockItems.setStockItemName(products.getProductName());
                    _stockItems.setProduct(products);
                    Set<Photos> photoList = new HashSet<Photos>();
                    for (Photos _photos : _stockItems.getPhotoLists()) {
                        if (_photos.getOriginalPhotoBlob() != null && _photos.getThumbnailPhotoBlob() != null) {
                            _photos.setStockItem(_stockItems);
                            photoList.add(_photos);
                        }
                    }
                    _stockItems.getPhotoLists().clear();
                    _stockItems.getPhotoLists().addAll(photoList);
                }
            } else {
//                saveProduct.setProductModel(products.getProductModel());
                saveProduct.setProductBrand(products.getProductBrand());
                saveProduct.setProductCategory(products.getProductCategory());
//                saveProduct.setWarrantyType(products.getWarrantyType());

                saveProduct.setProductName(products.getProductName());
                saveProduct.setSearchDetails(products.getSearchDetails());
                saveProduct.setSupplier(products.getSupplier());
//                saveProduct.setWarrantyPeriod(products.getWarrantyPeriod());
//                saveProduct.setWarrantyPolicy(products.getWarrantyPolicy());
//                saveProduct.setWhatInTheBox(products.getWhatInTheBox());

                for (StockItems _stockItems : products.getStockItemLists()) {
                    StockItems stockItems = new StockItems();
                    stockItems.setStockItemName(products.getProductName());
//                    stockItems.setQuantityPerOuter(_stockItems.getQuantityPerOuter());
//                    stockItems.setTypicalHeightPerUnit(_stockItems.getTypicalHeightPerUnit());
//                    stockItems.setTypicalLengthPerUnit(_stockItems.getTypicalLengthPerUnit());
//                    stockItems.setTypicalWeightPerUnit(_stockItems.getTypicalWeightPerUnit());
//                    stockItems.setTypicalWidthPerUnit(_stockItems.getTypicalWidthPerUnit());
                    stockItems.setUnitPrice(_stockItems.getUnitPrice());
                    stockItems.setRecommendedRetailPrice(_stockItems.getRecommendedRetailPrice());

                    stockItems.setProductAttribute(_stockItems.getProductAttribute());
                    stockItems.setProductOption(_stockItems.getProductOption());

                    stockItems.setProduct(saveProduct);

                    for (Photos _photos : _stockItems.getPhotoLists()) {
                        if (_photos.getOriginalPhotoBlob() != null && _photos.getThumbnailPhotoBlob() != null) {
                            Photos photos = new Photos();

                            photos.originalPhotoBlob(_photos.getOriginalPhotoBlob());
                            photos.originalPhotoBlobContentType(_photos.getOriginalPhotoBlobContentType());

                            photos.thumbnailPhotoBlob(_photos.getThumbnailPhotoBlob());
                            photos.thumbnailPhotoBlobContentType(_photos.getThumbnailPhotoBlobContentType());

                            photos.setStockItem(stockItems);
                            stockItems.getPhotoLists().add(photos);
                        }
                    }
                    saveProduct.getStockItemLists().add(stockItems);
                }
            }

            saveProduct = productsExtendRepository.save(saveProduct);
            String _productThumbnailUrl = "";
            int index = 0;
            for (StockItems _stockItems : saveProduct.getStockItemLists()) {
                if(++index == 1){
                    _productThumbnailUrl = serverUrl + "/photos-extend/stockitem/" + _stockItems.getId() + "/thumbnail";
                }
                _stockItems.setThumbnailUrl(serverUrl + "/photos-extend/stockitem/" + _stockItems.getId() + "/thumbnail");
            }
//            saveProduct.setThumbnailUrl(_productThumbnailUrl);
            String _productnumber = saveProduct.getProductName().replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
            _productnumber = _productnumber.length() > 8 ? _productnumber.substring(0, 8) : _productnumber;
            _productnumber = _productnumber + "-" + saveProduct.getId();
            saveProduct.setProductNumber(_productnumber);
            productsExtendRepository.save(saveProduct);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return productsMapper.toDto(saveProduct);
    }


}
