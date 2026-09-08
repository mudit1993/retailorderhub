package com.training.retailorderhub.strategy;

public class CreditCardStrategy implements PaymentStrategy {
    @Override
    public boolean charge(double amount) {
        System.out.println("Charging credit card: " + amount);
        return true;
    }
}
