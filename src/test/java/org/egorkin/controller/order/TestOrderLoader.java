package org.egorkin.controller.order;

import org.egorkin.models.datamodel.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestPropertySource(properties = { "app.resource.csv-folder=//Users//Mikhail_Egorkin//Documents//Java_Trainings_Project//Gift-Card-Service//src//test//resources//order.csv" })
public class TestOrderLoader {
    @Value("${app.resource.csv-folder}")
    String file;

    @Test
    public void testFileLoader() throws Exception {
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

        BufferedReader bufferedReader = new BufferedReader(new FileReader(
                file));
        String line = null;
        Scanner scanner = null;
        while ((line = bufferedReader.readLine()) != null) {
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                System.out.println(scanner.next());
            }
        }
    }
}
