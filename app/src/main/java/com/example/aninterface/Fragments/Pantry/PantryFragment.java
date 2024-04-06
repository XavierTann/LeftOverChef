package com.example.aninterface.Fragments.Pantry;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.aninterface.CameraPage;
import com.example.aninterface.Fragments.PantryIngredientSuggestions.Item;
import com.example.aninterface.Fragments.PantryIngredientSuggestions.SuggestionsAdapter;
import com.example.aninterface.Fragments.PantryIngredientSuggestions.UnitAdapter;
import com.example.aninterface.HelperClass.FirebaseFunctions;
import com.example.aninterface.HelperClass.SharedPreferencesUtil;
import com.example.aninterface.IngredientPage;
import com.example.aninterface.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PantryFragment extends Fragment implements SuggestionsAdapter.OnItemClickListener {
    private Button addIngredientButton;
    private ImageButton cancelButton, confirmButton;
    private EditText ingredientNameEditText, ingredientAmtEditText, ingredientUnitEditText;
    private LinearLayout actionButtonsContainer;
    private List<pantryIngredientItem> pantryIngredientItemList;
    private RecyclerView pantryRecyclerView, suggestionsRecyclerView, unitRecyclerView;
    private com.example.aninterface.Fragments.Pantry.recipeAdapterPantry recipeAdapterPantry;
    private LinearLayoutManager layoutManager;
    private SuggestionsAdapter suggestionsAdapter;
    private UnitAdapter unitAdapter;
    private List<Item> allIngredients; // Your dataset
    private static final List<String> VALID_UNITS = Arrays.asList("Piece(pc)", "Cup", "Tablespoon(Tbsp)", "Teaspoon(tsp)", "Millilitre(ML)", "Litre(L)", "Grams(g)", "Kilograms(kg)", "Ounce(oz)", "Pounds(lb)");
    public PantryFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pantry, container, false);

        // Generate Button
        Button generateButton = rootView.findViewById(R.id.btn_pantryPage_generateRecipe);
        generateButton.setOnClickListener(v -> generateRecipe());
        addIngredientButton = rootView.findViewById(R.id.addIngredientButton);
        ingredientNameEditText = rootView.findViewById(R.id.ingredientNameEditText);
        ingredientAmtEditText = rootView.findViewById(R.id.ingredientAmtEditText);
        ingredientUnitEditText = rootView.findViewById(R.id.ingredientUnitEditText);
        suggestionsRecyclerView = rootView.findViewById(R.id.suggestionsRecyclerView);
        unitRecyclerView = rootView.findViewById(R.id.UnitRecyclerView);
        actionButtonsContainer = rootView.findViewById(R.id.actionButtonsContainer);
        cancelButton = rootView.findViewById(R.id.cancelButton);
        confirmButton = rootView.findViewById(R.id.confirmButton);

        pantryIngredientItemList = new ArrayList<>();
        generatePantryItemsFromFirebase(pantryIngredientItemList);

        allIngredients = Item.getAllIngredients();
        suggestionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        suggestionsAdapter = new SuggestionsAdapter(new SuggestionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                // Update the EditText with the selected suggestion
                ingredientNameEditText.setText(item.getName());
                ingredientUnitEditText.setText(item.getUnit());
                suggestionsRecyclerView.setVisibility(View.GONE); // Hide the RecyclerView
                ingredientNameEditText.clearFocus();
                suggestionsAdapter.updateData(new ArrayList<>());
            }
        });
        suggestionsRecyclerView.setAdapter(suggestionsAdapter);
        ingredientNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // EditText has gained focus, make RecyclerView visible
                    suggestionsRecyclerView.setVisibility(View.VISIBLE);}
                else {
                    // EditText has lost focus, make RecyclerView gone or invisible as needed
                    suggestionsRecyclerView.setVisibility(View.GONE);}
            }
        });
        ingredientNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());}
            @Override
            public void afterTextChanged(Editable s) {}
        });

//        List<String> units = Arrays.asList("Piece(pc)", "Cup", "Tablespoon(Tbsp)", "Teaspoon(tsp)", "Millilitre(ML)", "Litre(L)", "Grams(g)", "Kilograms(kg)", "Ounce(oz)", "Pounds(lb)");
        unitAdapter = new UnitAdapter(VALID_UNITS, unit -> {
            ingredientUnitEditText.setText(unit);});
        ingredientUnitEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                unitRecyclerView.setVisibility(View.VISIBLE);
            } else {
                if (!isValidUnit(ingredientUnitEditText.getText().toString())) {
                    ingredientUnitEditText.setError("Invalid unit. Please enter a valid unit.");
                }
                unitRecyclerView.setVisibility(View.GONE);
            }
        });
        ingredientUnitEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                unitAdapter.filter(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        unitRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        unitRecyclerView.setAdapter(unitAdapter);

        addIngredientButton.setOnClickListener(v -> toggleViewVisibility(true));
        cancelButton.setOnClickListener(v -> toggleViewVisibility(false));
        confirmButton.setOnClickListener(v -> {
            // Implement the creation logic here
            toggleViewVisibility(false);
            String ingredientName = ingredientNameEditText.getText().toString().trim();
            String ingredientAmount = ingredientAmtEditText.getText().toString().trim();
            String ingredientUnit = ingredientUnitEditText.getText().toString().trim();
            if (!ingredientName.isEmpty() && !ingredientUnit.isEmpty() && !ingredientAmount.isEmpty()) {
                pantryIngredientItem newIngredient = new pantryIngredientItem(ingredientName,ingredientAmount,ingredientUnit,false);
                recipeAdapterPantry.addItem(newIngredient);
                String phoneNumber = SharedPreferencesUtil.getPhoneNumber(getActivity().getApplicationContext());
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(phoneNumber).child("pantry");
                String ingredientNameFB = FirebaseFunctions.recipeNameToFirebaseKey(ingredientName); // Create a unique ID for the ingredient
                databaseReference.child(ingredientNameFB).setValue(newIngredient)
                        .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Ingredient added successfully.", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to add ingredient.", Toast.LENGTH_SHORT).show());

                // Reset the input fields and hide them
                ingredientNameEditText.setText("");
                ingredientAmtEditText.setText("");
                ingredientUnitEditText.setText("");
                toggleViewVisibility(false);
            }
            else {
            Toast.makeText(getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();}
        });

        ImageButton pantryCameraButton = rootView.findViewById(R.id.pantry_camera);
        pantryCameraButton.setOnClickListener(v -> {
            // Start the CameraRecognition activity
            Intent intent = new Intent(getActivity(), CameraPage.class);
            startActivity(intent);});

        pantryRecyclerView = rootView.findViewById(R.id.pantryRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        pantryRecyclerView.setLayoutManager(layoutManager);
        recipeAdapterPantry = new recipeAdapterPantry(getContext(),pantryIngredientItemList);
        pantryRecyclerView.setAdapter(recipeAdapterPantry);
        return rootView;
    }
    private boolean isValidUnit(String input) {
        return VALID_UNITS.contains(input);
    }
    private void filter(String text) {
        List<Item> startsWithList = new ArrayList<>();
        List<Item> containsList = new ArrayList<>();
        for (Item item : allIngredients) { // Assuming allIngredients is a List<Item>
            if (item.getName().toLowerCase().startsWith(text.toLowerCase())) {
                startsWithList.add(item);
            } else if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                containsList.add(item);
            }
        }
        List<Item> filteredList = new ArrayList<>(startsWithList);
        filteredList.addAll(containsList);
        suggestionsAdapter.updateData(filteredList);
    }

    private void toggleViewVisibility(boolean showInput) {
        addIngredientButton.setVisibility(showInput ? View.GONE : View.VISIBLE);
        ingredientNameEditText.setVisibility(showInput ? View.VISIBLE : View.GONE);
        ingredientAmtEditText.setVisibility(showInput ? View.VISIBLE : View.GONE);
        ingredientUnitEditText.setVisibility(showInput ? View.VISIBLE : View.GONE);
        actionButtonsContainer.setVisibility(showInput ? View.VISIBLE : View.GONE);
    }

    private void generatePantryItemsFromFirebase(List<pantryIngredientItem> pantryIngredientItems) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String phoneNumber = SharedPreferencesUtil.getPhoneNumber(getActivity().getApplicationContext());

        // Assuming phoneNumber is the variable containing the specific phone number
        databaseReference.child("users").child(phoneNumber).child("pantry").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
//                    String firebaseRecipeName = recipeSnapshot.getKey();
                    String ingredientName = recipeSnapshot.child("ingredientName").getValue(String.class);
                    String ingredientAmount = recipeSnapshot.child("ingredientAmount").getValue(String.class);
                    String ingredientUnit = recipeSnapshot.child("ingredientUnit").getValue(String.class);
                    // Create a new favouritesRecipeItem and add it to the list
                    pantryIngredientItems.add(new pantryIngredientItem(ingredientName, ingredientAmount, ingredientUnit, false));
                }
                // Notify adapter that data set has changed
                recipeAdapterPantry.notifyDataSetChanged();}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur
            }
        });
    }
    @Override
    public void onItemClick(Item item) {
        if (ingredientNameEditText != null) {
            ingredientNameEditText.setText(item.getName());
        }
    }
    private void generateRecipe() {
        // Collect all ingredients and amounts from pantryIngredientItemList
        ArrayList<String> ingredientInfoList = new ArrayList<>();

        // Create a list to store the keys of ingredients to be removed from the database
        List<String> ingredientsToRemoveKeys = new ArrayList<>();

        for (pantryIngredientItem item : pantryIngredientItemList) {
            if (item.isSelected()) {
                // If checked, add the ingredient name and amount to the lists
                String ingredientInfo = item.getIngredientName() + ", " + item.getIngredientAmount() + " " + item.getIngredientUnit();
                ingredientInfoList.add(ingredientInfo);

                // Add the key of the ingredient to the list of keys to be removed
                String ingredientKey = FirebaseFunctions.recipeNameToFirebaseKey(item.getIngredientName());
                ingredientsToRemoveKeys.add(ingredientKey);
            }
        }

        // Remove selected ingredients from the database
        removeIngredientsFromDatabase(ingredientsToRemoveKeys);

        String recipeDetails = TextUtils.join("\n", ingredientInfoList);

        // Pass the data to RecipePage fragment or activity
        Intent intent = new Intent(getActivity(), IngredientPage.class);
        intent.putExtra("ingredientsFromPantry", recipeDetails);
        startActivity(intent);
    }

    private void removeIngredientsFromDatabase(List<String> ingredientKeys) {
        String phoneNumber = SharedPreferencesUtil.getPhoneNumber(getActivity().getApplicationContext());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(phoneNumber).child("pantry");

        // Iterate through the list of keys and remove corresponding ingredients from the database
        for (String key : ingredientKeys) {
            databaseReference.child(key).removeValue();
        }
    }

}


