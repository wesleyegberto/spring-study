package com.wesleyegberto.ecommerce.orders.management;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	@Query(
		value = "SELECT SUM(total) FROM orders WHERE client_id = ?1 AND status != 'REJECTED' AND created_at >= (CURRENT_DATE - 30)",
		nativeQuery = true
	)
	int sumClientOrdersTotalLast30Days(long id);
}
