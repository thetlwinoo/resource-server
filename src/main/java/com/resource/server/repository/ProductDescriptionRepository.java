package com.resource.server.repository;

import com.resource.server.domain.ProductDescription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductDescription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, Long> {

}
