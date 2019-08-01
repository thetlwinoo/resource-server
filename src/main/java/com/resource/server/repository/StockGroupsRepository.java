package com.resource.server.repository;

import com.resource.server.domain.StockGroups;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StockGroups entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockGroupsRepository extends JpaRepository<StockGroups, Long> {

}
