package org.egorkin.controller.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@ContextConfiguration
@TestPropertySource(properties = { "app.clients_api=https://jsonplaceholder.typicode.com/users" })
public class TestClientController {
    @Value("${app.clients_api}")
    String api;

    @Test
    public void testLoadClients() {
        RestTemplate restTemplate = new RestTemplate();
        String page = restTemplate.getForObject(api, String.class);
        System.out.println(page);
    }
}
