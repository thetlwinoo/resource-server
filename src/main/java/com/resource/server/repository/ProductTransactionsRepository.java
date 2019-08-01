package com.resource.server.repository;

import com.resource.server.domain.ProductTransactions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductTransactions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductTransactionsRepository extends JpaRepository<ProductTransactions, Long> {

}
