package com.resource.server.repository;

import org.springframework.data.jpa.repository.Query;

public interface InvoicesExtendRepository extends InvoicesRepository {
    @Query(value = "Select nextval(pg_get_serial_sequence('invoices', 'id'))", nativeQuery = true)
    Long getNextSeriesId();
}
