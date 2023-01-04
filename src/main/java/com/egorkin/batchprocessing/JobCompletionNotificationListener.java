package com.egorkin.batchprocessing;

import com.egorkin.model.datamodel.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("JOB FINISHED");
			log.info("Verify the results");

			jdbcTemplate.query("SELECT user_id, amount FROM orders",
				(rs, row) -> new Order(
					rs.getString(1),
					rs.getDouble(2))
			).forEach(order -> log.info("Found <" + order + "> in the database."));
		}
	}
}
