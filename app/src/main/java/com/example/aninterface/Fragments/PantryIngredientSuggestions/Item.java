package com.example.aninterface.Fragments.PantryIngredientSuggestions;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private String name;
    private String unit;

    public Item(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }
    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }
    // Static method to get all ingredients
    public static List<Item> getAllIngredients() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Milk", "Millilitre(ML)"));
        items.add(new Item("Flour", "Grams(g)"));
        items.add(new Item("Butter", "Grams(g)"));
        items.add(new Item("Sugar", "Tablespoon(Tbsp)"));
        items.add(new Item("Olive Oil", "Tablespoon(Tbsp)"));
        items.add(new Item("Baking Powder", "Teaspoon(tsp)"));
        items.add(new Item("Chicken Breast", "Piece(pc)"));
        items.add(new Item("Beef", "Kilograms(kg)"));
        items.add(new Item("Eggs", "Piece(pc)"));
        items.add(new Item("Carrots", "Grams(g)"));
        items.add(new Item("Honey", "Tablespoon(Tbsp)"));
        items.add(new Item("Water", "Litre(L)"));
        items.add(new Item("Vanilla Extract", "Teaspoon(tsp)"));
        items.add(new Item("Cinnamon", "Teaspoon(tsp)"));
        items.add(new Item("Cheese", "Grams(g)"));
        items.add(new Item("Yogurt", "Millilitre(ML)"));
        items.add(new Item("Tomatoes", "Piece(pc)"));
        items.add(new Item("Potatoes", "Kilograms(kg)"));
        items.add(new Item("Lemon Juice", "Tablespoon(Tbsp)"));
        items.add(new Item("Salt", "Pinch"));
        items.add(new Item("Pepper", "Dash"));
        items.add(new Item("Ground Beef", "Pounds(lb)"));
        items.add(new Item("Rice", "Grams(g)"));
        items.add(new Item("Pasta", "Grams(g)"));
        items.add(new Item("Bread Crumbs", "Cup"));
        items.add(new Item("Apple Cider Vinegar", "Millilitre(ML)"));
        items.add(new Item("Maple Syrup", "Tablespoon(Tbsp)"));
        items.add(new Item("Almonds", "Grams(g)"));
        items.add(new Item("Raisins", "Cup"));
        items.add(new Item("Chicken Stock", "Millilitre(ML)"));
        items.add(new Item("Coconut Milk", "Millilitre(ML)"));
        items.add(new Item("Soy Sauce", "Tablespoon(Tbsp)"));
        items.add(new Item("Wine", "Millilitre(ML)"));
        items.add(new Item("Beer", "Millilitre(ML)"));
        items.add(new Item("Chocolate Chips", "Cup"));
        items.add(new Item("Oregano", "Teaspoon(tsp)"));
        items.add(new Item("Basil", "Teaspoon(tsp)"));
        items.add(new Item("Parsley", "Tablespoon(Tbsp)"));
        items.add(new Item("Rosemary", "Teaspoon(tsp)"));
        items.add(new Item("Thyme", "Teaspoon(tsp)"));
        items.add(new Item("Mozzarella Cheese", "Grams(g)"));
        items.add(new Item("Parmesan Cheese", "Grams(g)"));
        items.add(new Item("Heavy Cream", "Millilitre(ML)"));
        items.add(new Item("Sour Cream", "Millilitre(ML)"));
        items.add(new Item("Whole Chicken", "Piece(pc)"));
        items.add(new Item("Pork Loin", "Kilograms(kg)"));
        items.add(new Item("Salmon Fillet", "Piece(pc)"));
        items.add(new Item("Trout Fillet", "Piece(pc)"));
        items.add(new Item("Cod Fillet", "Piece(pc)"));
        items.add(new Item("Tuna Steak", "Piece(pc)"));
        items.add(new Item("Vegetable Broth", "Millilitre(ML)"));
        items.add(new Item("Beef Broth", "Millilitre(ML)"));
        items.add(new Item("Orange Juice", "Millilitre(ML)"));
        items.add(new Item("Grape Juice", "Millilitre(ML)"));
        items.add(new Item("Balsamic Vinegar", "Tablespoon(Tbsp)"));
        items.add(new Item("Red Wine Vinegar", "Tablespoon(Tbsp)"));
        items.add(new Item("White Wine Vinegar", "Tablespoon(Tbsp)"));
        items.add(new Item("Sesame Oil", "Tablespoon(Tbsp)"));
        items.add(new Item("Sunflower Oil", "Millilitre(ML)"));
        items.add(new Item("Canola Oil", "Millilitre(ML)"));
        items.add(new Item("Peanut Oil", "Millilitre(ML)"));
        items.add(new Item("Walnuts", "Grams(g)"));
        items.add(new Item("Pecans", "Grams(g)"));
        items.add(new Item("Cashews", "Grams(g)"));
        items.add(new Item("Pistachios", "Grams(g)"));
        items.add(new Item("Macadamia Nuts", "Grams(g)"));
        items.add(new Item("Quinoa", "Grams(g)"));
        items.add(new Item("Lentils", "Cup"));
        items.add(new Item("Chickpeas", "Cup"));
        items.add(new Item("Black Beans", "Cup"));
        items.add(new Item("Kidney Beans", "Cup"));
        items.add(new Item("Turmeric", "Teaspoon(tsp)"));
        items.add(new Item("Ginger", "Grams(g)"));
        items.add(new Item("Garlic", "Piece(pc)"));
        items.add(new Item("Onions", "Piece(pc)"));
        items.add(new Item("Mint Leaves", "Tablespoon(Tbsp)"));
        items.add(new Item("Coriander Leaves", "Tablespoon(Tbsp)"));
        items.add(new Item("Paprika", "Teaspoon(tsp)"));
        items.add(new Item("Cumin", "Teaspoon(tsp)"));
        items.add(new Item("Cocoa Powder", "Cup"));
        items.add(new Item("Icing Sugar", "Cup"));
        items.add(new Item("Brown Sugar", "Cup"));
        items.add(new Item("Powdered Gelatin", "Teaspoon(tsp)"));
        items.add(new Item("Yeast", "Teaspoon(tsp)"));
        items.add(new Item("Baking Soda", "Teaspoon(tsp)"));
        items.add(new Item("Cornstarch", "Tablespoon(Tbsp)"));
        items.add(new Item("Vinegar", "Tablespoon(Tbsp)"));
        items.add(new Item("Mustard", "Teaspoon(tsp)"));
        items.add(new Item("Saffron", "Pinch"));
        items.add(new Item("Cloves", "Piece(pc)"));
        items.add(new Item("Nutmeg", "Teaspoon(tsp)"));
        items.add(new Item("Star Anise", "Piece(pc)"));
        items.add(new Item("Cinnamon Stick", "Piece(pc)"));
        items.add(new Item("Bay Leaves", "Piece(pc)"));
        items.add(new Item("Cardamom", "Piece(pc)"));
        items.add(new Item("Chili Powder", "Teaspoon(tsp)"));
        items.add(new Item("Fennel Seeds", "Teaspoon(tsp)"));
        items.add(new Item("Poppy Seeds", "Tablespoon(Tbsp)"));
        items.add(new Item("Sesame Seeds", "Tablespoon(Tbsp)"));
        items.add(new Item("Sunflower Seeds", "Cup"));
        items.add(new Item("Pumpkin Seeds", "Cup"));
        items.add(new Item("Flaxseeds", "Tablespoon(Tbsp)"));
        items.add(new Item("Chia Seeds", "Tablespoon(Tbsp)"));
        items.add(new Item("Hemp Seeds", "Tablespoon(Tbsp)"));
        items.add(new Item("Capers", "Tablespoon(Tbsp)"));
        items.add(new Item("Olives", "Piece(pc)"));
        items.add(new Item("Pickles", "Piece(pc)"));
        items.add(new Item("Artichoke Hearts", "Piece(pc)"));
        items.add(new Item("Sun-Dried Tomatoes", "Grams(g)"));
        items.add(new Item("Anchovy Fillets", "Piece(pc)"));
        items.add(new Item("Tahini", "Tablespoon(Tbsp)"));
        items.add(new Item("Pesto", "Tablespoon(Tbsp)"));
        items.add(new Item("Maple Extract", "Teaspoon(tsp)"));
        items.add(new Item("Almond Extract", "Teaspoon(tsp)"));
        items.add(new Item("Vanilla Bean", "Piece(pc)"));
        items.add(new Item("Whipping Cream", "Millilitre(ML)"));
        items.add(new Item("Sour Dough Starter", "Cup"));
        items.add(new Item("Active Dry Yeast", "Teaspoon(tsp)"));
        items.add(new Item("Agave Syrup", "Tablespoon(Tbsp)"));
        items.add(new Item("Molasses", "Tablespoon(Tbsp)"));
        items.add(new Item("Rice Vinegar", "Tablespoon(Tbsp)"));
        items.add(new Item("Mirin", "Tablespoon(Tbsp)"));
        items.add(new Item("Fish Sauce", "Tablespoon(Tbsp)"));
        items.add(new Item("Oyster Sauce", "Tablespoon(Tbsp)"));
        items.add(new Item("Hoisin Sauce", "Tablespoon(Tbsp)"));
        items.add(new Item("Sriracha", "Tablespoon(Tbsp)"));
        items.add(new Item("Hot Sauce", "Teaspoon(tsp)"));
        items.add(new Item("Worcestershire Sauce", "Tablespoon(Tbsp)"));


        return items;
    }

}
