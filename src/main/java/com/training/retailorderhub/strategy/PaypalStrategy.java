package com.training.retailorderhub.strategy;

import org.springframework.stereotype.Component;

@Component("PAYPAL")
public class PaypalStrategy implements PaymentStrategy {
    @Override
    public boolean charge(double amount) {
        System.out.println("Charging PayPal: " + amount);
        return true;
    }
}

