package com.egorkin.web.clientservice;

import com.egorkin.model.datamodel.Client;

import java.util.List;

public interface ClientServiceRest {

    List<Client> getClientsFromUrl();
}
