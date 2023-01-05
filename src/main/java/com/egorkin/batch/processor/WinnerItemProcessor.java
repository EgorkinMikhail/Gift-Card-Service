package com.egorkin.batch.processor;

import com.egorkin.exceptions.IncorrectValueException;
import com.egorkin.model.datamodel.Client;
import com.egorkin.model.datamodel.Order;
import com.egorkin.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
@Slf4j
public class WinnerItemProcessor implements ItemProcessor<Order, Client> {
    @Autowired
    ClientService clientService;

    @Override
    public Client process(final Order order) {
        try {
            Client client = clientService.saveClientAsWinnerById(order.getUserId());
            log.info("selected winner for order (" + order + ") was updated in clients table (" + client + ")");
            return client;

        } catch (Exception e) {
            throw new IncorrectValueException("Error winner processing: " + e.getMessage());
        }
    }
}
