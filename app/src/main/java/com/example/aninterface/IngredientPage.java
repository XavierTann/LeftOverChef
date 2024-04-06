package com.example.aninterface;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aninterface.Fragments.PantryIngredientSuggestions.Item;
import com.example.aninterface.Fragments.PantryIngredientSuggestions.SuggestionsAdapter;
import com.example.aninterface.Fragments.PantryIngredientSuggestions.UnitAdapter;
import com.example.aninterface.Fragments.pantryIngredientItem;
import com.example.aninterface.Fragments.recipeAdapterPantry;
import com.example.aninterface.HelperClass.FirebaseFunctions;
import com.example.aninterface.HelperClass.SharedPreferencesUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IngredientPage extends AppCompatActivity implements SuggestionsAdapter.OnItemClickListener{
    private Button addIngredientButton;
    private ImageButton cancelButton, confirmButton;
    private EditText ingredientNameEditText, ingredientAmtEditText, ingredientUnitEditText;
    private LinearLayout actionButtonsContainer;
    private List<pantryIngredientItem> ingredientPageItemList;
    private RecyclerView pantryRecyclerView, suggestionsRecyclerView, unitRecyclerView;
    private com.example.aninterface.IngredientPage_Adapter ingredientPageAdapter;
    private LinearLayoutManager layoutManager;
    private SuggestionsAdapter suggestionsAdapter;
    private UnitAdapter unitAdapter;
    private List<Item> allIngredients; // Your dataset
    private static final List<String> VALID_UNITS = Arrays.asList("Piece(pc)", "Cup", "Tablespoon(Tbsp)", "Teaspoon(tsp)", "Millilitre(ML)", "Litre(L)", "Grams(g)", "Kilograms(kg)", "Ounce(oz)", "Pounds(lb)");

    public IngredientPage() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_page);

        fetchAndUpdateSpecialRequirements();
        fetchAndUpdateDietaryRequirements();

        String ingredientsFromCamera = getIntent().getStringExtra("ingredientString");
        String ingredientsFromPantry = getIntent().getStringExtra("ingredientsFromPantry");
        TextView txt_predictedIngredients = findViewById(R.id.txt_ingredientPage_predictedIngredients);
        if (ingredientsFromCamera != null) {
            txt_predictedIngredients.setText("The camera has scanned: " + ingredientsFromCamera + "Enter the other ingredients it is missing, as well as select your preferences below.");
        } else {
            txt_predictedIngredients.setText("No ingredients were scanned from the camera. Take a picture of your ingredients to get started, or just type in the search bar below");
        }
        if (ingredientsFromPantry != null) {
            txt_predictedIngredients.setText("The pantry has scanned: " + ingredientsFromPantry + "Enter the other ingredients it is missing, as well as select your preferences below.");
        } else {
            txt_predictedIngredients.setText("No ingredients were scanned from the pantry. Take a picture of your ingredients to get started, or just type in the search bar below");
        }

        addIngredientButton = findViewById(R.id.addIngredientButton);
        ingredientNameEditText = findViewById(R.id.ingredientNameEditText);
        ingredientAmtEditText = findViewById(R.id.ingredientAmtEditText);
        ingredientUnitEditText = findViewById(R.id.ingredientUnitEditText);
        suggestionsRecyclerView = findViewById(R.id.suggestionsRecyclerView);
        unitRecyclerView = findViewById(R.id.UnitRecyclerView);
        actionButtonsContainer = findViewById(R.id.actionButtonsContainer);
        cancelButton = findViewById(R.id.cancelButton);
        confirmButton = findViewById(R.id.confirmButton);

        allIngredients = Item.getAllIngredients();
        suggestionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        unitRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                ingredientPageAdapter.addItem(newIngredient);
                // Reset the input fields and hide them
                ingredientNameEditText.setText("");
                ingredientAmtEditText.setText("");
                ingredientUnitEditText.setText("");
                toggleViewVisibility(false);
            }
            else {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();}
        });


        pantryRecyclerView = findViewById(R.id.pantryRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        pantryRecyclerView.setLayoutManager(layoutManager);
//        ingredientPageAdapter = new IngredientPage_Adapter(this, ingredientPageItemList);
//        pantryRecyclerView.setAdapter(ingredientPageAdapter);



//        EditText searchIngredient = findViewById(R.id.edit_ingredientPage_searchIngredient);
        Button generateRecipes = findViewById(R.id.btn_ingredientPage_generateRecipes);
        Spinner spinnerDifficulty = findViewById(R.id.spinner_difficulty);
        Spinner spinnerCookingTime = findViewById(R.id.spinner_duration);
        Spinner spinnerCuisine = findViewById(R.id.spinner_ingredientPage_cuisine);
//        Spinner spinnerDietaryRequirements = findViewById(R.id.spinner_ingredientPage_dietaryRequirements);
        EditText editDietaryRequirements = findViewById(R.id.edit_ingredientPage_dietaryRequirements);
        EditText editSpecialRequirements =  findViewById(R.id.edit_ingredientPage_specialRequirements);


        generateRecipes.setOnClickListener(v -> {
            // Getting ingredients and filters
//            String ingredients = searchIngredient.getText().toString();
            String selectedDifficulty = spinnerDifficulty.getSelectedItem().toString();
            String cookingTime = spinnerCookingTime.getSelectedItem().toString();
            String cuisine = spinnerCuisine.getSelectedItem().toString();
//                    String dietaryRequirements = spinnerDietaryRequirements.getSelectedItem().toString();
            String dietaryRequirements = editDietaryRequirements.getText().toString();
            String specialRequirements = editSpecialRequirements.getText().toString();

            String phoneNumber = SharedPreferencesUtil.getPhoneNumber(this);
            Intent intent = new Intent(IngredientPage.this, RecipePage.class);
            intent.putExtra("phoneNumber", phoneNumber);
//            intent.putExtra("ingredients", ingredients);
            intent.putExtra("difficulty", selectedDifficulty);
            intent.putExtra("cookingTime", cookingTime);
            intent.putExtra("cuisine", cuisine);
            intent.putExtra("dietaryRequirements", dietaryRequirements);
            intent.putExtra("specialRequirements", specialRequirements);
            intent.putExtra("ingredientsFromCamera", ingredientsFromCamera);
            intent.putExtra("ingredientsFromPantry", ingredientsFromPantry);

            startActivity(intent);
        });
    }
    @Override
    public void onItemClick(Item item) {
        if (ingredientNameEditText != null) {
            ingredientNameEditText.setText(item.getName());
        }
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
    private void fetchAndUpdateSpecialRequirements() {
        String phoneNumber = SharedPreferencesUtil.getPhoneNumber(this);
        EditText editSpecialRequirementsSetText = findViewById(R.id.edit_ingredientPage_specialRequirements);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users")
                .child(phoneNumber)
                .child("otherSpecialRestrictions");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String specialRequirements = snapshot.getValue(String.class);
                    editSpecialRequirementsSetText.setText(specialRequirements);
                } else {;}
            }
            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                Log.e("Firebase Read", "Failed to read special requirements", error.toException());
            }
        });
    }
    private void fetchAndUpdateDietaryRequirements() {
        String phoneNumber = SharedPreferencesUtil.getPhoneNumber(this);
        EditText editDietaryRequirementsSetText = findViewById(R.id.edit_ingredientPage_dietaryRequirements);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users")
                .child(phoneNumber)
                .child("dietaryRestrictions");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dietaryRestictions = "| ";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String dietaryRequirement = snapshot.getValue(String.class);
                    dietaryRestictions = dietaryRestictions + dietaryRequirement + " | ";
                }
                editDietaryRequirementsSetText.setText(dietaryRestictions);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

