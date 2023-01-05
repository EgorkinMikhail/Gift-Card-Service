package com.egorkin.controller.client;

import com.egorkin.web.clientservice.ClientServiceRest;
import com.egorkin.web.clientservice.ClientServiceRestImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = { "app.resource.clients=https://jsonplaceholder.typicode.com/users" })
@ContextConfiguration(classes = ClientServiceRestImpl.class)
public class TestClientController {
    @Value("${app.resource.clients}")
    String apiUrl;

    @Autowired
    ClientServiceRest clientService;

    @Test
    public void testLoadClientsFromUrl() {
        List<?> clientsData = clientService.getClientsFromUrl();
        clientsData.forEach(System.out::println);
        Assert.assertFalse(clientsData.isEmpty());
    }
}
