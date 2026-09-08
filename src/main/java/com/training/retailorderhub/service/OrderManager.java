package com.training.retailorderhub.service;

import com.training.retailorderhub.model.Order;
import com.training.retailorderhub.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TRAINING NOTE:
 * This class is deliberately written the way a real legacy class often looks —
 * one method doing everything, duplicated validation, and unsafe query building.
 * It is the reference "OrderManager" used in Day 1's Lab 1 (HLD vs LLD) and
 * Lab 2 (SonarCloud) materials. Do NOT use this class as a model for production
 * code — Day 2 refactors it through the SOLID principles.
 */
@Service
public class OrderManager {

    @PersistenceContext
    private EntityManager entityManager;

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;

    public OrderManager(OrderRepository orderRepository, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
    }

    @Transactional
    public boolean processOrder(String customerId, List<String> itemNames, String paymentMethod, double amount) {
        // Validate customer
        if (customerId == null || customerId.isEmpty()) {
            System.out.println("Invalid customer");
            return false;
        }
        if (itemNames == null || itemNames.isEmpty()) {
            System.out.println("Invalid items");
            return false;
        }

        // Check inventory
        for (String itemName : itemNames) {
            int qty = getInventoryQuantity(itemName);
            if (qty <= 0) {
                System.out.println("Out of stock: " + itemName);
                return false;
            }
        }

        if(!paymentService.charge(paymentMethod, amount)) {
            return false;
        }

        // Process payment
//        if (paymentMethod.equals("CREDIT_CARD")) {
//            System.out.println("Charging credit card: " + amount);
//        } else if (paymentMethod.equals("PAYPAL")) {
//            System.out.println("Charging PayPal: " + amount);
//        } else if (paymentMethod.equals("GIFT_CARD")) {
//            System.out.println("Charging gift card: " + amount);
//        } else {
//            System.out.println("Unknown payment method: " + paymentMethod);
//            return false;
//        }

        // Save order
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setItemNames(String.join(",", itemNames));
        order.setPaymentMethod(paymentMethod);
        order.setAmount(amount);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);

        // Update inventory
        for (String itemName : itemNames) {
            String updateQuery = "UPDATE product SET quantity = quantity - 1 WHERE name = '" + itemName + "'";
            entityManager.createNativeQuery(updateQuery).executeUpdate();
        }

        System.out.println("Order confirmed for customer " + customerId);
        return true;
    }

    public boolean validateCustomer(String customerId) {
        if (customerId == null || customerId.isEmpty()) {
            System.out.println("Invalid customer");
            return false;
        }
        return true;
    }

    public boolean validateItems(List<String> itemNames) {
        if (itemNames == null || itemNames.isEmpty()) {
            System.out.println("Invalid items");
            return false;
        }
        return true;
    }

    private int getInventoryQuantity(String itemName) {
        String query = "SELECT quantity FROM product WHERE name = '" + itemName + "'";
        try {
            Object result = entityManager.createNativeQuery(query).getSingleResult();
            return ((Number) result).intValue();
        } catch (jakarta.persistence.NoResultException e) {
            return 0;
        }
    }
}
