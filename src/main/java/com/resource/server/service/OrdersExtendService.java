package com.resource.server.service;

import com.resource.server.domain.Orders;
import com.resource.server.service.dto.OrdersDTO;

import java.security.Principal;
import java.util.List;

public interface OrdersExtendService {
    Integer getAllOrdersCount(Principal principal);

    List<Orders> getAllOrders(Principal principal, Integer page, Integer pageSize);

    List<Orders> getAllOrdersWithoutPaging(Principal principal);

    Orders postOrder(Principal principal, OrdersDTO ordersDTO);
}
