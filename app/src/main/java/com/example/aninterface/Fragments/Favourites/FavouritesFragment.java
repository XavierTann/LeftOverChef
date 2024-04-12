package com.example.aninterface.Fragments.Favourites;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.List;
public class FavouritesFragment extends Fragment {
    private List<favouritesRecipeItem> favouritesRecipeItemsList;
    private RecyclerView favouritesRecyclerView;
    private com.example.aninterface.Fragments.Favourites.recipeAdapterFavourites recipeAdapterFavourites;
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
        recipeAdapterFavourites = new recipeAdapterFavourites(getContext(),favouritesRecipeItemsList);
        favouritesRecyclerView.setAdapter(recipeAdapterFavourites);

        return rootView;

    }

    private List<favouritesRecipeItem> generateRecipeItems() {
        List<favouritesRecipeItem> favouritesRecipeItems = new ArrayList<>();
        //ADD YOUR ITEMS TO THE RECYCLER VIEW HERE!!!
        generateRecipeItemsFromFirebase(favouritesRecipeItems);
        return favouritesRecipeItems;
    }



    private void generateRecipeItemsFromFirebase(List<favouritesRecipeItem> favouritesRecipeItems) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String phoneNumber = SharedPreferencesUtil.getPhoneNumber(getActivity().getApplicationContext());


        // Assuming phoneNumber is the variable containing the specific phone number
        databaseReference.child("users").child(phoneNumber).child("favourites").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int recipeCount = 0; // Counter to limit to three recipes

                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    if (recipeCount >= 50) {
                        break; // Break out of the loop after adding (How Many?) items
                    }
                    String firebaseRecipeName = recipeSnapshot.getKey();

                    // Convert underscores to spaces if necessary
                    String displayRecipeName = firebaseRecipeName.replace("_", " ");
//
                    String cookingInstructions = recipeSnapshot.child("cookingInstructions").getValue(String.class);
                    String imageURL = recipeSnapshot.child("imageUrl").getValue(String.class);

                    // Create a new favouritesRecipeItem and add it to the list
                    favouritesRecipeItems.add(new favouritesRecipeItem(imageURL, displayRecipeName, cookingInstructions));
                    recipeCount++;
                }
                // Notify adapter that data set has changed
                recipeAdapterFavourites.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur
            }
        });
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
