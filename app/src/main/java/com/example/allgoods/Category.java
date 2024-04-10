package com.example.allgoods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum Category {
    HATCHBACKS("Hatchbacks"),
    SEDANS("Sedans"),
    SUVS("SUVs"),
    CONVERTIBLES("Convertibles"),
    COUPES("Coupes"),
    ELECTRIC("Electric"),
    HYBRIDS("Hybrids");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


    public static List<Category> getAllCategories() {
        return Arrays.asList(Category.values());
    }

    // Method to get a list of random categories
    public static List<Category> getRandomCategories(int count) {
        List<Category> allCategories = new ArrayList<>(Arrays.asList(Category.values()));
        Collections.shuffle(allCategories);
        return allCategories.subList(0, Math.min(count, allCategories.size()));
    }

    public static List<Category> filterCategoriesBasedOnInput(String input) {
        return Arrays.stream(Category.values())
                .filter(category -> category.displayName.toLowerCase().contains(input.toLowerCase()))
                .collect(Collectors.toList());
    }
}
