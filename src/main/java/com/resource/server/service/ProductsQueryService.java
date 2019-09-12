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
            if (criteria.getMakeFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getMakeFlag(), Products_.makeFlag));
            }
            if (criteria.getFinishedGoodsFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getFinishedGoodsFlag(), Products_.finishedGoodsFlag));
            }
            if (criteria.getColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColor(), Products_.color));
            }
            if (criteria.getSafetyStockLevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSafetyStockLevel(), Products_.safetyStockLevel));
            }
            if (criteria.getReorderPoint() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReorderPoint(), Products_.reorderPoint));
            }
            if (criteria.getStandardCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStandardCost(), Products_.standardCost));
            }
            if (criteria.getUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitPrice(), Products_.unitPrice));
            }
            if (criteria.getRecommendedRetailPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRecommendedRetailPrice(), Products_.recommendedRetailPrice));
            }
            if (criteria.getBrand() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrand(), Products_.brand));
            }
            if (criteria.getSpecifySize() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpecifySize(), Products_.specifySize));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), Products_.weight));
            }
            if (criteria.getDaysToManufacture() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDaysToManufacture(), Products_.daysToManufacture));
            }
            if (criteria.getProductLine() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductLine(), Products_.productLine));
            }
            if (criteria.getClassType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassType(), Products_.classType));
            }
            if (criteria.getStyle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStyle(), Products_.style));
            }
            if (criteria.getCustomFields() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomFields(), Products_.customFields));
            }
            if (criteria.getPhoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoto(), Products_.photo));
            }
            if (criteria.getSellStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellStartDate(), Products_.sellStartDate));
            }
            if (criteria.getSellEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellEndDate(), Products_.sellEndDate));
            }
            if (criteria.getMarketingComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMarketingComments(), Products_.marketingComments));
            }
            if (criteria.getInternalComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInternalComments(), Products_.internalComments));
            }
            if (criteria.getDiscontinuedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscontinuedDate(), Products_.discontinuedDate));
            }
            if (criteria.getSellCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellCount(), Products_.sellCount));
            }
            if (criteria.getReviewLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getReviewLineId(),
                    root -> root.join(Products_.reviewLine, JoinType.LEFT).get(ReviewLines_.id)));
            }
            if (criteria.getUnitPackageId() != null) {
                specification = specification.and(buildSpecification(criteria.getUnitPackageId(),
                    root -> root.join(Products_.unitPackage, JoinType.LEFT).get(PackageTypes_.id)));
            }
            if (criteria.getOuterPackageId() != null) {
                specification = specification.and(buildSpecification(criteria.getOuterPackageId(),
                    root -> root.join(Products_.outerPackage, JoinType.LEFT).get(PackageTypes_.id)));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(Products_.supplier, JoinType.LEFT).get(Suppliers_.id)));
            }
            if (criteria.getMerchantId() != null) {
                specification = specification.and(buildSpecification(criteria.getMerchantId(),
                    root -> root.join(Products_.merchant, JoinType.LEFT).get(Merchants_.id)));
            }
            if (criteria.getProductSubCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductSubCategoryId(),
                    root -> root.join(Products_.productSubCategory, JoinType.LEFT).get(ProductSubCategory_.id)));
            }
            if (criteria.getSizeUnitMeasureCodeId() != null) {
                specification = specification.and(buildSpecification(criteria.getSizeUnitMeasureCodeId(),
                    root -> root.join(Products_.sizeUnitMeasureCode, JoinType.LEFT).get(UnitMeasure_.id)));
            }
            if (criteria.getWeightUnitMeasureCodeId() != null) {
                specification = specification.and(buildSpecification(criteria.getWeightUnitMeasureCodeId(),
                    root -> root.join(Products_.weightUnitMeasureCode, JoinType.LEFT).get(UnitMeasure_.id)));
            }
            if (criteria.getProductModelId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductModelId(),
                    root -> root.join(Products_.productModel, JoinType.LEFT).get(ProductModel_.id)));
            }
        }
        return specification;
    }
}
