package org.egorkin.web.controller.clients;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController {
    @Value("${app.resource.clients}")
    private String load_client_api;

    @Override
    @GetMapping("/question/reloadClients")
    public ResponseEntity<?> loadClients() {
        RestTemplate restTemplate = new RestTemplate();
        String page = restTemplate.getForObject(load_client_api, String.class);
        System.out.println(page);
        return null;
    }
}
