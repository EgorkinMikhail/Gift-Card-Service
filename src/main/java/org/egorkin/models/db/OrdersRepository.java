package org.egorkin.models.db;

import org.egorkin.models.db.entities.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<OrdersEntity, String> {
}
