package com.training.retailorderhub.strategy;

public interface PaymentStrategy {
    public boolean charge( double amount);
}
