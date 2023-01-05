package com.egorkin.batch.reader;

import com.egorkin.model.datamodel.Client;
import com.egorkin.web.clientservice.ClientServiceRest;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClientItemReader implements ItemReader<Client> {
    @Autowired
    ClientServiceRest clientService;

    private int nextClientIndex;
    private List<Client> clientData;

    public ClientItemReader() {
        nextClientIndex = 0;
    }

    @Override
    public Client read() throws Exception {
        if (clientsDataIsNotInitialized()) {
            clientData = clientService.getClientsFromUrl();
        }

        Client nextClient = null;

        if (nextClientIndex < clientData.size()) {
            nextClient = clientData.get(nextClientIndex);
            nextClientIndex++;
        } else {
            nextClientIndex = 0;
            clientData = null;
        }

        return nextClient;
    }

    private boolean clientsDataIsNotInitialized() {
        return this.clientData == null;
    }
}
