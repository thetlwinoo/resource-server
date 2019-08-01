package com.resource.server.repository;

import com.resource.server.domain.BusinessEntityAddress;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessEntityAddressExtendRepository extends BusinessEntityAddressRepository {
    List<BusinessEntityAddress> findAllByPersonId(Long id);
}
