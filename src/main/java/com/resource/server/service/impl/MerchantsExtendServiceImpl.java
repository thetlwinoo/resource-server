package com.resource.server.service.impl;

import com.resource.server.domain.Merchants;
import com.resource.server.domain.People;
import com.resource.server.repository.MerchantsExtendRepository;
import com.resource.server.repository.PeopleExtendRepository;
import com.resource.server.service.MerchantsExtendService;
import com.resource.server.service.dto.MerchantsDTO;
import com.resource.server.service.mapper.MerchantsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Service
@Transactional
public class MerchantsExtendServiceImpl implements MerchantsExtendService {

    private final Logger log = LoggerFactory.getLogger(MerchantsExtendServiceImpl.class);
    private final MerchantsExtendRepository merchantsExtendRepository;
    private final MerchantsMapper merchantsMapper;
    private final PeopleExtendRepository peopleExtendRepository;

    public MerchantsExtendServiceImpl(MerchantsExtendRepository merchantsExtendRepository, MerchantsMapper merchantsMapper, PeopleExtendRepository peopleExtendRepository) {
        this.merchantsExtendRepository = merchantsExtendRepository;
        this.merchantsMapper = merchantsMapper;
        this.peopleExtendRepository = peopleExtendRepository;
    }

    @Override
    public Optional<MerchantsDTO> getOneByPrincipal(Principal principal) {
        People people = getUserFromPrinciple(principal);

        return merchantsExtendRepository.findMerchantsByPersonId(people.getId())
            .map(merchantsMapper::toDto);
    }

    private People getUserFromPrinciple(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new IllegalArgumentException("Invalid access");
        }

        String username = principal.getName();
        Optional<People> people = peopleExtendRepository.findPeopleByLogonName(username);
        if (!people.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        return people.get();
    }
}
