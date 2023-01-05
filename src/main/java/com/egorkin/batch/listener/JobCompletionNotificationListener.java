package com.egorkin.batch.listener;

import com.egorkin.converter.PojoJsonConverter;
import com.egorkin.exceptions.IncorrectValueException;
import com.egorkin.model.datamodel.Address;
import com.egorkin.model.datamodel.Client;
import com.egorkin.model.datamodel.Company;
import com.egorkin.model.datamodel.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.egorkin.converter.PojoJsonConverter.toPojoValue;

@Slf4j
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("JOB FINISHED");
            log.info("Verify orders");
            jdbcTemplate.query("SELECT user_id, amount FROM orders",
                    (rs, row) -> new Order(
                            rs.getInt(1),
                            rs.getDouble(2))
            ).forEach(order -> log.info("Found order <" + order + "> in the database."));

            log.info("Verify clients");
            jdbcTemplate.query("SELECT id, name, username, email, address, phone, website, company, winner " +
                            "  FROM clients",
                    (rs, row) -> {
                        try {
                            return new Client(
                                    rs.getInt(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    toPojoValue(rs.getString(5), Address.class),
                                    rs.getString(6),
                                    rs.getString(7),
                                    toPojoValue(rs.getString(8), Company.class),
                                    rs.getBoolean(9));
                        } catch (IOException e) {
                            log.error(e.getMessage());
                            throw new IncorrectValueException(e.getMessage());
                        }
                    }
            ).forEach(client -> log.info("Found client <" + client + "> in the database."));
        }
    }
}
