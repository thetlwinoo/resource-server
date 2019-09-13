package com.resource.server.service.impl;

import com.resource.server.domain.People;
import com.resource.server.repository.PeopleExtendRepository;
import com.resource.server.repository.PeopleRepository;
import com.resource.server.service.PeopleExtendService;
import com.resource.server.service.mapper.PeopleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Service
@Transactional
public class PeopleExtendServiceImpl implements PeopleExtendService {

    private final Logger log = LoggerFactory.getLogger(PeopleExtendServiceImpl.class);
    private final PeopleExtendRepository peopleExtendRepository;
    private final PeopleRepository peopleRepository;
    private final PeopleMapper peopleMapper;

    @Autowired
    public PeopleExtendServiceImpl(PeopleExtendRepository peopleExtendRepository, PeopleRepository peopleRepository, PeopleMapper peopleMapper) {
        this.peopleExtendRepository = peopleExtendRepository;
        this.peopleRepository = peopleRepository;
        this.peopleMapper = peopleMapper;
    }


    @Override
    public People findPeopleByEmailAddress(String email) {
        return peopleExtendRepository.findPeopleByEmailAddress(email).get();
    }

    @Override
    public People findPeopleByLogonName(String name) {
        return peopleExtendRepository.findPeopleByLogonName(name).get();
    }

    @Override
    public People findPeopleByEmailAddressOrLogonName(String email, String name) {
        return peopleExtendRepository.findPeopleByEmailAddressOrLogonName(email, name).get();
    }

    private People getUserFromPrinciple(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new IllegalArgumentException("Invalid access");
        }

        Optional<People> people = peopleExtendRepository.findPeopleByLogonName(principal.getName());
//        if (people == null) {
//            throw new IllegalArgumentException("User not found");
//        }
        return people.get();
    }

}
