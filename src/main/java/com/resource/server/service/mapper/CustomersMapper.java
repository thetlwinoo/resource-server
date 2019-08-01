package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.CustomersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Customers and its DTO CustomersDTO.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class})
public interface CustomersMapper extends EntityMapper<CustomersDTO, Customers> {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "person.fullName", target = "personFullName")
    CustomersDTO toDto(Customers customers);

    @Mapping(source = "personId", target = "person")
    Customers toEntity(CustomersDTO customersDTO);

    default Customers fromId(Long id) {
        if (id == null) {
            return null;
        }
        Customers customers = new Customers();
        customers.setId(id);
        return customers;
    }
}
