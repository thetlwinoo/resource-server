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

import com.resource.server.domain.ProductModel;
import com.resource.server.domain.*; // for static metamodels
import com.resource.server.repository.ProductModelRepository;
import com.resource.server.service.dto.ProductModelCriteria;
import com.resource.server.service.dto.ProductModelDTO;
import com.resource.server.service.mapper.ProductModelMapper;

/**
 * Service for executing complex queries for ProductModel entities in the database.
 * The main input is a {@link ProductModelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductModelDTO} or a {@link Page} of {@link ProductModelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductModelQueryService extends QueryService<ProductModel> {

    private final Logger log = LoggerFactory.getLogger(ProductModelQueryService.class);

    private final ProductModelRepository productModelRepository;

    private final ProductModelMapper productModelMapper;

    public ProductModelQueryService(ProductModelRepository productModelRepository, ProductModelMapper productModelMapper) {
        this.productModelRepository = productModelRepository;
        this.productModelMapper = productModelMapper;
    }

    /**
     * Return a {@link List} of {@link ProductModelDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductModelDTO> findByCriteria(ProductModelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductModel> specification = createSpecification(criteria);
        return productModelMapper.toDto(productModelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductModelDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductModelDTO> findByCriteria(ProductModelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductModel> specification = createSpecification(criteria);
        return productModelRepository.findAll(specification, page)
            .map(productModelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductModelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductModel> specification = createSpecification(criteria);
        return productModelRepository.count(specification);
    }

    /**
     * Function to convert ProductModelCriteria to a {@link Specification}
     */
    private Specification<ProductModel> createSpecification(ProductModelCriteria criteria) {
        Specification<ProductModel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProductModel_.id));
            }
            if (criteria.getProductModelName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductModelName(), ProductModel_.productModelName));
            }
            if (criteria.getCalalogDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCalalogDescription(), ProductModel_.calalogDescription));
            }
            if (criteria.getInstructions() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstructions(), ProductModel_.instructions));
            }
            if (criteria.getDescriptionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDescriptionId(),
                    root -> root.join(ProductModel_.descriptions, JoinType.LEFT).get(ProductModelDescription_.id)));
            }
            if (criteria.getMerchantId() != null) {
                specification = specification.and(buildSpecification(criteria.getMerchantId(),
                    root -> root.join(ProductModel_.merchant, JoinType.LEFT).get(Merchants_.id)));
            }
        }
        return specification;
    }
}
