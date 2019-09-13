package com.resource.server.repository;

import com.resource.server.domain.ProductModelDescription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductModelDescription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductModelDescriptionRepository extends JpaRepository<ProductModelDescription, Long> {

}
