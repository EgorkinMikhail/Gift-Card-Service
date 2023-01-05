package com.egorkin.service;

import com.egorkin.exceptions.IncorrectValueException;
import com.egorkin.model.datamodel.Order;
import com.egorkin.model.db.OrdersRepository;
import com.egorkin.model.db.entities.OrdersEntity;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class ClientWinnerService implements WinnerService<Order> {
    @Autowired
    OrdersRepository ordersRepository;
    @Value("${app.winner.amount}")
    int amount;

    @Override
    public Order selectWinner() {
        List<OrdersEntity> ordersEntityList = ordersRepository.findAllWithAmount(amount);
        Random random = new Random();

        int sizeOfQuestionList = ordersEntityList.size();
        int randomIndex = 0;
        if (sizeOfQuestionList == 0) {
            throw new IncorrectValueException("No orders founded by conditions. Winner couldn't be found");
        }
        if (sizeOfQuestionList > 1) {
            randomIndex = random.nextInt(sizeOfQuestionList);
        }

        OrdersEntity ordersEntity = ordersEntityList.get(randomIndex);
        return new Order(ordersEntity.getUser_id(), ordersEntity.getAmount());
    }

}
