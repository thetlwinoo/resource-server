package com.resource.server.service.impl;

import com.resource.server.domain.ProductPhoto;
import com.resource.server.domain.Products;
import com.resource.server.repository.ProductPhotoExtendRepository;
import com.resource.server.repository.ProductPhotoRepository;
import com.resource.server.repository.ProductsRepository;
import com.resource.server.service.ProductPhotoExtendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductPhotoExtendServiceImpl implements ProductPhotoExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductPhotoExtendServiceImpl.class);
    private final ProductPhotoExtendRepository productPhotoExtendRepository;
    private final ProductPhotoRepository productPhotoRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public ProductPhotoExtendServiceImpl(ProductPhotoExtendRepository productPhotoExtendRepository, ProductPhotoRepository productPhotoRepository, ProductsRepository productsRepository) {
        this.productPhotoExtendRepository = productPhotoExtendRepository;
        this.productPhotoRepository = productPhotoRepository;
        this.productsRepository = productsRepository;
    }

    @Override
    public ProductPhoto findByProductAndAndDefaultIndIsTrue(Long productId) {
        return productPhotoExtendRepository.findByProductAndAndDefaultIndIsTrue(productId);
    }

    @Override
    public Optional<ProductPhoto> setDefault(Long productPhotoId) {
        Optional<ProductPhoto> productPhoto = productPhotoRepository.findById(productPhotoId);
        List<ProductPhoto> productPhotos = new ArrayList<>();

        if(productPhoto.isPresent()){
            Long _ProductId = productPhoto.get().getProduct().getId();
            productPhotos= productPhotoExtendRepository.findAllByProductId(_ProductId);
        }else{
            throw new IllegalArgumentException("product photo not found");
        }

        for (ProductPhoto i : productPhotos) {
            if(i.getId() == productPhotoId){
                i.setDefaultInd(true);
                productPhotoRepository.save(i);

                Products products = productsRepository.getOne(i.getProduct().getId());
                products.setPhoto(i.getThumbnailPhoto());
                productsRepository.save(products);
            }
            else{
                if(i.isDefaultInd()){
                    i.setDefaultInd(false);
                    productPhotoRepository.save(i);
                }
            }
        }

        return productPhoto;
    }

    @Override
    public List<ProductPhoto> getProductPhotosByProduct(Long productId) {
        List<ProductPhoto> returnList = productPhotoExtendRepository.findAllByProductId(productId);

        return returnList;
    }

}
