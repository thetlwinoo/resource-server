package com.resource.server.service.impl;

import com.resource.server.service.ProductReviewService;
import com.resource.server.domain.ProductReview;
import com.resource.server.repository.ProductReviewRepository;
import com.resource.server.service.dto.ProductReviewDTO;
import com.resource.server.service.mapper.ProductReviewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProductReview.
 */
@Service
@Transactional
public class ProductReviewServiceImpl implements ProductReviewService {

    private final Logger log = LoggerFactory.getLogger(ProductReviewServiceImpl.class);

    private final ProductReviewRepository productReviewRepository;

    private final ProductReviewMapper productReviewMapper;

    public ProductReviewServiceImpl(ProductReviewRepository productReviewRepository, ProductReviewMapper productReviewMapper) {
        this.productReviewRepository = productReviewRepository;
        this.productReviewMapper = productReviewMapper;
    }

    /**
     * Save a productReview.
     *
     * @param productReviewDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductReviewDTO save(ProductReviewDTO productReviewDTO) {
        log.debug("Request to save ProductReview : {}", productReviewDTO);
        ProductReview productReview = productReviewMapper.toEntity(productReviewDTO);
        productReview = productReviewRepository.save(productReview);
        return productReviewMapper.toDto(productReview);
    }

    /**
     * Get all the productReviews.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductReviewDTO> findAll() {
        log.debug("Request to get all ProductReviews");
        return productReviewRepository.findAll().stream()
            .map(productReviewMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productReview by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductReviewDTO> findOne(Long id) {
        log.debug("Request to get ProductReview : {}", id);
        return productReviewRepository.findById(id)
            .map(productReviewMapper::toDto);
    }

    /**
     * Delete the productReview by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductReview : {}", id);
        productReviewRepository.deleteById(id);
    }
}
