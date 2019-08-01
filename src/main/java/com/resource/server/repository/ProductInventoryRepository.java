package com.resource.server.repository;

import com.resource.server.domain.ProductInventory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductInventory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {

}
