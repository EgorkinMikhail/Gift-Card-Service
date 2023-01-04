package org.egorkin.web.controller.orders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egorkin.models.db.OrdersRepository;
import org.egorkin.models.db.entities.OrdersEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController{

    private final OrdersRepository ordersRepository;

    @Override
    @GetMapping("/getAllOrders")
    public ResponseEntity<String> getAllOrders() {
        List<OrdersEntity> ordersEntityList = ordersRepository.findAll();
        return ResponseEntity.ok(ordersEntityList.toString());
    }
}
