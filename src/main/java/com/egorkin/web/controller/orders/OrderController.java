package com.egorkin.web.controller.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface OrderController {
    ResponseEntity<?> getAllOrders() throws IOException;
}
