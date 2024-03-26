package com.example.aninterface;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aninterface.R;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomePage extends AppCompatActivity {

//    private static final String API_KEY = "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"; // Replace with your OpenAI API key
//
//    public static String generateResponse(String prompt) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        MediaType mediaType = MediaType.parse("application/json");
//
//        String requestBody = "{\"model\":\"gpt-3.5-turbo-instruct\", \"prompt\":\"" + prompt + "\", \"max_tokens\":300}";
//        Request request = new Request.Builder()
//                .url("https://api.openai.com/v1/completions")
//                .post(RequestBody.create(mediaType, requestBody))
//                .addHeader("Authorization", "Bearer " + API_KEY)
//                .addHeader("Content-Type", "application/json")
//                .build();
//
//        Response response = client.newCall(request).execute();
//        return response.body().string();
//    }
//
//    public static String extractGeneratedText(String jsonResponse) throws IOException {
//        // Use Jackson ObjectMapper to parse JSON
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
//
//        // Extract text from the "choices" array
//        JsonNode choicesArray = jsonNode.path("choices");
//        String generatedText = choicesArray.isArray() && choicesArray.size() > 0
//                ? choicesArray.get(0).path("text").asText()
//                : "No text found in the response";
//
//        return generatedText;
//    }
//
//    // HTTP Requests should be done asynchronously, which means it should be done on a separate thread, not the main thread.
//    private class NetworkTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... params) {
//            String prompt = params[0];
//            try {
//                return generateResponse(prompt);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        @Override
//        protected void onPostExecute(String response) {
//            try {
//                String generatedText = extractGeneratedText(response);
//                System.out.println(generatedText);
//                TextView textView = findViewById(R.id.txt_homePage_generatedRecipes);
//                textView.setText(generatedText);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.homePage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Execute network task in the background

//        String prompt = "I have leftover ingredients. The ingredients consist of 2 apples, 2 onions and garlics, and the basic cooking ingredients. " +
//                "Give me two recipes for these ingredients. Give me clear step by step instructions on how to prepare the dish.";
//        new NetworkTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, prompt);
    }
}
