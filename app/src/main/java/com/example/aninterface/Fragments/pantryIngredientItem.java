package com.example.aninterface.Fragments;

public class pantryIngredientItem {

    private String ingredientName;
    private String ingredientAmount;
    private String ingredientUnit;
    private boolean isSelected;

    public pantryIngredientItem(String ingredientName, String ingredientAmount, String ingredientUnit, boolean isSelected){
        this.ingredientName = ingredientName;
        this.ingredientAmount = ingredientAmount;
        this.ingredientUnit = ingredientUnit;
        this.isSelected = isSelected;
    }


    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientAmount() {
        return ingredientAmount;
    }

    public void setIngredientAmount(String ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
    }

    public String getIngredientUnit() {
        return ingredientUnit;
    }

    public void setIngredientUnit(String ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString(){
        return "pantryIngredientItem{" + "name=" + ingredientName + '\'' + ", isSelected=" + isSelected + '}' ;
    }
}
