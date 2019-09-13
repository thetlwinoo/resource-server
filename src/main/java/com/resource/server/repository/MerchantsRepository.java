package com.resource.server.repository;

import com.resource.server.domain.Merchants;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Merchants entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MerchantsRepository extends JpaRepository<Merchants, Long>, JpaSpecificationExecutor<Merchants> {

}
