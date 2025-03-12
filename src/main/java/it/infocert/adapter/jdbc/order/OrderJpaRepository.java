package it.infocert.adapter.jdbc.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends CrudRepository<OrderEntity, Long> {

}