package com.resource.server.service;

import com.resource.server.domain.People;

public interface PeopleExtendService {
    People findPeopleByEmailAddress(String email);

    People findPeopleByLogonName(String name);

    People findPeopleByEmailAddressOrLogonName(String email, String name);
}
