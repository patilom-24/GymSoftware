package com.gymbackend.demo.service;

import com.gymbackend.demo.model.FoodItem;
import com.gymbackend.demo.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FoodItemService {
    @Autowired
    private FoodItemRepository foodItemRepository;

    public List<FoodItem> getAllItems() {
        return foodItemRepository.findAll();
    }

    public List<FoodItem> getItemsByCategory(FoodItem.Category category) {
        return foodItemRepository.findByCategory(category);
    }

    public FoodItem addItem(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }
    public void deleteItem(Long id) {
        foodItemRepository.deleteById(id);
    }

    public Optional<FoodItem> getItemById(Long id) {
        return foodItemRepository.findById(id);
    }
}
