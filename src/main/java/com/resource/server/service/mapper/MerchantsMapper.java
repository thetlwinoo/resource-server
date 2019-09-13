package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.MerchantsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Merchants and its DTO MerchantsDTO.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class})
public interface MerchantsMapper extends EntityMapper<MerchantsDTO, Merchants> {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "person.fullName", target = "personFullName")
    MerchantsDTO toDto(Merchants merchants);

    @Mapping(source = "personId", target = "person")
    Merchants toEntity(MerchantsDTO merchantsDTO);

    default Merchants fromId(Long id) {
        if (id == null) {
            return null;
        }
        Merchants merchants = new Merchants();
        merchants.setId(id);
        return merchants;
    }
}
