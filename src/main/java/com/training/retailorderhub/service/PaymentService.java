package com.training.retailorderhub.service;

import com.training.retailorderhub.strategy.CreditCardStrategy;
import com.training.retailorderhub.strategy.GiftCardStrategy;
import com.training.retailorderhub.strategy.PaymentStrategy;
import com.training.retailorderhub.strategy.PaypalStrategy;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private PaymentStrategy paymentStrategy;

    public boolean charge(String paymentMethod, double amount){

        switch(paymentMethod) {
            case "CREDIT_CARD" :
                paymentStrategy = new CreditCardStrategy();
                break;
            case "PAYPAL" :
                paymentStrategy = new PaypalStrategy();
                break;
            case "GIFT_CARD" :
                paymentStrategy = new GiftCardStrategy();
                break;
            default:
                System.out.println("Unknown payment method: " + paymentMethod);
                return false;
        }
      return paymentStrategy.charge(amount);
    }
}
