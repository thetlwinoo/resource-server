package com.resource.server.service.impl;

import com.resource.server.domain.Customers;
import com.resource.server.domain.People;
import com.resource.server.repository.*;
import com.resource.server.service.MembershipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@Transactional
public class MembershipServiceImpl implements MembershipService {

    private final Logger log = LoggerFactory.getLogger(MembershipServiceImpl.class);
    private final PeopleRepository peopleRepository;
    private final UserRepository userRepository;
    private final CustomersRepository customersRepository;
    private final PeopleExtendRepository peopleExtendRepository;
    private final CustomersExtendRepository customersExtendRepository;

    @Autowired
    public MembershipServiceImpl(PeopleRepository peopleRepository, UserRepository userRepository, CustomersRepository customersRepository, PeopleExtendRepository peopleExtendRepository, CustomersExtendRepository customersExtendRepository) {
        this.peopleRepository = peopleRepository;
        this.userRepository = userRepository;
        this.customersRepository = customersRepository;
        this.peopleExtendRepository = peopleExtendRepository;
        this.customersExtendRepository = customersExtendRepository;
    }

    @Override
    public People checkProfile(Principal principal, People people) {
        People peopleFromPrincipal = getUserFromPrinciple(principal);

        if (peopleFromPrincipal == null) {
            try {
                peopleFromPrincipal = peopleRepository.save(people);
            } catch (Exception ex) {
                throw ex;
            }
        }

        if(peopleFromPrincipal.isIsPermittedToLogon() && peopleFromPrincipal.isIsGuestUser()){
            Customers checkCustomer = new Customers();
            checkCustomer = customersExtendRepository.findCustomersByPersonId(peopleFromPrincipal.getId());

            if (checkCustomer == null) {
                try {
                    Customers customer = new Customers();
                    customer.setPerson(peopleFromPrincipal);
                    String accountNumber = String.format("AW%010d", peopleFromPrincipal.getId());
                    customer.setAccountNumber(accountNumber);
                    customersRepository.save(customer);
                } catch (Exception ex) {
                    throw ex;
                }
            }
        }


        return peopleFromPrincipal;
    }

    private People getUserFromPrinciple(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new IllegalArgumentException("Invalid access");
        }

        People people = peopleExtendRepository.findPeopleByLogonName(principal.getName());
//        if (people == null) {
//            throw new IllegalArgumentException("User not found");
//        }
        return people;
    }

}
