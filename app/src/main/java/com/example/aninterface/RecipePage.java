package com.example.aninterface;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecipePage extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        // Make an image search request
        String query = "sunny side up"; // Replace with your desired search query
        String apiKey = "AIzaSyDbrOusjueLtlTNgSHOJcachiTW606mXsg"; // Replace with your Google API key
        searchImage(query, apiKey);
    }

    // FUNCTION TO SEARCH IMAGE FROM INTERNET
    private void searchImage(String query, String apiKey) {
        String cx = "d6406233621ac4b28"; // Replace with your Custom Search Engine ID
        String url = "https://www.googleapis.com/customsearch/v1?q=" + query + "&searchType=image&key=" + apiKey + "&cx=" + cx;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray items = response.getJSONArray("items");
                            if (items.length() > 0) {
                                JSONObject item = items.getJSONObject(0);
                                String imageUrl = item.getString("link");
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
}

