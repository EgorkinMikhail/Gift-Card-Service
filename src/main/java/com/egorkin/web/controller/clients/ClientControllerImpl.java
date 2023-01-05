package com.egorkin.web.controller.clients;

import com.egorkin.converter.PojoJsonConverter;
import com.egorkin.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController {

    private final ClientService clientService;

    @Override
    @GetMapping("/getWinnerClient")
    public ResponseEntity<?> getWinnerClient() throws IOException {
        return ResponseEntity.ok(PojoJsonConverter.toJson(clientService.getWinnerClient()));
    }
}
