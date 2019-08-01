package com.resource.server.repository;

import com.resource.server.domain.Addresses;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressesExtendRepository extends AddressesRepository {
    List<Addresses> findAllByPersonId(Long id);
}
