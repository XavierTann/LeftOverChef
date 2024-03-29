package com.example.aninterface.Fragments;

public class favouritesRecipeItem {

    private int recipeThumbnail;
    private String recipeName;
    private String recipeDescription;
//    private int recipeImage;

    public int getRecipeThumbnail() {
        return recipeThumbnail;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

//    public int getRecipeImage() {
//        return recipeImage;
//    }

    public favouritesRecipeItem(int recipeThumbnail, String recipeName, String recipeDescription) {
        this.recipeThumbnail = recipeThumbnail;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
//        this.recipeImage = recipeImage;
    }




}