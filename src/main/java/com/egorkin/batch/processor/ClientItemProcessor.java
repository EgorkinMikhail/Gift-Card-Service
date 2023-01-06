package com.egorkin.batch.processor;

import com.egorkin.exceptions.IncorrectValueException;
import com.egorkin.model.datamodel.Address;
import com.egorkin.model.datamodel.Client;
import com.egorkin.model.datamodel.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import static com.egorkin.converter.CustomClientConverter.transformClientToClientModel;

@Slf4j
public class ClientItemProcessor implements ItemProcessor<Client, Client> {
    @Override
    public Client process(final Client client) {
        try {
            final Client transformedOrder = transformClientToClientModel(client);
            log.info("Converting (" + client + ") into (" + transformedOrder + ")");
            return transformedOrder;
        } catch (Exception e) {
            throw new IncorrectValueException("Error converting (" + client + "): " + e.getMessage());
        }
    }

}
