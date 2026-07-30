package com.example.BillingSystem.service;

import com.example.BillingSystem.entity.Inventory;
import com.example.BillingSystem.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    public Inventory getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product"));
    }

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public List<Inventory> getLowStockItems() {
        return inventoryRepository.findByQuantityLessThan(10);
    }

    public Inventory updateInventory(Long id, Inventory inventoryDetails) {
        Inventory inventory = getInventoryById(id);
        if (inventoryDetails.getQuantity() != null) inventory.setQuantity(inventoryDetails.getQuantity());
        if (inventoryDetails.getMinimumLevel() != null) inventory.setMinimumLevel(inventoryDetails.getMinimumLevel());
        if (inventoryDetails.getWarehouseLocation() != null) inventory.setWarehouseLocation(inventoryDetails.getWarehouseLocation());
        return inventoryRepository.save(inventory);
    }

    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }

    public void updateStock(Long productId, Integer quantityChange) {
        Inventory inventory = getInventoryByProductId(productId);
        inventory.setQuantity(inventory.getQuantity() + quantityChange);
        inventoryRepository.save(inventory);
    }
}
