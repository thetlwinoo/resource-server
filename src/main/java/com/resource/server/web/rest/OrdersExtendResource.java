package com.resource.server.web.rest;

import com.resource.server.domain.Orders;
import com.resource.server.repository.OrdersExtendRepository;
import com.resource.server.service.OrdersExtendService;
import com.resource.server.service.dto.OrdersDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * OrdersExtendResource controller
 */
@RestController
@RequestMapping("/api/orders-extend")
public class OrdersExtendResource {

    private final Logger log = LoggerFactory.getLogger(OrdersExtendResource.class);

    private final OrdersExtendService orderService;
    private final OrdersExtendRepository ordersExtendRepository;

    @Autowired
    public OrdersExtendResource(OrdersExtendService orderService, OrdersExtendRepository ordersExtendRepository) {
        this.orderService = orderService;
        this.ordersExtendRepository = ordersExtendRepository;
    }

    @RequestMapping(value = "/order/count", method = RequestMethod.GET)
    public ResponseEntity getAllOrdersCount(Principal principal) {
        Integer orderCount = orderService.getAllOrdersCount(principal);
        return new ResponseEntity<Integer>(orderCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET, params = {"page", "size"})
    public ResponseEntity getAllOrders(@RequestParam("page") Integer page, @RequestParam("size") Integer pageSize, Principal principal) {
        System.out.println("In getAllOrders GET");
        List<Orders> orderList = orderService.getAllOrders(principal, page, pageSize);
        return new ResponseEntity<List<Orders>>(orderList, HttpStatus.OK);
    }

    @RequestMapping(value = "/allorders", method = RequestMethod.GET)
    public ResponseEntity getAllOrders(Principal principal) {
        System.out.println("In getAllOrders GET");
        List<Orders> orderList = orderService.getAllOrdersWithoutPaging(principal);
        return new ResponseEntity<List<Orders>>(orderList, HttpStatus.OK);
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ResponseEntity postOrder(@Valid @RequestBody OrdersDTO ordersDTO, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Orders saveOrder = orderService.postOrder(principal, ordersDTO);
        return new ResponseEntity<Orders>(saveOrder, HttpStatus.OK);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public ResponseEntity<Orders> getOrder(@PathVariable Long id) {
        log.debug("REST request to get OrderLines : {}", id);
        Optional<Orders> orders =  ordersExtendRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orders);
    }

}
