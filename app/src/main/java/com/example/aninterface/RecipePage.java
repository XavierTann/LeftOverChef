package com.example.aninterface;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class RecipePage extends AppCompatActivity {
    private String generatedString;
    private String foodName;
    private static String phoneNumber;
    private static String difficulty;
    private static String cookingTime;
    private static String ingredients;
    private static String cuisine;
    private static String dietaryRequirements;
    private static String specialRequirements;
    private static String ingredientsFromCamera;
    private static String ingredientsFromPantry;


    // FUNCTION TO SEARCH IMAGE FROM INTERNET USING GOOGLE CUSTOM SEARCH API //
    private void searchImage(String query, String apiKey, ImageView imageViewToUpdate, Button buttonViewToUpdate, ImageView favouriteButton) {
        String cx = "d6406233621ac4b28"; // Replace with your Custom Search Engine ID
        String url = "https://www.googleapis.com/customsearch/v1?q=" + query + "&searchType=image&key=" + apiKey + "&cx=" + cx;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray items = response.getJSONArray("items");
                        if (items.length() > 0) {
                            JSONObject item = items.getJSONObject(0);
                            String imageUrl = item.getString("link");
                            Picasso.get().load(imageUrl).into(imageViewToUpdate);
                            seeMoreButton(buttonViewToUpdate, generatedString, imageUrl);
                            addToFavorites(favouriteButton, foodName, generatedString, imageUrl);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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

    public static String removeLeadingWhitespace(String input) {
        return input.replaceAll("^\\s+", "");
    }


    public void seeMoreButton(Button seeMoreButton, String generatedString, String imageUrl) {

        seeMoreButton.setOnClickListener(v -> {
            recipeDatabase(foodName, generatedString, imageUrl);
            allRecipesDatabase(foodName, generatedString, imageUrl);
            // Start a new activity to show full details of the recipe
            Intent intent = new Intent(RecipePage.this, SeeMorePage.class);
            // Pass any necessary data to the new activity
            intent.putExtra("recipeDescription", generatedString);
            intent.putExtra("recipeImage", imageUrl);
            startActivity(intent);
        });

    }
    // FUNCTION TO SEND RECIPES TO DATABASE //
    public static void recipeDatabase(String foodName, String generatedString, String imageUrl) {
        FirebaseDatabase database;
        DatabaseReference reference;
        String phoneNumber = RecipePage.phoneNumber;

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users").child(phoneNumber);

        // Create a reference to the "recipe" node under the phone number node
        DatabaseReference recipeRef = reference.child("recipe");

        // Create a reference to the new recipe node using the generated key
        String sanitizedFoodName = foodName.replaceAll("[^a-zA-Z0-9]", "_");
        DatabaseReference newRecipeRef = recipeRef.child(sanitizedFoodName);

        // Now you can set the value of the new recipe node
        // For example, you can set recipe details including name, instructions, ingredients, cooking time, and difficulty
        Recipe recipe = new Recipe(foodName, generatedString, RecipePage.ingredients, RecipePage.cookingTime, RecipePage.difficulty, imageUrl);
        newRecipeRef.setValue(recipe);
    }

    public static void allRecipesDatabase(String foodName, String generatedString, String imageUrl) {
        FirebaseDatabase database;

        database = FirebaseDatabase.getInstance();
        DatabaseReference recipeRef = database.getReference("all_recipes");

        // Create a reference to the new recipe node using the generated key
        String sanitizedFoodName = foodName.replaceAll("[^a-zA-Z0-9]", "_");
        DatabaseReference newRecipeRef = recipeRef.child(sanitizedFoodName);

        // Now you can set the value of the new recipe node
        // For example, you can set recipe details including name, instructions, ingredients, cooking time, and difficulty
        Recipe recipe = new Recipe(foodName, generatedString, RecipePage.ingredients, RecipePage.cookingTime, RecipePage.difficulty, imageUrl);
        newRecipeRef.setValue(recipe);
    }

    private void addToFavorites(ImageView favouriteButton, final String foodName, final String generatedString, String imageUrl) {
        favouriteButton.setOnClickListener(v -> {
            FirebaseDatabase database;
            DatabaseReference reference;
            String phoneNumber = RecipePage.phoneNumber;

            database = FirebaseDatabase.getInstance();
            reference = database.getReference("users").child(phoneNumber);

            // Create a reference to the "favourites" node under the phone number node
            DatabaseReference recipeRef = reference.child("favourites");

            // Create a reference to the new recipe node using the generated key
            String sanitizedFoodName = foodName.replaceAll("[^a-zA-Z0-9]", "_");
            DatabaseReference newRecipeRef = recipeRef.child(sanitizedFoodName);

            // Now you can set the value of the new recipe node
            // For example, you can set recipe details including name, instructions, ingredients, cooking time, and difficulty
            Recipe recipe = new Recipe(foodName, generatedString, RecipePage.ingredients, RecipePage.cookingTime, RecipePage.difficulty, imageUrl);
            newRecipeRef.setValue(recipe);
        });
    }

    private class NetworkTask extends AsyncTask<String, Void, String> {
        private TextView textViewToUpdate;
        private ImageView imageViewToUpdate;
        private Button buttonViewToUpdate;
        private ImageView favouriteButton;

        // Constructor to accept TextView and ImageView references
        public NetworkTask(TextView textViewToUpdate, ImageView imageViewToUpdate, Button buttonViewToUpdate, ImageView favouriteButton) {
            this.textViewToUpdate = textViewToUpdate;
            this.imageViewToUpdate = imageViewToUpdate;
            this.buttonViewToUpdate = buttonViewToUpdate;
            this.favouriteButton = favouriteButton;
        }


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
                generatedString = removeLeadingWhitespace(generatedString);
                textViewToUpdate.setText(generatedString);
                searchImage(foodName, apiKey, imageViewToUpdate, buttonViewToUpdate, favouriteButton); // Use foodName for the query

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

        String uncleanedIngredientsFromCamera = intent.getStringExtra("ingredientsFromCamera");
        if (uncleanedIngredientsFromCamera != null) {
            RecipePage.ingredientsFromCamera = uncleanedIngredientsFromCamera.replaceAll("[^a-zA-Z0-9]", " ");
        } else {
            RecipePage.ingredientsFromCamera = ""; // Or some default value
        }

        String uncleanedIngredientsFromPantry = intent.getStringExtra("ingredientsFromPantry");
        if (uncleanedIngredientsFromPantry != null) {
            RecipePage.ingredientsFromPantry = uncleanedIngredientsFromPantry.replaceAll("[^a-zA-Z0-9]", " ");
        } else {
            RecipePage.ingredientsFromPantry = ""; // Or some default value
        }

        RecipePage.phoneNumber = intent.getStringExtra("phoneNumber");

        // Generate text for recipe, and generate images for recipe
        String defaultprompt =  "I have leftover ingredients. " +
                "These are the ingredients:" + ingredients + ingredientsFromCamera + ingredientsFromPantry +
                "Give me a recipe for these ingredients" +
                "I want the recipe's difficulty level to be" + difficulty +
                "I want the recipe's cooking time to be " + cookingTime +
                "I want the cuisine of the dish to be " + cuisine +
                "My dietary requirement is " + dietaryRequirements +
                "My special request is " + specialRequirements +
                "You may assume I have basic cooking ingredients like salt." +
                "Limit to 50 words. " +
                "Include the name of the dish in the first few words." +
                "For the format, the first line should strictly only contain the name of the dish. DO NOT include any labels in the first line like 'Name:' " +
                "Third line should include ingredients, fourth line difficulty and fifth line cooking time. Sixth line onwards will have the instructions. Do not have any special characters in the first line" +
                "Give step by step clear instructions." +
                "Stick strictly to the ingredients provided above.";

        String prompt1 = defaultprompt + "Stick strictly to the ingredients provided above.";
        String prompt2 = defaultprompt + "You may use other ingredients other than the ingredients provided above, but let the main ingredients be the ones above";
        String prompt3 = defaultprompt + "Make this dish more creative!";


        // Find the TextViews and ImageView to update
        TextView textView1 = findViewById(R.id.text_recipePage_generatedRecipe1);
        ImageView imageView1 = findViewById(R.id.image_recipePage_searchedImage1);
        Button seeMore1 = findViewById(R.id.btn_recipePage_seeMore1);
        ImageView favourite1 = findViewById(R.id.image_recipePage_favourite1);
        TextView textView2 = findViewById(R.id.text_recipePage_generatedRecipe2);
        ImageView imageView2 = findViewById(R.id.image_recipePage_searchedImage2);
        Button seeMore2 = findViewById(R.id.btn_recipePage_seeMore2);
        ImageView favourite2 = findViewById(R.id.image_recipePage_favourite2);
        TextView textView3 = findViewById(R.id.text_recipePage_generatedRecipe3);
        ImageView imageView3 = findViewById(R.id.image_recipePage_searchedImage3);
        Button seeMore3 = findViewById(R.id.btn_recipePage_seeMore3);
        ImageView favourite3 = findViewById(R.id.image_recipePage_favourite3);

        // Create and execute NetworkTask instances with references to different TextViews and ImageViews
        NetworkTask task1 = new NetworkTask(textView1, imageView1, seeMore1, favourite1);
        task1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, prompt1);

        NetworkTask task2 = new NetworkTask(textView2, imageView2, seeMore2, favourite2);
        task2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, prompt2);

        NetworkTask task3 = new NetworkTask(textView3, imageView3, seeMore3, favourite3);
        task3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, prompt3);



        ImageButton backButton = findViewById(R.id.back_recipePage_ingredientpage);
        backButton.setOnClickListener(v -> {
            Intent intent2 = new Intent(this,IngredientPage.class);
            startActivity(intent2);
        });

    }




    }




