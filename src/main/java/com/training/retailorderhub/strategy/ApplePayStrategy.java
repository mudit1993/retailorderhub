package com.training.retailorderhub.strategy;

import org.springframework.stereotype.Component;

@Component("APPLE_PAY")
public class ApplePayStrategy implements PaymentStrategy {
    @Override
    public boolean charge(double amount) {
        System.out.println("Charging Apple pay: " + amount);
        return true;
    }
}
