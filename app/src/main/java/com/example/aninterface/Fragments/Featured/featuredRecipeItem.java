package com.example.aninterface.Fragments.Featured;

public class featuredRecipeItem {

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

    public String getRecipeThumbnail() {return recipeThumbnail;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public featuredRecipeItem(String recipeThumbnail, String recipeName, String recipeDescription) {
        this.recipeThumbnail = recipeThumbnail;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
    }
}
