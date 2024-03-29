package com.example.aninterface;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aninterface.HelperClass.Recipe;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class RecipePage extends AppCompatActivity {
    private String generatedString;
    private String foodName;
    private static String imageUrl;
    private static String phoneNumber;
    private static String difficulty;
    private static String cookingTime;
    private static String ingredients;
    private static String cuisine;
    private static String dietaryRequirements;
    private static String specialRequirements;

    private List<Recipe> generatedRecipes = new ArrayList<>();

    // FUNCTION TO SEARCH IMAGE FROM INTERNET USING GOOGLE CUSTOM SEARCH API //
    private void searchImage(String query, String apiKey) {
        String cx = "d6406233621ac4b28"; // Replace with your Custom Search Engine ID
        String url = "https://www.googleapis.com/customsearch/v1?q=" + query + "&searchType=image&key=" + apiKey + "&cx=" + cx;
        ImageView imageView = findViewById(R.id.image_recipePage_searchedImage);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray items = response.getJSONArray("items");
                            if (items.length() > 0) {
                                JSONObject item = items.getJSONObject(0);
                                RecipePage.imageUrl = item.getString("link");
                                Picasso.get().load(imageUrl).into(imageView);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(request);
    }

    private static final String API_KEY = "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"; // Replace with your OpenAI API key


    // FUNCTION TO GENERATE TEXT USING OPENAI //
    public static String generateResponse(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        String requestBody = "{\"model\":\"gpt-3.5-turbo-instruct\", \"prompt\":\"" + prompt + "\", \"max_tokens\":300}";
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .post(RequestBody.create(mediaType, requestBody))
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        okhttp3.Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String extractGeneratedText(String jsonResponse) throws IOException {
        // Use Jackson ObjectMapper to parse JSON
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        // Extract text from the "choices" array
        JsonNode choicesArray = jsonNode.path("choices");
        String generatedText = choicesArray.isArray() && choicesArray.size() > 0
                ? choicesArray.get(0).path("text").asText()
                : "No text found in the response";

        return generatedText;
    }

    // HTTP Requests should be done asynchronously, which means it should be done on a separate thread, not the main thread.
    private class NetworkTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String prompt = params[0];
            try {
                return generateResponse(prompt);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        @Override
        protected void onPostExecute(String response) {
            try {
                String apiKey = "AIzaSyDbrOusjueLtlTNgSHOJcachiTW606mXsg";
                generatedString = extractGeneratedText(response);
                foodName = getFirstWords(generatedString);
                System.out.println(foodName);
                TextView textView = findViewById(R.id.text_recipePage_generatedRecipe1);
                textView.setText(generatedString);
                searchImage(foodName, apiKey); // Use foodName for the query
                recipeDatabase(foodName, generatedString);
                seeMoreButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String getFirstWords(String input) {
        if (input == null) {
            return ""; // Return an empty string if input is null
        }

        StringBuilder sb = new StringBuilder();
        boolean nonWhiteSpaceEncountered = false;

        for (char c : input.toCharArray()) {
            if (!nonWhiteSpaceEncountered && Character.isWhitespace(c)) {
                continue; // Skip leading whitespace characters until a non-whitespace character is encountered
            }
            nonWhiteSpaceEncountered = true; // Mark that a non-whitespace character has been encountered
            if (c == '\n') {
                break; // Exit loop when a newline character is encountered
            }
            sb.append(c); // Append characters until a newline character is encountered
        }

        return sb.toString().trim(); // Trim any leading/trailing spaces and return the result
    }



    public void seeMoreButton() {
        TextView textView = findViewById(R.id.text_recipePage_generatedRecipe1);
        Button seeMoreButton = findViewById(R.id.btn_recipePage_seeMore1);

        seeMoreButton.setOnClickListener(v -> {
            // Start a new activity to show full details of the recipe
            Intent intent = new Intent(RecipePage.this, IndividualRecipe.class);
            // Pass any necessary data to the new activity
            intent.putExtra("recipeDescription", generatedString);
            intent.putExtra("recipeImage", imageUrl);
            startActivity(intent);
        });

    }



    // FUNCTION TO SEND RECIPES TO DATABASE //
    public static void recipeDatabase(String foodName, String generatedString) {
        FirebaseDatabase database;
        DatabaseReference reference;
        String phoneNumber = RecipePage.phoneNumber;

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users").child(phoneNumber);

        // Create a reference to the "recipe" node under the phone number node
        DatabaseReference recipeRef = reference.child("recipe");

        // Create a reference to the new recipe node using the generated key
        DatabaseReference newRecipeRef = recipeRef.child(foodName);

        // Now you can set the value of the new recipe node
        // For example, you can set recipe details including name, instructions, ingredients, cooking time, and difficulty
        Recipe recipe = new Recipe(foodName, generatedString, RecipePage.ingredients, RecipePage.cookingTime, RecipePage.difficulty, RecipePage.imageUrl);
        newRecipeRef.setValue(recipe);
    }

    // Function to generate and display recipes
    private void generateRecipes(String prompt) {
        // Generate multiple recipes based on the prompt
        for (int i = 0; i < 3; i++) {
            // Generate recipe asynchronously
            new NetworkTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, prompt);
        }
    }

    // Function to display generated recipes
    private void displayRecipes() {
        // Iterate through the list of generated recipes and display them in your user interface
        for (Recipe recipe : generatedRecipes) {
            // Display each recipe in your UI (e.g., in a RecyclerView, ListView, etc.)
            // You can create a new activity or fragment to display detailed recipe information
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_page);

        // Retrieve filter criteria passed from IngredientPage
        Intent intent = getIntent();
        RecipePage.cookingTime = intent.getStringExtra("cookingTime");
        RecipePage.difficulty = intent.getStringExtra("difficulty");
        RecipePage.ingredients = intent.getStringExtra("ingredients");
        RecipePage.cuisine = intent.getStringExtra("cuisine");
        RecipePage.dietaryRequirements = intent.getStringExtra("dietaryRequirements");
        RecipePage.specialRequirements = intent.getStringExtra("specialRequirements");

        RecipePage.phoneNumber = intent.getStringExtra("phoneNumber");

        // Generate text for recipe, and generate images for recipe
        String prompt =  "I have leftover ingredients. " +
                "These are the ingredients:" + ingredients +
                "Give me a recipe for these ingredients" +
                "I want the recipe's difficulty level to be" + difficulty +
                "I want the recipe's cooking time to be " + cookingTime +
                "I want the cuisine of the dish to be " + cuisine +
                "My dietary requirement is " + dietaryRequirements +
                "My special request is " + specialRequirements +
                "You may assume I have basic cooking ingredients like salt." +
                "Limit to 50 words. " +
                "Include the name of the dish in the first few words." +
                "The first line should only contain the name of the dish. Everything after should come in the next few lines. Do not have any special characters in the first line" +
                "Ingredients, difficulty, and cooking time should be included below the recipe name." +
                "Give step by step clear instructions. Try to give add ingredients that are not specified into the recipe.";
        new NetworkTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, prompt);




    }


}

