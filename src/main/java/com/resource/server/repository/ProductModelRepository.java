package com.resource.server.repository;

import com.resource.server.domain.ProductModel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductModelRepository extends JpaRepository<ProductModel, Long> {

}
