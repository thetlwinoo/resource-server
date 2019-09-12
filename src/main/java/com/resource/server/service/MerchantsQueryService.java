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

import com.resource.server.domain.Merchants;
import com.resource.server.domain.*; // for static metamodels
import com.resource.server.repository.MerchantsRepository;
import com.resource.server.service.dto.MerchantsCriteria;
import com.resource.server.service.dto.MerchantsDTO;
import com.resource.server.service.mapper.MerchantsMapper;

/**
 * Service for executing complex queries for Merchants entities in the database.
 * The main input is a {@link MerchantsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MerchantsDTO} or a {@link Page} of {@link MerchantsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MerchantsQueryService extends QueryService<Merchants> {

    private final Logger log = LoggerFactory.getLogger(MerchantsQueryService.class);

    private final MerchantsRepository merchantsRepository;

    private final MerchantsMapper merchantsMapper;

    public MerchantsQueryService(MerchantsRepository merchantsRepository, MerchantsMapper merchantsMapper) {
        this.merchantsRepository = merchantsRepository;
        this.merchantsMapper = merchantsMapper;
    }

    /**
     * Return a {@link List} of {@link MerchantsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MerchantsDTO> findByCriteria(MerchantsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Merchants> specification = createSpecification(criteria);
        return merchantsMapper.toDto(merchantsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MerchantsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MerchantsDTO> findByCriteria(MerchantsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Merchants> specification = createSpecification(criteria);
        return merchantsRepository.findAll(specification, page)
            .map(merchantsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MerchantsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Merchants> specification = createSpecification(criteria);
        return merchantsRepository.count(specification);
    }

    /**
     * Function to convert MerchantsCriteria to a {@link Specification}
     */
    private Specification<Merchants> createSpecification(MerchantsCriteria criteria) {
        Specification<Merchants> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Merchants_.id));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNumber(), Merchants_.accountNumber));
            }
            if (criteria.getMerchantName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMerchantName(), Merchants_.merchantName));
            }
            if (criteria.getCreditRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreditRating(), Merchants_.creditRating));
            }
            if (criteria.getActiveFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getActiveFlag(), Merchants_.activeFlag));
            }
            if (criteria.getWebServiceUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebServiceUrl(), Merchants_.webServiceUrl));
            }
            if (criteria.getWebSiteUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebSiteUrl(), Merchants_.webSiteUrl));
            }
            if (criteria.getPersonId() != null) {
                specification = specification.and(buildSpecification(criteria.getPersonId(),
                    root -> root.join(Merchants_.person, JoinType.LEFT).get(People_.id)));
            }
        }
        return specification;
    }
}
