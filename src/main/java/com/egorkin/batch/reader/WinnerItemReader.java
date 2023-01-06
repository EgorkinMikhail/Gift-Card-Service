package com.egorkin.batch.reader;

import com.egorkin.model.datamodel.Order;
import com.egorkin.service.WinnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class WinnerItemReader implements ItemReader<Order> {

    @Autowired
    WinnerService<Order> clientWinnerService;
    private boolean winnerIndex;

    public WinnerItemReader() {
        winnerIndex = false;
    }

    @Override
    public Order read() {
        log.info("started Winner Reader");
        if (winnerDataIsNotInitialized()) {
            Order order = clientWinnerService.selectWinner();
            log.info("selected winner with order: {}", order);
            this.winnerIndex = true;
            return order;
        } else {
            winnerIndex = false;
            return null;
        }
    }

    private boolean winnerDataIsNotInitialized() {
        return !this.winnerIndex;
    }
}
