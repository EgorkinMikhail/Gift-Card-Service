package com.egorkin.web.controller.orders;

import com.egorkin.converter.PojoJsonConverter;
import com.egorkin.model.datamodel.Order;
import com.egorkin.model.db.OrdersRepository;
import com.egorkin.model.db.entities.OrdersEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController{

    private final OrdersRepository ordersRepository;

    @Override
    @GetMapping("/getAllOrders")
    public ResponseEntity<String> getAllOrders() throws IOException {
        List<OrdersEntity> ordersEntityList = ordersRepository.findAll();
        OrdersEntity ordersEntity = ordersRepository.findAll().get(0);
        System.out.println(ordersEntity.toString());
        Order orders = PojoJsonConverter.toPojoValue(ordersRepository.findAll().get(0).toString(), Order.class);
        return ResponseEntity.ok(PojoJsonConverter.toJson(orders));
    }
}
