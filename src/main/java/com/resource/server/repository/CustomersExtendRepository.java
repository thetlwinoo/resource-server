package com.resource.server.repository;


import com.resource.server.domain.Customers;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersExtendRepository extends CustomersRepository {
    Customers findCustomersByPersonId(Long personId);
}
