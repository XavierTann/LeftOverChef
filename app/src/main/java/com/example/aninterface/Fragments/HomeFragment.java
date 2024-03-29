package com.example.aninterface.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aninterface.HelperClass.SharedPreferencesUtil;
import com.example.aninterface.IngredientPage;
import com.example.aninterface.Login;
import com.example.aninterface.R;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        Button generateRecipeButton = rootView.findViewById(R.id.btn_homePage_generateRecipe);


        generateRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IngredientPage.class);
                startActivity(intent);
            }
        });

        return rootView;


    }
}