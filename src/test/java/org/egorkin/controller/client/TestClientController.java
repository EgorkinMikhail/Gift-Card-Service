package org.egorkin.controller.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = { "app.resource.clients=https://jsonplaceholder.typicode.com/users" })
public class TestClientController {
    @Value("${app.resource.clients}")
    String api;

    @Test
    public void testLoadClients() {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(api);
        String page = restTemplate.getForObject(api, String.class);
        System.out.println(page);
    }
}
