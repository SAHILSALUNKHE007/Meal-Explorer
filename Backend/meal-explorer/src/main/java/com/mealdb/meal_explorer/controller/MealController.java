package com.mealdb.meal_explorer.controller;

import com.mealdb.meal_explorer.dto.MealDetailDto;
import com.mealdb.meal_explorer.dto.MealSummaryDto;
import com.mealdb.meal_explorer.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MealController {

    @Autowired
    private MealService mealService;

    @GetMapping("/search/{name}")
    public List<MealSummaryDto> searchMeals(@PathVariable String name){
        return  mealService.searchMeals(name);
    }

    @GetMapping("/category/{name}")
    public List<MealSummaryDto> getMealsByCategory(@PathVariable String name) {
        return mealService.getMealsByCategory(name);
    }

    @GetMapping("/meal/{id}")
    public MealDetailDto getMealById(@PathVariable String id) {
        return mealService.getMealById(id);
    }

    @GetMapping("/random")
    public MealDetailDto getRandomMeal() {
        return mealService.getRandomMeal();
    }
    @GetMapping("/categories")
    public List<String> getCategories() {
        return mealService.getCategories();
    }



}
