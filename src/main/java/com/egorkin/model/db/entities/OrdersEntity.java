package com.egorkin.model.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrdersEntity {
    @Id
    private String user_id;
    private Double amount;
}
