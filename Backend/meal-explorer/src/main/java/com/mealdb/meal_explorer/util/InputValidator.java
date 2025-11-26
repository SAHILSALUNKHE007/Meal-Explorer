package com.mealdb.meal_explorer.util;

import com.mealdb.meal_explorer.exception.BadRequestException;

public class InputValidator {

    public static void validateSearchName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new BadRequestException("Search term cannot be empty.");

        if (name.length() < 2)
            throw new BadRequestException("Search term must contain at least 2 characters.");

        if (!name.matches("^[A-Za-z\\s]+$"))
            throw new BadRequestException("Invalid search term. Only letters allowed.");
    }

    public static void validateCategory(String category) {
        if (category == null || category.isBlank())
            throw new BadRequestException("Category is required.");

        if (!category.matches("^[A-Za-z\\s]+$"))
            throw new BadRequestException("Invalid category.");
    }

    public static void validateMealId(String id) {
        if (!id.matches("^[0-9]+$"))
            throw new BadRequestException("Invalid Meal ID.");
    }
}
