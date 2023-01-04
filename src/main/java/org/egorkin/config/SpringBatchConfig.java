package org.egorkin.config;

import lombok.extern.slf4j.Slf4j;
import org.egorkin.models.datamodel.Order;
import org.egorkin.processor.JobCompletionNotificationListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    @Value("${app.resource.csv-folder}")
    String file;

    @Bean
    public Tasklet clearOrdersTasklet(JdbcTemplate jdbcTemplate) {
        return (stepContribution, chunkContext) -> {
            log.info("Clear orders table");
            jdbcTemplate.update("delete from orders");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step setupStep(Tasklet clearOrdersTasklet, JobRepository jobRepository,
                          PlatformTransactionManager transactionManager) {
        return new StepBuilder("setupStep", jobRepository)
                .tasklet(clearOrdersTasklet, transactionManager)
                .build();
    }

    @Bean
    public FlatFileItemReader<Order> reader() {
        FlatFileItemReader<Order> reader = new FlatFileItemReader<Order>();
        reader.setResource(new FileSystemResource(file));
        reader.setLineMapper(new DefaultLineMapper<Order>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "user_id", "amount" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Order>() {{
                setTargetType(Order.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ItemProcessor<Order, Order> processor() {
        return order -> order;
    }

    @Bean
    public JdbcBatchItemWriter<Order> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Order>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO orders (user_id, amount) VALUES (:userId, :amount)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importOrderLoaderJob(JobRepository jobRepository,
                             JobCompletionNotificationListener listener, Step setupStep, Step loadCsvStep) {
        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(setupStep)
                .next(loadCsvStep)
                .build();
    }

    @Bean
    public Step loadCsvStep(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager, JdbcBatchItemWriter<Order> writer) {
        return new StepBuilder("loadCsvStep", jobRepository)
                .<Order, Order> chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
