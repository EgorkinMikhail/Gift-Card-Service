package com.egorkin.web.clientservice;

import com.egorkin.model.datamodel.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ClientServiceRestImpl implements ClientServiceRest {
    @Value("${app.resource.clients}")
    String apiUrl;

    @Override
    public List<Client> getClientsFromUrl() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Client[]> response;
        try {
            response = restTemplate.getForEntity(apiUrl, Client[].class);
        } catch (RestClientException e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

}
