package com.egorkin.web.controller.clients;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ClientController {
    ResponseEntity<?> getWinnerClient() throws IOException;
}
