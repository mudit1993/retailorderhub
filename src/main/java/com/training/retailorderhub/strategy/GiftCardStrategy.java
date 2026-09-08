package com.training.retailorderhub.strategy;

public class GiftCardStrategy implements PaymentStrategy {
    @Override
    public boolean charge(double amount) {
        System.out.println("Charging gift card: " + amount);
        return true;
    }
}

