package com.training.retailorderhub.strategy;

import org.springframework.stereotype.Component;

@Component("DEBIT_CARD")
public class DebitCardStrategy implements PaymentStrategy {
    @Override
    public boolean charge(double amount) {
        System.out.println("Charging Debit card: " + amount);
        return true;
    }
}
