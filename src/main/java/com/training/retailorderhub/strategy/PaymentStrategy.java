package com.training.retailorderhub.strategy;

public interface PaymentStrategy {
    boolean charge( double amount);
}
