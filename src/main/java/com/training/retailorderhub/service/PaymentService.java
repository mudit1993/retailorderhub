package com.training.retailorderhub.service;

import com.training.retailorderhub.strategy.CreditCardStrategy;
import com.training.retailorderhub.strategy.GiftCardStrategy;
import com.training.retailorderhub.strategy.PaymentStrategy;
import com.training.retailorderhub.strategy.PaypalStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentService {

    private final Map<String, PaymentStrategy> strategyMap;

    public PaymentService(Map<String, PaymentStrategy> strategyMap) {
        this.strategyMap = strategyMap;
    }

    public boolean charge(String paymentMethod, double amount){
        PaymentStrategy paymentStrategy = strategyMap.get(paymentMethod);
        if(paymentStrategy == null){
            System.out.println("Unknown payment method: " + paymentMethod);
            return false;
        }
        return paymentStrategy.charge(amount);
    }
}
