package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.StockGroupsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StockGroups and its DTO StockGroupsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StockGroupsMapper extends EntityMapper<StockGroupsDTO, StockGroups> {



    default StockGroups fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockGroups stockGroups = new StockGroups();
        stockGroups.setId(id);
        return stockGroups;
    }
}
