package com.example.aninterface.Fragments;

import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.aninterface.CameraRecognition;
import com.example.aninterface.R;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;
public class PantryFragment extends Fragment implements SuggestionsAdapter.OnItemClickListener {
    private Button addIngredientButton;
    private ImageButton cancelButton, confirmButton;
    private EditText ingredientNameEditText, ingredientAmtEditText, ingredientUnitEditText;
    private LinearLayout actionButtonsContainer;
    private List<pantryIngredientItem> pantryIngredientItemList;
    private RecyclerView pantryRecyclerView, suggestionsRecyclerView;
    private recipeAdapterPantry recipeAdapterPantry;
    private LinearLayoutManager layoutManager;
    private SuggestionsAdapter suggestionsAdapter;
    private UnitAdapter unitAdapter;
    private List<Item> allIngredients; // Your dataset
    @Override
    public void onItemClick(Item item) {
        if(ingredientNameEditText != null) {
            ingredientNameEditText.setText(item.getName());}
    }
    public PantryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pantry, container, false);
        pantryIngredientItemList = generateRecipeItems();
        addIngredientButton = rootView.findViewById(R.id.addIngredientButton);
        ingredientNameEditText = rootView.findViewById(R.id.ingredientNameEditText);
        ingredientAmtEditText = rootView.findViewById(R.id.ingredientAmtEditText);
        ingredientUnitEditText = rootView.findViewById(R.id.ingredientUnitEditText);
        suggestionsRecyclerView = rootView.findViewById(R.id.suggestionsRecyclerView);
        actionButtonsContainer = rootView.findViewById(R.id.actionButtonsContainer);
        cancelButton = rootView.findViewById(R.id.cancelButton);
        confirmButton = rootView.findViewById(R.id.confirmButton);

        allIngredients = getAllIngredients(); // Initialize your dataset here
        suggestionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Initialize the SuggestionsAdapter with an OnItemClickListener
        suggestionsAdapter = new SuggestionsAdapter(new SuggestionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                // Update the EditText with the selected suggestion
                ingredientNameEditText.setText(item.getName());
                ingredientUnitEditText.setText(item.getUnit());
                suggestionsRecyclerView.setVisibility(View.GONE); // Hide the RecyclerView
                ingredientNameEditText.clearFocus();
                suggestionsAdapter.updateData(new ArrayList<>());}
        });
        suggestionsRecyclerView.setAdapter(suggestionsAdapter);
        ingredientNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // EditText has gained focus, make RecyclerView visible
                    suggestionsRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    // EditText has lost focus, make RecyclerView gone or invisible as needed
                    suggestionsRecyclerView.setVisibility(View.GONE);}
            }});
        ingredientNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());}
            @Override
            public void afterTextChanged(Editable s) {}
        });


        addIngredientButton.setOnClickListener(v -> toggleViewVisibility(true));
        cancelButton.setOnClickListener(v -> toggleViewVisibility(false));
        confirmButton.setOnClickListener(v -> {
            // Implement the creation logic here
            toggleViewVisibility(false);
            // Use the text from ingredientEditText for whatever creation logic you need
        });

        ImageButton pantryCameraButton = rootView.findViewById(R.id.pantry_camera);
        pantryCameraButton.setOnClickListener(v -> {
            // Start the CameraRecognition activity
            Intent intent = new Intent(getActivity(), CameraRecognition.class);
            startActivity(intent);});

        pantryRecyclerView = rootView.findViewById(R.id.pantryRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        pantryRecyclerView.setLayoutManager(layoutManager);
        recipeAdapterPantry = new recipeAdapterPantry(pantryIngredientItemList);
        pantryRecyclerView.setAdapter(recipeAdapterPantry);
        return rootView;
    }


    private void filter(String text) {
        List<Item> startsWithList = new ArrayList<>();
        List<Item> containsList = new ArrayList<>();
        for (Item item : allIngredients) { // Assuming allIngredients is a List<Item>
            if (item.getName().toLowerCase().startsWith(text.toLowerCase())) {
                startsWithList.add(item);
            } else if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                containsList.add(item);}
        }
        List<Item> filteredList = new ArrayList<>(startsWithList);
        filteredList.addAll(containsList);
        suggestionsAdapter.updateData(filteredList);
    }
    private List<Item> getAllIngredients() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Milk", "ML"));
        items.add(new Item("Flour", "g"));
        items.add(new Item("Butter", "g"));
        items.add(new Item("Sugar", "Tbsp"));
        items.add(new Item("Olive Oil", "Tbsp"));
        items.add(new Item("Baking Powder", "tsp"));
        items.add(new Item("Chicken Breast", "pc"));
        items.add(new Item("Beef", "kg"));
        items.add(new Item("Eggs", "pc"));
        items.add(new Item("Carrots", "g"));
        items.add(new Item("Honey", "Tbsp"));
        items.add(new Item("Water", "L"));
        items.add(new Item("Vanilla Extract", "tsp"));
        items.add(new Item("Cinnamon", "tsp"));
        items.add(new Item("Cheese", "g"));
        items.add(new Item("Yogurt", "ML"));
        items.add(new Item("Tomatoes", "pc"));
        items.add(new Item("Potatoes", "kg"));
        items.add(new Item("Lemon Juice", "Tbsp"));
        items.add(new Item("Salt", "Pinch"));
        items.add(new Item("Pepper", "Dash"));
        items.add(new Item("Ground Beef", "lb"));
        items.add(new Item("Rice", "g"));
        items.add(new Item("Pasta", "g"));
        items.add(new Item("Bread Crumbs", "Cup"));
        items.add(new Item("Apple Cider Vinegar", "ML"));
        items.add(new Item("Maple Syrup", "Tbsp"));
        items.add(new Item("Almonds", "g"));
        items.add(new Item("Raisins", "Cup"));
        items.add(new Item("Chicken Stock", "ML"));
        items.add(new Item("Coconut Milk", "ML"));
        items.add(new Item("Soy Sauce", "Tbsp"));
        items.add(new Item("Wine", "ML"));
        items.add(new Item("Beer", "ML"));
        items.add(new Item("Chocolate Chips", "Cup"));
        items.add(new Item("Oregano", "Tsp"));
        items.add(new Item("Basil", "Tsp"));
        items.add(new Item("Parsley", "Tbsp"));
        items.add(new Item("Rosemary", "Tsp"));
        items.add(new Item("Thyme", "Tsp"));
        items.add(new Item("Mozzarella Cheese", "g"));
        items.add(new Item("Parmesan Cheese", "g"));
        items.add(new Item("Heavy Cream", "ML"));
        items.add(new Item("Sour Cream", "ML"));
        items.add(new Item("Whole Chicken", "pc"));
        items.add(new Item("Pork Loin", "kg"));
        items.add(new Item("Salmon Fillet", "pc"));
        items.add(new Item("Trout Fillet", "pc"));
        items.add(new Item("Cod Fillet", "pc"));
        items.add(new Item("Tuna Steak", "pc"));
        items.add(new Item("Vegetable Broth", "ML"));
        items.add(new Item("Beef Broth", "ML"));
        items.add(new Item("Orange Juice", "ML"));
        items.add(new Item("Grape Juice", "ML"));
        items.add(new Item("Balsamic Vinegar", "Tbsp"));
        items.add(new Item("Red Wine Vinegar", "Tbsp"));
        items.add(new Item("White Wine Vinegar", "Tbsp"));
        items.add(new Item("Sesame Oil", "Tbsp"));
        items.add(new Item("Sunflower Oil", "ML"));
        items.add(new Item("Canola Oil", "ML"));
        items.add(new Item("Peanut Oil", "ML"));
        items.add(new Item("Walnuts", "g"));
        items.add(new Item("Pecans", "g"));
        items.add(new Item("Cashews", "g"));
        items.add(new Item("Pistachios", "g"));
        items.add(new Item("Macadamia Nuts", "g"));
        items.add(new Item("Quinoa", "g"));
        items.add(new Item("Lentils", "Cup"));
        items.add(new Item("Chickpeas", "Cup"));
        items.add(new Item("Black Beans", "Cup"));
        items.add(new Item("Kidney Beans", "Cup"));
        return items;
    }
    private void toggleViewVisibility(boolean showInput) {
        addIngredientButton.setVisibility(showInput ? View.GONE : View.VISIBLE);
        ingredientNameEditText.setVisibility(showInput ? View.VISIBLE : View.GONE);
        ingredientAmtEditText.setVisibility(showInput ? View.VISIBLE : View.GONE);
        ingredientUnitEditText.setVisibility(showInput ? View.VISIBLE : View.GONE);
        actionButtonsContainer.setVisibility(showInput ? View.VISIBLE : View.GONE);
    }

    private List<pantryIngredientItem> generateRecipeItems() {
        List<pantryIngredientItem> pantryIngredientItems = new ArrayList<>();
        //ADD YOUR ITEMS TO THE RECYCLER VIEW HERE!!!
        pantryIngredientItems.add(new pantryIngredientItem("Egg", "1", "Unit", false));
        pantryIngredientItems.add(new pantryIngredientItem("Onion", "1", "Unit", false));
        pantryIngredientItems.add(new pantryIngredientItem("Cabbage", "2", "Unit", false));
        pantryIngredientItems.add(new pantryIngredientItem("Soya Sauce", "50", "ML", false));
        pantryIngredientItems.add(new pantryIngredientItem("Milk", "200", "ML", false));
        pantryIngredientItems.add(new pantryIngredientItem("Cucumber", "50", "Unit", false));

        return pantryIngredientItems;
    }
}





//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.fragment_featured, container, false);
//
//        featuredRecipeItemList = generateRecipeItems();
//
//        featuredRecyclerView = rootView.findViewById(R.id.featuredRecyclerView);
//        layoutManager = new FlexboxLayoutManager(getActivity());
//        layoutManager.setFlexWrap(FlexWrap.WRAP);
//        layoutManager.setFlexDirection(FlexDirection.ROW);
//        layoutManager.setAlignItems(AlignItems.STRETCH);
//        featuredRecyclerView.setLayoutManager(layoutManager);
//        recipeAdapterFeatured = new recipeAdapterFeatured(featuredRecipeItemList);
//        featuredRecyclerView.setAdapter(recipeAdapterFeatured);
//
////        featuredRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
////        recipeAdapter = new recipeAdapter(featuredRecipeItemList);
////        featuredRecyclerView.setAdapter(recipeAdapter);
//
//        return rootView;
//
//    }
//
//}