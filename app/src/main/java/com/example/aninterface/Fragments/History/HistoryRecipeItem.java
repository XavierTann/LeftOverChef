package com.example.aninterface.Fragments.History;

public class HistoryRecipeItem {

    private String recipeThumbnail;
    private String recipeName;
    private String recipeDescription;

    private boolean liked;

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getRecipeThumbnail() {
        return recipeThumbnail;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public HistoryRecipeItem(String recipeThumbnail, String recipeName, String recipeDescription) {
        this.recipeThumbnail = recipeThumbnail;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
    }
}
