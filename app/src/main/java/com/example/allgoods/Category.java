package com.example.allgoods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    // Method to get a list of random categories
    public static List<Category> getRandomCategories(int count) {
        List<Category> allCategories = new ArrayList<>(Arrays.asList(Category.values()));
        Collections.shuffle(allCategories);
        return allCategories.subList(0, Math.min(count, allCategories.size()));
    }
}
