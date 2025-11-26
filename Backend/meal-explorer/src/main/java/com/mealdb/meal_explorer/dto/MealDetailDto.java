package com.mealdb.meal_explorer.dto;

import java.util.List;

public record MealDetailDto(
        String id,
        String name,
        String category,
        String area,
        String instructions,
        String thumbnailUrl,
        String youtubeUrl,
        List<IngredientDto> ingredients
) {}
