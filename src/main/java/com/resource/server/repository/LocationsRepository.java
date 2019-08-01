package com.resource.server.repository;

import com.resource.server.domain.Locations;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Locations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationsRepository extends JpaRepository<Locations, Long> {

}
