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

public class FeaturedFragment extends Fragment {

    private List<featuredRecipeItem> featuredRecipeItemList;
    private RecyclerView featuredRecyclerView;
    private recipeAdapter recipeAdapter;

    private FlexboxLayoutManager layoutManager;

    public FeaturedFragment(){
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_featured, container, false);

        featuredRecipeItemList = generateRecipeItems();

        featuredRecyclerView = rootView.findViewById(R.id.featuredRecyclerView);
        layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        featuredRecyclerView.setLayoutManager(layoutManager);
        recipeAdapter = new recipeAdapter(featuredRecipeItemList);
        featuredRecyclerView.setAdapter(recipeAdapter);

//        featuredRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recipeAdapter = new recipeAdapter(featuredRecipeItemList);
//        featuredRecyclerView.setAdapter(recipeAdapter);

        return rootView;

    }

    private List<featuredRecipeItem> generateRecipeItems(){
        List<featuredRecipeItem> featuredRecipeItems = new ArrayList<>();
        //ADD YOUR ITEMS TO THE RECYCLER VIEW HERE!!!
        featuredRecipeItems.add(new featuredRecipeItem(R.drawable.leftoverchef, "Apple Pie","Made from apples etc..."));
        featuredRecipeItems.add(new featuredRecipeItem(R.drawable.leftoverchef, "Blueberry Pie", "Made From Blueberry etc..."));
        featuredRecipeItems.add(new featuredRecipeItem(R.drawable.leftoverchef, "Raspberry Pie", "Made From Raspberry etc..."));
        featuredRecipeItems.add(new featuredRecipeItem(R.drawable.leftoverchef, "Strawberry Pie", "Made From Strawberry etc..."));

        return featuredRecipeItems;
    }

}