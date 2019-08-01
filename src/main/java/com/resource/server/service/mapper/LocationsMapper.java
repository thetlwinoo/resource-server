package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.LocationsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Locations and its DTO LocationsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LocationsMapper extends EntityMapper<LocationsDTO, Locations> {



    default Locations fromId(Long id) {
        if (id == null) {
            return null;
        }
        Locations locations = new Locations();
        locations.setId(id);
        return locations;
    }
}
