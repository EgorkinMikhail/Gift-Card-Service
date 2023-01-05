package com.egorkin.service;

import com.egorkin.exceptions.HttpException;
import com.egorkin.exceptions.IncorrectValueException;
import com.egorkin.model.datamodel.Client;
import com.egorkin.model.db.ClientsRepository;
import com.egorkin.model.db.entities.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.egorkin.converter.CustomClientConverter.concertEntityToClientModel;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    ClientsRepository clientsRepository;

    @Override
    public Client saveClientAsWinnerById(Integer keyId) {

        clientsRepository.clearWinners();
        ClientEntity clientEntity = clientsRepository.findById(keyId)
                .orElseThrow(() -> new IncorrectValueException("Client not found by keyId (" + keyId + ")"));
        clientEntity.setWinner(true);
        clientsRepository.save(clientEntity);

        return concertEntityToClientModel(clientEntity);
    }

    @Override
    public Client getWinnerClient() {
        ClientEntity clientEntity = clientsRepository.findWinner().orElseThrow(() -> new HttpException("Winner doesn't set in Clients table", HttpStatus.NOT_FOUND));
        return concertEntityToClientModel(clientEntity);
    }
}
