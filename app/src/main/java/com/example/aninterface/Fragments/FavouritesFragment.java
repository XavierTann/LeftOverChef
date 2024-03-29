package com.example.aninterface.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aninterface.R;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;
public class FavouritesFragment extends Fragment {
    private List<favouritesRecipeItem> favouritesRecipeItemsList;
    private RecyclerView favouritesRecyclerView;
    private recipeAdapterFavourites recipeAdapterFavourites;

    private FlexboxLayoutManager layoutManager;

    public FavouritesFragment() {
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);

        favouritesRecipeItemsList = generateRecipeItems();
        favouritesRecyclerView = rootView.findViewById(R.id.favouritesRecyclerView);
        layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        favouritesRecyclerView.setLayoutManager(layoutManager);
        recipeAdapterFavourites = new recipeAdapterFavourites(favouritesRecipeItemsList);
        favouritesRecyclerView.setAdapter(recipeAdapterFavourites);

        return rootView;

    }

    private List<favouritesRecipeItem> generateRecipeItems() {
        List<favouritesRecipeItem> favouritesRecipeItems = new ArrayList<>();
        //ADD YOUR ITEMS TO THE RECYCLER VIEW HERE!!!
        favouritesRecipeItems.add(new favouritesRecipeItem(R.drawable.leftoverchef, "Apple Pie", "Made from apples etc..."));
        favouritesRecipeItems.add(new favouritesRecipeItem(R.drawable.leftoverchef, "Blueberry Pie", "Made From Blueberry etc..."));
        favouritesRecipeItems.add(new favouritesRecipeItem(R.drawable.leftoverchef, "Raspberry Pie", "Made From Raspberry etc..."));
        favouritesRecipeItems.add(new favouritesRecipeItem(R.drawable.leftoverchef, "Strawberry Pie", "Made From Strawberry etc..."));
        favouritesRecipeItems.add(new favouritesRecipeItem(R.drawable.leftoverchef, "Test Pie 1", "Made From Test1 etc..."));
        favouritesRecipeItems.add(new favouritesRecipeItem(R.drawable.leftoverchef, "Test Pie 2", "Made From Test2 etc..."));
//        favouritesRecipeItems.add(new favouritesRecipeItem(R.drawable.leftoverchef, "Test Pie 3", "Made From Test3 etc..."));

        return favouritesRecipeItems;

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
