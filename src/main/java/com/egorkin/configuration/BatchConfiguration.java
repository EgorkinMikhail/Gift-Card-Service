package com.egorkin.configuration;

import com.egorkin.batch.custom.ClientSqlParameterSourceProvider;
import com.egorkin.batch.listener.JobCompletionNotificationListener;
import com.egorkin.batch.processor.ClientItemProcessor;
import com.egorkin.batch.processor.OrderItemProcessor;
import com.egorkin.batch.reader.ClientItemReader;
import com.egorkin.batch.tasklet.SelectWinnerTaskLet;
import com.egorkin.exceptions.IncorrectValueException;
import com.egorkin.model.datamodel.Client;
import com.egorkin.model.datamodel.Order;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration {
    @Value("${app.resource.csv-file}")
    String FILE;
    @Value("${app.batch.chunk-size}")
    int CHUNK_SIZE;

    @Bean
    @StepScope
    public FlatFileItemReader<Order> orderReader() {
        return new FlatFileItemReaderBuilder<Order>()
                .name("orderItemReader")
                .linesToSkip(1)
                .resource(new ClassPathResource(FILE))
                .delimited()
                .names("user_id", "amount")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Order>() {{
                    setTargetType(Order.class);
                }})
                .build();
    }

    @Bean
    public OrderItemProcessor orderProcessor() {
        return new OrderItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Order> orderWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Order>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO orders (user_id, amount) VALUES (:userId, :amount)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public ItemReader<Client> clientReader() {
        return new ClientItemReader();
    }

    @Bean
    public ClientItemProcessor clientProcessor() {
        return new ClientItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Client> clientWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Client>()
                .itemSqlParameterSourceProvider(new ClientSqlParameterSourceProvider())
                .sql("INSERT INTO clients (id, name, username, email, address, phone, website, company, winner) " +
                        "VALUES (:id, :name, :username, :email, :address, :phone, :website, :company, :winner)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public SelectWinnerTaskLet selectWinnerTaskLet() {
        return new SelectWinnerTaskLet();
    }

    @Bean
    public Step selectWinnerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("selectWinnerStep", jobRepository)
                .tasklet(selectWinnerTaskLet(), transactionManager)
                .build();
    }

    @Bean
    public Step importOrders(JobRepository jobRepository,
                             PlatformTransactionManager transactionManager, JdbcBatchItemWriter<Order> writer) {
        return new StepBuilder("importOrders", jobRepository)
                .<Order, Order>chunk(CHUNK_SIZE, transactionManager)
                .faultTolerant()
                .skip(IncorrectValueException.class)
                .skip(FlatFileParseException.class)
                .skipLimit(3)
                .reader(orderReader())
                .processor(orderProcessor())
                .writer(writer)
                .build();
    }

    @Bean
    public Step importClients(JobRepository jobRepository, PlatformTransactionManager transactionManager, JdbcBatchItemWriter<Client> writer) {
        return new StepBuilder("importClients", jobRepository)
                .<Client, Client>chunk(CHUNK_SIZE, transactionManager)
                .faultTolerant()
                .skip(IncorrectValueException.class)
                .skipLimit(3)
                .reader(clientReader())
                .processor(clientProcessor())
                .writer(writer)
                .build();
    }

    @Bean
    public Job GiftCardWinnerServiceJob(JobRepository jobRepository,
                                        JobCompletionNotificationListener listener, Step importOrders, Step importClients, Step selectWinnerStep) {
        return new JobBuilder("GiftCardWinnerServiceJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(importOrders)
                .next(importClients)
                .next(selectWinnerStep)
                .build();
    }
}
