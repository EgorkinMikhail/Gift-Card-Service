package com.egorkin.batch.endtoend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

@SpringBatchTest
@TestPropertySource(locations = {"classpath:application-test.yaml"}, properties = "app.winner.amount=20000000")
@Configuration
@EnableJpaRepositories("com.egorkin.model.db")
@ActiveProfiles("test")
@Profile("test")
@TestConfiguration
@SpringBootTest
@EnableAutoConfiguration
public class TestBatchJobWinnerNotFound {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void testJob(@Autowired Job job) throws Exception {
        this.jobLauncherTestUtils.setJob(job);
        this.jdbcTemplate.update("delete from orders");
        this.jdbcTemplate.update("delete from clients");

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        Assertions.assertEquals("FAILED", jobExecution.getExitStatus().getExitCode());
    }
}
