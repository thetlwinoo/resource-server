package com.resource.server.repository;

import com.resource.server.domain.Merchants;

import java.util.Optional;

public interface MerchantsExtendRepository extends MerchantsRepository {
    Optional<Merchants> findMerchantsByPersonId(Long personId);
}
