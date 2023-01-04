package org.egorkin.processor;

import lombok.extern.slf4j.Slf4j;
import org.egorkin.models.datamodel.Order;
import org.springframework.batch.item.ItemProcessor;
@Slf4j
public class OrderItemProcessor implements ItemProcessor<Order, Order> {
    @Override
    public Order process(final Order order) throws Exception {
        final String userId = order.getUserId();
        final Double amount = order.getAmount();

        final Order transformedOrder = new Order(userId, amount);

        log.info("Converting (" + order + ") into (" + transformedOrder + ")");

        return transformedOrder;
    }
}
