package com.resource.server.service.impl;

import com.resource.server.domain.Addresses;
import com.resource.server.domain.People;
import com.resource.server.repository.AddressesExtendRepository;
import com.resource.server.repository.AddressesRepository;
import com.resource.server.repository.PeopleExtendRepository;
import com.resource.server.service.AddressesExtendService;
import com.resource.server.service.AddressesService;
import com.resource.server.service.dto.AddressesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@Transactional
public class AddressesExtendServiceImpl implements AddressesExtendService {

    private final Logger log = LoggerFactory.getLogger(AddressesExtendServiceImpl.class);
    private final PeopleExtendRepository peopleExtendRepository;
    private final AddressesRepository addressesRepository;
    private final AddressesExtendRepository addressesExtendRepository;
    private final AddressesService addressesService;

    public AddressesExtendServiceImpl(PeopleExtendRepository peopleExtendRepository, AddressesRepository addressesRepository, AddressesExtendRepository addressesExtendRepository, AddressesService addressesService) {
        this.peopleExtendRepository = peopleExtendRepository;
        this.addressesRepository = addressesRepository;
        this.addressesExtendRepository = addressesExtendRepository;
        this.addressesService = addressesService;
    }

    @Override
    public List<Addresses> fetchAddresses(Principal principal) {
        People people = getUserFromPrinciple(principal);
        return addressesExtendRepository.findAllByPersonId(people.getId());
    }

    @Override
    public void clearDefaultAddress(Principal principal) {
        People people = getUserFromPrinciple(principal);
        List<Addresses> addressesList = addressesExtendRepository.findAllByPersonId(people.getId());

        for (Addresses addresses : addressesList) {
            addresses.setDefaultInd(false);
            addressesRepository.save(addresses);
        }
    }

    @Override
    public void setDefaultAddress(Principal principal, Long addressId) {
        People people = getUserFromPrinciple(principal);
        List<Addresses> addressesList = addressesExtendRepository.findAllByPersonId(people.getId());

        for (Addresses addresses : addressesList) {
            if (addresses.getId().equals(addressId)) {
                addresses.setDefaultInd(true);
            } else {
                addresses.setDefaultInd(false);
            }

            addressesRepository.save(addresses);
        }
    }

    @Override
    public AddressesDTO crateAddresses(AddressesDTO addressesDTO, Principal principal) {
        People people = getUserFromPrinciple(principal);
        if (addressesDTO.isDefaultInd()) {
            this.clearDefaultAddress(principal);
        }

        addressesDTO.setPersonId(people.getId());
        AddressesDTO result = addressesService.save(addressesDTO);

        return result;
    }

    @Override
    public AddressesDTO updateAddresses(AddressesDTO addressesDTO, Principal principal) {
        People people = getUserFromPrinciple(principal);
        if (addressesDTO.isDefaultInd()) {
            this.clearDefaultAddress(principal);
        }
        AddressesDTO result = addressesService.save(addressesDTO);

        return result;
    }

    private People getUserFromPrinciple(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new IllegalArgumentException("Invalid access");
        }

        People people = peopleExtendRepository.findPeopleByLogonName(principal.getName());
        if (people == null) {
            throw new IllegalArgumentException("People not found");
        }
        return people;
    }

}
