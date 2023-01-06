package com.egorkin.batch.stepscope;

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
@TestPropertySource(locations = {"classpath:application-test.yaml"}, properties = "app.winner.amount=20")
@Configuration
@EnableJpaRepositories("com.egorkin.model.db")
@ActiveProfiles("test")
@Profile("test")
@TestConfiguration
@SpringBootTest
@EnableAutoConfiguration
public class TestBatchSteps {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void testImportOrdersStep(@Autowired Job job) throws Exception {
        this.jobLauncherTestUtils.setJob(job);
        this.jdbcTemplate.update("delete from orders");

        JobExecution jobExecution = jobLauncherTestUtils.launchStep("importOrders");

        Assertions.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void testImportClients(@Autowired Job job) throws Exception {
        this.jobLauncherTestUtils.setJob(job);
        this.jdbcTemplate.update("delete from clients");

        JobExecution jobExecution = jobLauncherTestUtils.launchStep("importClients");

        Assertions.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void testFindWinnerStep(@Autowired Job job) throws Exception {
        this.jobLauncherTestUtils.setJob(job);

        JobExecution jobExecution = jobLauncherTestUtils.launchStep("findWinnerStep");

        Assertions.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
    }

}
