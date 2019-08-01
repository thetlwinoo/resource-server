package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.WishlistsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Wishlists and its DTO WishlistsDTO.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class})
public interface WishlistsMapper extends EntityMapper<WishlistsDTO, Wishlists> {

    @Mapping(source = "wishlistUser.id", target = "wishlistUserId")
    WishlistsDTO toDto(Wishlists wishlists);

    @Mapping(source = "wishlistUserId", target = "wishlistUser")
    @Mapping(target = "wishlistLists", ignore = true)
    Wishlists toEntity(WishlistsDTO wishlistsDTO);

    default Wishlists fromId(Long id) {
        if (id == null) {
            return null;
        }
        Wishlists wishlists = new Wishlists();
        wishlists.setId(id);
        return wishlists;
    }
}
