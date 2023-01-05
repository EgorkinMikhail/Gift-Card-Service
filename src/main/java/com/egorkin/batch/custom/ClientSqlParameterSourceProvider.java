package com.egorkin.batch.custom;

import com.egorkin.exceptions.IncorrectValueException;
import com.egorkin.model.datamodel.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.HashMap;

import static com.egorkin.converter.PojoJsonConverter.toJson;

public class ClientSqlParameterSourceProvider implements ItemSqlParameterSourceProvider<Client> {

    @Override
    public SqlParameterSource createSqlParameterSource(final Client item) {
        return new MapSqlParameterSource(new HashMap<String, Object>() {
            {
                try {
                    put("id", item.getId());
                    put("name", item.getName());
                    put("username", item.getUsername());
                    put("email", item.getEmail());
                    put("address", toJson(item.getAddress()));
                    put("phone", item.getPhone());
                    put("website", item.getWebsite());
                    put("company", toJson(item.getCompany()));
                    put("winner", item.getWinner());
                } catch (JsonProcessingException e) {
                    throw new IncorrectValueException(e.getMessage());
                }
            }
        });
    }
}

