package com.egorkin.processor;

import com.egorkin.exceptions.IncorrectValueException;
import com.egorkin.model.datamodel.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class OrderItemProcessor implements ItemProcessor<Order, Order> {

    private static final Logger log = LoggerFactory.getLogger(OrderItemProcessor.class);

    @Override
    public Order process(final Order order) {
        try {
            final String userId = order.getUserId().toUpperCase();
            final Double amount = order.getAmount();

            final Order transformedOrder = new Order(userId, amount);

            log.info("Converting (" + order + ") into (" + transformedOrder + ")");

            return transformedOrder;
        } catch (Exception e) {
            throw new IncorrectValueException("Error converting (" + order + "): " + e.getMessage());
        }
    }

}
