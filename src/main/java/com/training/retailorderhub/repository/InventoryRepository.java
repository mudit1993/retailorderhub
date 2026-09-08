package com.training.retailorderhub.repository;

import com.training.retailorderhub.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository {

    public int getQuantity(String itemName);

    public void decrementQuantity(String itemName);
}
