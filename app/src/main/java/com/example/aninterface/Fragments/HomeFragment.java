package com.example.aninterface.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aninterface.Fragments.History.HistoryRecipeItem;
import com.example.aninterface.Fragments.History.RecipeAdapterHistory;
import com.example.aninterface.R;

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        historyRecyclerView.setLayoutManager(layoutManager);

        return rootView;
    }

    // Generate mock recipe items for demonstration
    private List<HistoryRecipeItem> generateRecipeItems() {
        List<HistoryRecipeItem> historyRecipeItems = new ArrayList<>();
        // Add your items to the RecyclerView here
        historyRecipeItems.add(new HistoryRecipeItem("recipe_thumbnail_1", "Recipe 1", "Description 1"));
        historyRecipeItems.add(new HistoryRecipeItem("recipe_thumbnail_2", "Recipe 2", "Description 2"));
        historyRecipeItems.add(new HistoryRecipeItem("recipe_thumbnail_3", "Recipe 3", "Description 3"));
        return historyRecipeItems;
    }
}
