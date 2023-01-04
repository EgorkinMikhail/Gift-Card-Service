package org.egorkin.db;

import org.egorkin.db.entities.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<OrdersEntity, String> {
}
