package com.resource.server.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.resource.server.domain.Products;
import com.resource.server.domain.*; // for static metamodels
import com.resource.server.repository.ProductsRepository;
import com.resource.server.service.dto.ProductsCriteria;
import com.resource.server.service.dto.ProductsDTO;
import com.resource.server.service.mapper.ProductsMapper;

/**
 * Service for executing complex queries for Products entities in the database.
 * The main input is a {@link ProductsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductsDTO} or a {@link Page} of {@link ProductsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductsQueryService extends QueryService<Products> {

    private final Logger log = LoggerFactory.getLogger(ProductsQueryService.class);

    private final ProductsRepository productsRepository;

    private final ProductsMapper productsMapper;

    public ProductsQueryService(ProductsRepository productsRepository, ProductsMapper productsMapper) {
        this.productsRepository = productsRepository;
        this.productsMapper = productsMapper;
    }

    /**
     * Return a {@link List} of {@link ProductsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductsDTO> findByCriteria(ProductsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Products> specification = createSpecification(criteria);
        return productsMapper.toDto(productsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductsDTO> findByCriteria(ProductsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Products> specification = createSpecification(criteria);
        return productsRepository.findAll(specification, page)
            .map(productsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Products> specification = createSpecification(criteria);
        return productsRepository.count(specification);
    }

    /**
     * Function to convert ProductsCriteria to a {@link Specification}
     */
    private Specification<Products> createSpecification(ProductsCriteria criteria) {
        Specification<Products> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Products_.id));
            }
            if (criteria.getProductName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductName(), Products_.productName));
            }
            if (criteria.getProductNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductNumber(), Products_.productNumber));
            }
            if (criteria.getSearchDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSearchDetails(), Products_.searchDetails));
            }
            if (criteria.getThumbnailUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThumbnailUrl(), Products_.thumbnailUrl));
            }
            if (criteria.getWarrantyPeriod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWarrantyPeriod(), Products_.warrantyPeriod));
            }
            if (criteria.getWarrantyPolicy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWarrantyPolicy(), Products_.warrantyPolicy));
            }
            if (criteria.getSellCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellCount(), Products_.sellCount));
            }
            if (criteria.getWhatInTheBox() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWhatInTheBox(), Products_.whatInTheBox));
            }
            if (criteria.getStockItemListId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemListId(),
                    root -> root.join(Products_.stockItemLists, JoinType.LEFT).get(StockItems_.id)));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(Products_.supplier, JoinType.LEFT).get(Suppliers_.id)));
            }
            if (criteria.getMerchantId() != null) {
                specification = specification.and(buildSpecification(criteria.getMerchantId(),
                    root -> root.join(Products_.merchant, JoinType.LEFT).get(Merchants_.id)));
            }
            if (criteria.getUnitPackageId() != null) {
                specification = specification.and(buildSpecification(criteria.getUnitPackageId(),
                    root -> root.join(Products_.unitPackage, JoinType.LEFT).get(PackageTypes_.id)));
            }
            if (criteria.getOuterPackageId() != null) {
                specification = specification.and(buildSpecification(criteria.getOuterPackageId(),
                    root -> root.join(Products_.outerPackage, JoinType.LEFT).get(PackageTypes_.id)));
            }
            if (criteria.getProductModelId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductModelId(),
                    root -> root.join(Products_.productModel, JoinType.LEFT).get(ProductModel_.id)));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(Products_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getProductBrandId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductBrandId(),
                    root -> root.join(Products_.productBrand, JoinType.LEFT).get(ProductBrand_.id)));
            }
            if (criteria.getWarrantyTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getWarrantyTypeId(),
                    root -> root.join(Products_.warrantyType, JoinType.LEFT).get(WarrantyTypes_.id)));
            }
        }
        return specification;
    }
}
