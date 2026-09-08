package com.training.retailorderhub.service;

import com.training.retailorderhub.repository.InventoryRepository;
import com.training.retailorderhub.strategy.CreditCardStrategy;
import com.training.retailorderhub.strategy.GiftCardStrategy;
import com.training.retailorderhub.strategy.PaymentStrategy;
import com.training.retailorderhub.strategy.PaypalStrategy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {


    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
       this.inventoryRepository = inventoryRepository;
    }

    public int getInventoryQuantity(String itemName) {
       return inventoryRepository.getQuantity(itemName);
    }

    public boolean isInStock(String itemName){
        return getInventoryQuantity(itemName) > 0;
    }

    public void decrementQuantity(String itemName) {
        inventoryRepository.decrementQuantity(itemName);
    }

}
