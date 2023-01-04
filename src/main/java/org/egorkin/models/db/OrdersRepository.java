package org.egorkin.models.db;

import org.egorkin.models.db.entities.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, String> {

    @Transactional(readOnly = true)
    List<OrdersEntity> findAll();
}
