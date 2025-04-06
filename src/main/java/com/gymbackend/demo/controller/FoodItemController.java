package com.gymbackend.demo.controller;

import com.gymbackend.demo.model.FoodItem;
import com.gymbackend.demo.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/food-items")
@CrossOrigin("*")  // Allow frontend requests
public class FoodItemController {
    
    @Autowired
    private FoodItemService foodItemService;

    // 1️⃣ Get All Food Items
    @GetMapping
    public ResponseEntity<List<FoodItem>> getAllItems() {
        return ResponseEntity.ok(foodItemService.getAllItems());
    }

    // 2️⃣ Get Food Items by Category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<FoodItem>> getItemsByCategory(@PathVariable String category) {
        try {
            FoodItem.Category foodCategory = FoodItem.Category.valueOf(category.toUpperCase());
            return ResponseEntity.ok(foodItemService.getItemsByCategory(foodCategory));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 3️⃣ Add New Food Item
    
    @PostMapping("/add")
    public ResponseEntity<?> addFoodItem(@RequestBody FoodItem foodItem) {
        if (foodItem.getName() == null || foodItem.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Item name is required!");
        }
        if (foodItem.getCategory() == null) {
            return ResponseEntity.badRequest().body("Category is required!");
        }

        System.out.println("✅ Received Request: Name = " + foodItem.getName() + ", Category = " + foodItem.getCategory());

        return ResponseEntity.ok(foodItemService.addItem(foodItem));
    }

    
    // 4️⃣ Delete Food Item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable Long id) {
        foodItemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    // 5️⃣ Get Food Item by ID
    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> getFoodItemById(@PathVariable Long id) {
        Optional<FoodItem> item = foodItemService.getItemById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
