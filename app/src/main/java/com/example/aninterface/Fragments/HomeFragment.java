package com.example.aninterface.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aninterface.Fragments.History.HistoryRecipeItem;
import com.example.aninterface.Fragments.History.RecipeAdapterHistory;
import com.example.aninterface.HelperClass.SharedPreferencesUtil;
import com.example.aninterface.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<HistoryRecipeItem> historyRecipeItemList;
    private RecyclerView historyRecyclerView;
    private RecipeAdapterHistory recipeAdapterHistory;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        historyRecipeItemList = generateRecipeItems();

        historyRecyclerView = rootView.findViewById(R.id.historyRecyclerView);
        recipeAdapterHistory = new RecipeAdapterHistory(getContext(), historyRecipeItemList);
        historyRecyclerView.setAdapter(recipeAdapterHistory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        historyRecyclerView.setLayoutManager(layoutManager);

        return rootView;
    }

    // Generate mock recipe items for demonstration
    private List<HistoryRecipeItem> generateRecipeItems() {
        List<HistoryRecipeItem> historyRecipeItems = new ArrayList<>();
        // Add your items to the RecyclerView here
//        historyRecipeItems.add(new HistoryRecipeItem("recipe_thumbnail_1", "Recipe 1", "Description 1"));
//        historyRecipeItems.add(new HistoryRecipeItem("recipe_thumbnail_2", "Recipe 2", "Description 2"));
//        historyRecipeItems.add(new HistoryRecipeItem("recipe_thumbnail_3", "Recipe 3", "Description 3"));
        generateRecipeItemsFromFirebase(historyRecipeItems);
        return historyRecipeItems;
    }

    private void generateRecipeItemsFromFirebase(List<HistoryRecipeItem> historyRecipeItems) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String phoneNumber = SharedPreferencesUtil.getPhoneNumber(getActivity().getApplicationContext());
        databaseReference.child("users").child(phoneNumber).child("recipe").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int recipeCount = 0; // Counter to limit to three recipes
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    if (recipeCount >= 10) {
                        break; // Break out of the loop after adding (How Many?) items
                    }
                    String recipeName = recipeSnapshot.child("recipeName").getValue(String.class);
                    String cookingInstructions = recipeSnapshot.child("cookingInstructions").getValue(String.class);
                    String imageURL = recipeSnapshot.child("imageUrl").getValue(String.class);
                    // Create a new HistoryRecipeItem and add it to the list
                    historyRecipeItems.add(new HistoryRecipeItem(imageURL, recipeName, cookingInstructions));
                    recipeCount++;
                }
                // Notify adapter that data set has changed
                recipeAdapterHistory.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur
            }
        });
    }

}
