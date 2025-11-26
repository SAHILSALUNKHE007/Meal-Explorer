package com.mealdb.meal_explorer.service;

import com.mealdb.meal_explorer.dto.IngredientDto;
import com.mealdb.meal_explorer.dto.MealDetailDto;
import com.mealdb.meal_explorer.dto.MealSummaryDto;
import com.mealdb.meal_explorer.exception.ResourceNotFoundException;
import io.netty.channel.ChannelOption;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class MealService {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(MealService.class);

    public MealService() {
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .responseTimeout(Duration.ofSeconds(10))
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                ))
                .build();
    }

    @Cacheable("searchMeals")
    public List<MealSummaryDto> searchMeals(String name) {
        try {
            log.info("CACHE MISS → searchMeals('{}')", name);
            String res = callApi("search.php", "s", name);

            JsonNode mealNode = objectMapper.readTree(res).get("meals");
            if (mealNode == null || mealNode.isNull()) return List.of();

            List<MealSummaryDto> list = new ArrayList<>();
            for (JsonNode meal : mealNode) {
                list.add(new MealSummaryDto(
                        meal.get("idMeal").asText(),
                        meal.get("strMeal").asText(),
                        meal.get("strMealThumb").asText()
                ));
            }
            return list;

        } catch (Exception ex) {
            return List.of();
        }
    }

    @Cacheable("mealsByCategory")
    public List<MealSummaryDto> getMealsByCategory(String category) {
        try {

            String res = callApi("filter.php", "c", category);

            JsonNode mealsNode = objectMapper.readTree(res).get("meals");
            if (mealsNode == null || mealsNode.isNull()) return List.of();

            List<MealSummaryDto> list = new ArrayList<>();
            for (JsonNode meal : mealsNode) {
                list.add(new MealSummaryDto(
                        meal.get("idMeal").asText(),
                        meal.get("strMeal").asText(),
                        meal.get("strMealThumb").asText()
                ));
            }
            return list;

        } catch (Exception ex) {
            return List.of();
        }
    }

    @Cacheable("mealById")
    public MealDetailDto getMealById(String id) {
        try {
            String res = callApi("lookup.php", "i", id);

            JsonNode mealsNode = objectMapper.readTree(res).get("meals");
            if (mealsNode == null || mealsNode.isNull()) return null;

            JsonNode meal = mealsNode.get(0);

            List<IngredientDto> ingredients = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                String ing = meal.get("strIngredient" + i).asText("");
                String measure = meal.get("strMeasure" + i).asText("");

                if (!ing.isBlank()) {
                    ingredients.add(new IngredientDto(ing, measure));
                }
            }

            return new MealDetailDto(
                    meal.get("idMeal").asText(),
                    meal.get("strMeal").asText(),
                    meal.get("strCategory").asText(),
                    meal.get("strArea").asText(),
                    meal.get("strInstructions").asText(),
                    meal.get("strMealThumb").asText(),
                    meal.get("strYoutube").asText(),
                    ingredients
            );

        } catch (Exception ex) {
            return null;
        }
    }


    public MealDetailDto getRandomMeal() {
        try {
            String res = callApiNoParam("random.php");

            JsonNode mealsNode = objectMapper.readTree(res).get("meals");
            if (mealsNode == null || mealsNode.isNull()) return null;

            JsonNode meal = mealsNode.get(0);

            List<IngredientDto> ingredients = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                String ing = meal.get("strIngredient" + i).asText("");
                String measure = meal.get("strMeasure" + i).asText("");

                if (!ing.isBlank()) {
                    ingredients.add(new IngredientDto(ing, measure));
                }
            }

            return new MealDetailDto(
                    meal.get("idMeal").asText(),
                    meal.get("strMeal").asText(),
                    meal.get("strCategory").asText(),
                    meal.get("strArea").asText(),
                    meal.get("strInstructions").asText(),
                    meal.get("strMealThumb").asText(),
                    meal.get("strYoutube").asText(),
                    ingredients
            );

        } catch (Exception ex) {
            return null;
        }
    }

    @Cacheable("categories")
    public List<String> getCategories() {
        try {
            String res = callApiNoParam("categories.php");

            JsonNode categoriesNode = objectMapper.readTree(res).get("categories");
            if (categoriesNode == null || categoriesNode.isNull()) return List.of();

            List<String> list = new ArrayList<>();
            for (JsonNode category : categoriesNode) {
                list.add(category.get("strCategory").asText());
            }

            return list;

        } catch (Exception ex) {
            return List.of();
        }
    }

    private String callApi(String path, String key, String value) {
        return webClient.get()
                .uri(uri -> uri.path(path).queryParam(key, value).build())
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(2)))
                .block();
    }

    private String callApiNoParam(String path) {
        return webClient.get()
                .uri(path)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(2)))
                .block();
    }
}
