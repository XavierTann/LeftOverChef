package com.example.aninterface.HelperClass;

public class FirebaseFunctions {
    public static String recipeNameToFirebaseKey(String recipeName) {
        return recipeName.replace(" ", "_");
    }

    public static String firebaseKeyToRecipeName(String firebaseKey) {
        return firebaseKey.replace("_", " ");
    }
}
