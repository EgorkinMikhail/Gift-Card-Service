package org.egorkin.web.controller.clients;

import org.springframework.http.ResponseEntity;

public interface ClientController {
    ResponseEntity<?> loadClients();
}
