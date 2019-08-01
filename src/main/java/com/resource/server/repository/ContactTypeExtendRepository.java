package com.resource.server.repository;

import com.resource.server.domain.ContactType;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactTypeExtendRepository extends ContactTypeRepository {
    ContactType findContactTypeByContactTypeNameContains(String name);
}
