package com.egorkin.service;

import com.egorkin.model.datamodel.Client;

public interface ClientService {
    Client saveClientAsWinnerById(Integer keyId);
    Client getWinnerClient();
}
