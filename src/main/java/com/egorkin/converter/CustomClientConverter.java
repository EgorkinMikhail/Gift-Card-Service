package com.egorkin.converter;

import com.egorkin.exceptions.IncorrectValueException;
import com.egorkin.model.datamodel.Address;
import com.egorkin.model.datamodel.Client;
import com.egorkin.model.datamodel.Company;
import com.egorkin.model.db.entities.ClientEntity;

import static com.egorkin.converter.PojoJsonConverter.toPojoValue;

public class CustomClientConverter {
    private static CustomClientConverter ourInstance = new CustomClientConverter();

    public static CustomClientConverter getInstance() {
            return ourInstance;
    }

    public static Client convertEntityToClientModel(ClientEntity clientEntity) {
        try {
            final Integer id = clientEntity.getId();
            final String name = clientEntity.getName();
            final String username = clientEntity.getUsername();
            final String email = clientEntity.getEmail();
            final Address address = toPojoValue(clientEntity.getAddress(), Address.class);
            final String phone = clientEntity.getPhone();
            final String website = clientEntity.getWebsite();
            final Company company = toPojoValue(clientEntity.getCompany(), Company.class);
            final Boolean winner = clientEntity.getWinner();

            return new Client(id, name, username, email, address, phone, website, company, winner);

        } catch (Exception e) {
            throw new IncorrectValueException("Error converting ClientEntity: " + e.getMessage());
        }
    }

    public static Client transformClientToClientModel(Client client) {
        try {
            final Integer id = client.getId();
            final String name = client.getName();
            final String username = client.getUsername();
            final String email = client.getEmail();
            final Address address = client.getAddress();
            final String phone = client.getPhone();
            final String website = client.getWebsite();
            final Company company = client.getCompany();
            final Boolean winner = client.getWinner();

            return new Client(id, name, username, email, address, phone, website, company, winner);

        } catch (Exception e) {
            throw new IncorrectValueException("Error converting ClientEntity: " + e.getMessage());
        }
    }
}
