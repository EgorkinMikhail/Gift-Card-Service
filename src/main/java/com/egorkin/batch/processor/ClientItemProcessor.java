package com.egorkin.batch.processor;

import com.egorkin.exceptions.IncorrectValueException;
import com.egorkin.model.datamodel.Address;
import com.egorkin.model.datamodel.Client;
import com.egorkin.model.datamodel.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class ClientItemProcessor implements ItemProcessor<Client, Client> {
    @Override
    public Client process(final Client client) {
        try {
            final Integer id = client.getId();
            final String name = client.getName();
            final String username = client.getUsername();
            final String email = client.getEmail();
            final Address address = client.getAddress();
            final String phone = client.getPhone();
            final String website = client.getWebsite();
            final Company company = client.getCompany();
            final Boolean winner = false;

            final Client transformedOrder = new Client(id, name, username, email, address, phone, website, company, winner);

            log.info("Converting (" + client + ") into (" + transformedOrder + ")");

            return transformedOrder;
        } catch (Exception e) {
            throw new IncorrectValueException("Error converting (" + client + "): " + e.getMessage());
        }
    }

}
