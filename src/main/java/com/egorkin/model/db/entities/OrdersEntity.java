package com.egorkin.model.db.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class OrdersEntity {
    @Id
    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "amount")
    private Double amount;
}
