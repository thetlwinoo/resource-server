package com.resource.server.repository;

import com.resource.server.domain.ProductReview;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductReview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

}
