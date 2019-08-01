package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ShoppingCartItemsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShoppingCartItems and its DTO ShoppingCartItemsDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductsMapper.class, ShoppingCartsMapper.class})
public interface ShoppingCartItemsMapper extends EntityMapper<ShoppingCartItemsDTO, ShoppingCartItems> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productProductName")
    @Mapping(source = "cart.id", target = "cartId")
    ShoppingCartItemsDTO toDto(ShoppingCartItems shoppingCartItems);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "cartId", target = "cart")
    ShoppingCartItems toEntity(ShoppingCartItemsDTO shoppingCartItemsDTO);

    default ShoppingCartItems fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShoppingCartItems shoppingCartItems = new ShoppingCartItems();
        shoppingCartItems.setId(id);
        return shoppingCartItems;
    }
}
