package com.egorkin.model.db;

import com.egorkin.model.db.entities.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {

    @Transactional(readOnly = true)
    List<OrdersEntity> findAll();

    @Transactional(readOnly = true)
    @Query(value = "SELECT user_id, amount FROM orders WHERE amount > :amount", nativeQuery = true)
    List<OrdersEntity> findAllWithAmount(@Param("amount") int amount);
}
