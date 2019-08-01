package com.resource.server.repository;

import com.resource.server.domain.People;

public interface PeopleExtendRepository extends PeopleRepository {
    People findPeopleByEmailAddress(String email);
    People findPeopleByLogonName(String name);
    People findPeopleByEmailAddressOrLogonName(String email, String name);
}
