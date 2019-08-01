package com.resource.server.service.impl;

import com.resource.server.domain.Customers;
import com.resource.server.domain.Orders;
import com.resource.server.domain.People;
import com.resource.server.domain.Reviews;
import com.resource.server.repository.CustomersExtendRepository;
import com.resource.server.repository.OrdersExtendRepository;
import com.resource.server.repository.PeopleExtendRepository;
import com.resource.server.repository.ReviewsExtendRepository;
import com.resource.server.service.ReviewsExtendService;
import com.resource.server.service.dto.ReviewsDTO;
import com.resource.server.service.mapper.ReviewsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewsExtendServiceImpl implements ReviewsExtendService {

    private final Logger log = LoggerFactory.getLogger(ReviewsExtendServiceImpl.class);
    private final ReviewsExtendRepository reviewsExtendRepository;
    private final PeopleExtendRepository peopleExtendRepository;
    private final CustomersExtendRepository customersExtendRepository;
    private final OrdersExtendRepository ordersExtendRepository;
    private final ReviewsMapper reviewsMapper;

    public ReviewsExtendServiceImpl(ReviewsExtendRepository reviewsExtendRepository, PeopleExtendRepository peopleExtendRepository, CustomersExtendRepository customersExtendRepository, OrdersExtendRepository ordersExtendRepository, ReviewsMapper reviewsMapper) {
        this.reviewsExtendRepository = reviewsExtendRepository;
        this.peopleExtendRepository = peopleExtendRepository;
        this.customersExtendRepository = customersExtendRepository;
        this.ordersExtendRepository = ordersExtendRepository;
        this.reviewsMapper = reviewsMapper;
    }

//    public Reviews getReviewsByProductId(Long productId) {
//        return this.reviewsExtendRepository.findReviewsByProductId(productId);
//    }

    public ReviewsDTO postReview(Principal principal, ReviewsDTO reviewsDTO, Long orderId) {
        People people = getUserFromPrinciple(principal);
        reviewsDTO.setReviewerName(people.getFullName());
        reviewsDTO.setEmailAddress(people.getEmailAddress());
        reviewsDTO.setReviewDate(LocalDate.now());
        Reviews reviews = reviewsMapper.toEntity(reviewsDTO);
        Optional<Orders> orders = ordersExtendRepository.findById(orderId);
        if (orders.isPresent()) {
            reviews.setOrder(orders.get());
        }
        reviews = reviewsExtendRepository.save(reviews);
        return reviewsMapper.toDto(reviews);
    }

    public List<Orders> findAllOrderedProducts(Principal principal) {
        try {
            People people = getUserFromPrinciple(principal);
            Customers customer = customersExtendRepository.findCustomersByPersonId(people.getId());
            List<Orders> ordersList = ordersExtendRepository.findAllByCustomerIdOrderByCreatedDateDesc(customer.getId());

            List<Orders> paidOrders = new ArrayList<Orders>();
            for (Orders order : ordersList) {
                if (order.getPaymentStatus() > 0) {
                    paidOrders.add(order);
                }
            }
            return paidOrders;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    private People getUserFromPrinciple(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new IllegalArgumentException("Invalid access");
        }

        People people = peopleExtendRepository.findPeopleByLogonName(principal.getName());
        if (people == null) {
            throw new IllegalArgumentException("User not found");
        }
        return people;
    }

}
