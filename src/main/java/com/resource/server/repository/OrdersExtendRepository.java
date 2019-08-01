package com.resource.server.repository;

import com.resource.server.domain.Orders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrdersExtendRepository extends PagingAndSortingRepository<Orders, Long> {
    List<Orders> findAllByCustomerIdOrderByCreatedDateDesc(Long id, Pageable pageable);

    List<Orders> findAllByCustomerIdOrderByCreatedDateDesc(Long id);

    Integer countAllByCustomerId(Long id);

    @Query(value = "Select nextval(pg_get_serial_sequence('orders', 'id'))", nativeQuery = true)
    Long getNextSeriesId();
}
