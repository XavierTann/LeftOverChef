package com.example.aninterface.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.aninterface.CameraRecognition;
import com.example.aninterface.R;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;
public class PantryFragment extends Fragment {

    private List<pantryIngredientItem> pantryIngredientItemList;
    private RecyclerView pantryRecyclerView;
    private recipeAdapterPantry recipeAdapterPantry;

    private LinearLayoutManager layoutManager;

    public PantryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pantry, container, false);
        pantryIngredientItemList = generateRecipeItems();

        // Find the button by its ID
        ImageButton pantryCameraButton = rootView.findViewById(R.id.pantry_camera);
        // Set OnClickListener to the button
        pantryCameraButton.setOnClickListener(v -> {
            // Start the CameraRecognition activity
            Intent intent = new Intent(getActivity(), CameraRecognition.class);
            startActivity(intent);
        });

        pantryRecyclerView = rootView.findViewById(R.id.pantryRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        pantryRecyclerView.setLayoutManager(layoutManager);
        recipeAdapterPantry = new recipeAdapterPantry(pantryIngredientItemList);
        pantryRecyclerView.setAdapter(recipeAdapterPantry);

        return rootView;
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
//        pantryIngredientItems.add(new pantryIngredientItem(R.drawable.leftoverchef, "Test Pie 1", "Made From Test1 etc..."));
//        pantryIngredientItems.add(new pantryIngredientItem(R.drawable.leftoverchef, "Test Pie 2", "Made From Test2 etc..."));
//        pantryIngredientItems.add(new pantryIngredientItem(R.drawable.leftoverchef, "Test Pie 3", "Made From Test3 etc..."));

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