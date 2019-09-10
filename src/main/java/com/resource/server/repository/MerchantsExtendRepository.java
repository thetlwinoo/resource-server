package com.resource.server.repository;

import com.resource.server.domain.Merchants;

public interface MerchantsExtendRepository extends MerchantsRepository {
    Merchants findMerchantsByPersonId(Long personId);
}
