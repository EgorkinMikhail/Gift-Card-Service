package org.egorkin.config;

import lombok.extern.slf4j.Slf4j;
import org.egorkin.models.datamodel.Order;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.net.MalformedURLException;

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

    public ItemReader<Order> reader() throws MalformedURLException {
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
    public ItemWriter<Order> writer(DataSource dataSource) {
        JdbcBatchItemWriter<Order> writer = new JdbcBatchItemWriter<Order>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Order>());
        writer.setSql("INSERT INTO orders (user_id, amount) VALUES (:userId, :amount)");
        writer.setDataSource(dataSource);
        return writer;
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job importUserJob(JobBuilderFactory jobs, Step s1) {
        return jobs.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(s1)
                .end()
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Order> reader,
                      ItemWriter<Order> writer, ItemProcessor<Order, Order> processor) {
        return stepBuilderFactory.get("step1")
                .<Order, Order> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
    // end::jobstep[]

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
