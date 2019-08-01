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

import com.resource.server.domain.ProductSubCategory;
import com.resource.server.domain.*; // for static metamodels
import com.resource.server.repository.ProductSubCategoryRepository;
import com.resource.server.service.dto.ProductSubCategoryCriteria;
import com.resource.server.service.dto.ProductSubCategoryDTO;
import com.resource.server.service.mapper.ProductSubCategoryMapper;

/**
 * Service for executing complex queries for ProductSubCategory entities in the database.
 * The main input is a {@link ProductSubCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductSubCategoryDTO} or a {@link Page} of {@link ProductSubCategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductSubCategoryQueryService extends QueryService<ProductSubCategory> {

    private final Logger log = LoggerFactory.getLogger(ProductSubCategoryQueryService.class);

    private final ProductSubCategoryRepository productSubCategoryRepository;

    private final ProductSubCategoryMapper productSubCategoryMapper;

    public ProductSubCategoryQueryService(ProductSubCategoryRepository productSubCategoryRepository, ProductSubCategoryMapper productSubCategoryMapper) {
        this.productSubCategoryRepository = productSubCategoryRepository;
        this.productSubCategoryMapper = productSubCategoryMapper;
    }

    /**
     * Return a {@link List} of {@link ProductSubCategoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductSubCategoryDTO> findByCriteria(ProductSubCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductSubCategory> specification = createSpecification(criteria);
        return productSubCategoryMapper.toDto(productSubCategoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductSubCategoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductSubCategoryDTO> findByCriteria(ProductSubCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductSubCategory> specification = createSpecification(criteria);
        return productSubCategoryRepository.findAll(specification, page)
            .map(productSubCategoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductSubCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductSubCategory> specification = createSpecification(criteria);
        return productSubCategoryRepository.count(specification);
    }

    /**
     * Function to convert ProductSubCategoryCriteria to a {@link Specification}
     */
    private Specification<ProductSubCategory> createSpecification(ProductSubCategoryCriteria criteria) {
        Specification<ProductSubCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProductSubCategory_.id));
            }
            if (criteria.getProductSubCategoryName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductSubCategoryName(), ProductSubCategory_.productSubCategoryName));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(ProductSubCategory_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
        }
        return specification;
    }
}
