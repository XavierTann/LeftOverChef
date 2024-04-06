package com.example.aninterface.Fragments.Featured;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aninterface.Fragments.Featured.featuredRecipeItem;
import com.example.aninterface.Fragments.Featured.recipeAdapterFeatured;
import com.example.aninterface.HelperClass.SharedPreferencesUtil;
import com.example.aninterface.R;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FeaturedFragment extends Fragment {

    private List<featuredRecipeItem> featuredRecipeItemList;
    private RecyclerView featuredRecyclerView;
    private com.example.aninterface.Fragments.Featured.recipeAdapterFeatured recipeAdapterFeatured;
    private FlexboxLayoutManager layoutManager;
    private Set<String> userFavorites = new HashSet<>();


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
        recipeAdapterFeatured = new recipeAdapterFeatured(getContext(), featuredRecipeItemList);
        featuredRecyclerView.setAdapter(recipeAdapterFeatured);


//        featuredRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recipeAdapter = new recipeAdapter(featuredRecipeItemList);
//        featuredRecyclerView.setAdapter(recipeAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchUserFavourites(); // Refresh favorites when fragment resumes
    }

    public void fetchUserFavourites() {
        String phoneNumber = SharedPreferencesUtil.getPhoneNumber(getContext());
        DatabaseReference favouritesRef = FirebaseDatabase.getInstance().getReference("users").child(phoneNumber).child("favourites");
        favouritesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userFavorites.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    userFavorites.add(snapshot.getKey());
                }
                if (recipeAdapterFeatured != null) {
                    recipeAdapterFeatured.setUserFavourites(userFavorites);
                    recipeAdapterFeatured.notifyDataSetChanged(); // Refresh the adapter
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private List<featuredRecipeItem> generateRecipeItems(){
        List<featuredRecipeItem> featuredRecipeItems = new ArrayList<>();
        //ADD YOUR ITEMS TO THE RECYCLER VIEW HERE!!!
        generateRecipeItemsFromFirebase(featuredRecipeItems);
        return featuredRecipeItems;
    }

    private void generateRecipeItemsFromFirebase(List<featuredRecipeItem> featuredRecipeItems) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String phoneNumber = SharedPreferencesUtil.getPhoneNumber(getActivity().getApplicationContext());
        databaseReference.child("all_recipes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int recipeCount = 0; // Counter to limit to three recipes
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    if (recipeCount >= 10) {
                        break;} // Break out of the loop after adding (How Many?) items
                    String recipeName = recipeSnapshot.child("recipeName").getValue(String.class);
                    String cookingInstructions = recipeSnapshot.child("cookingInstructions").getValue(String.class);
                    String imageURL = recipeSnapshot.child("imageUrl").getValue(String.class);
                    // Create a new favouritesRecipeItem and add it to the list
                    featuredRecipeItems.add(new featuredRecipeItem(imageURL, recipeName, cookingInstructions));
                    recipeCount++;}
                // Notify adapter that data set has changed
                recipeAdapterFeatured.notifyDataSetChanged();}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur
            }
        });
    }

}