package com.egorkin.controller.client;

import com.egorkin.model.datamodel.Client;
import com.egorkin.web.clientservice.ClientServiceRest;
import com.egorkin.web.clientservice.ClientServiceRestImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = { "app.resource.clients=https://jsonplaceholder.typicode.com/users" })
@ContextConfiguration(classes = ClientServiceRestImpl.class)
public class TestClientController {
    @Autowired
    ClientServiceRest clientService;

    @Test
    public void testLoadClientsFromUrl() {
        List<Client> clientsData = clientService.getClientsFromUrl();
        clientsData.forEach(System.out::println);
        Assert.assertFalse(clientsData.isEmpty());
    }
}
