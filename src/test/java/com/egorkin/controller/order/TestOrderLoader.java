package com.egorkin.controller.order;

import com.egorkin.model.datamodel.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestPropertySource(properties = {"app.resource.csv-file=order.csv"})
public class TestOrderLoader {
    @Value("${app.resource.csv-file}")
    String file;

    @Test
    public void testFileLoader() throws Exception {
        FlatFileItemReader<Order> reader = new FlatFileItemReaderBuilder<Order>()
                .name("personItemReader")
                .resource(new ClassPathResource(file))
                .delimited()
                .names(new String[]{"user_id", "amount"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Order>() {{
                    setTargetType(Order.class);
                }})
                .build();

        Resource resource = new FileSystemResource(file);
        System.out.println(resource.getFile().getAbsolutePath());

    }
}
