package org.egorkin.web.controller.orders;

import org.springframework.http.ResponseEntity;

public interface OrderController {
    ResponseEntity<?> getAllOrders();
}
