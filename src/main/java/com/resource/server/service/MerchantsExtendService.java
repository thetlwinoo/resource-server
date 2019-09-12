package com.resource.server.service;

import com.resource.server.service.dto.MerchantsDTO;

import java.security.Principal;
import java.util.Optional;

public interface MerchantsExtendService {
    Optional<MerchantsDTO> getOneByPrincipal(Principal principal);
}
