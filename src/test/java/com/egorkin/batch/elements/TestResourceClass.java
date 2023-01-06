package com.egorkin.batch.elements;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

@TestPropertySource(properties = "app.resource.csv-file=resources/orders.csv")
@Configuration
public class TestResourceClass {
    @Value("${app.resource.csv-file}")
    String file;

    @Test
    public void name() throws IOException {
        Resource resource = new FileSystemResource("resources/order.csv");
        System.out.println(resource.getFile().getAbsolutePath());
    }
}
