package com.egorkin.batch.tasklet;

import com.egorkin.exceptions.IncorrectValueException;
import com.egorkin.model.datamodel.Client;
import com.egorkin.model.datamodel.Order;
import com.egorkin.service.ClientService;
import com.egorkin.service.WinnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class SelectWinnerTaskLet implements Tasklet {
    @Autowired
    WinnerService<Order> clientWinnerService;
    @Autowired
    ClientService clientService;

    @Override
    public RepeatStatus execute(StepContribution contribution,
                                ChunkContext chunkContext) throws Exception {
        Order order = clientWinnerService.selectWinner();
        try {
            Client client = clientService.saveClientAsWinnerById(order.getUserId());
            log.info("For order (" + order + ") was updated client (" + client + ")");

        } catch (Exception e) {
            throw new IncorrectValueException("Error winner processing: " + e.getMessage());
        }

        return RepeatStatus.FINISHED;
    }

}
