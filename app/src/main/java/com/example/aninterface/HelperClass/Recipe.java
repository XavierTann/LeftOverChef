package com.example.aninterface.HelperClass;

import java.util.List;

public class Recipe {
    private String recipeName;
    private String cookingInstructions;
    private String ingredients;
    private String cookingTime; // Represented as a string
    private String cookingDifficulty;

    // Default constructor (required for Firebase)
    public Recipe() {
    }

    // Constructor with parameters
    public Recipe(String recipeName, String cookingInstructions, String ingredients, String cookingTime, String cookingDifficulty) {
        this.recipeName = recipeName;
        this.cookingInstructions = cookingInstructions;
        this.ingredients = ingredients;
        this.cookingTime = cookingTime;
        this.cookingDifficulty = cookingDifficulty;
    }

    // Getters and setters
    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getCookingInstructions() {
        return cookingInstructions;
    }

    public void setCookingInstructions(String cookingInstructions) {
        this.cookingInstructions = cookingInstructions;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getCookingDifficulty() {
        return cookingDifficulty;
    }

    public void setCookingDifficulty(String cookingDifficulty) {
        this.cookingDifficulty = cookingDifficulty;
    }
}






