package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Homepage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<message> list;
    private Button sendPromptButton;
    private EditText messageBox;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        list = new ArrayList<>();
        recyclerView =  findViewById(R.id.recyclerView);
        sendPromptButton = findViewById(R.id.sendPrompt);
        messageBox = findViewById(R.id.messgebox);

        String userId = getIntent().getStringExtra("id");
        String userToken = getIntent().getStringExtra("token");

        getAllPrompts(userId);

        sendPromptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2:8080/chatgpt/prompt";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                JSONObject postData = new JSONObject();
                try {
                    postData.put("id", userId);
                    postData.put("message", messageBox.getText().toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        postData,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("RESPONSE", response.toString());
                                Toast.makeText(getApplicationContext(),  response.toString(), Toast.LENGTH_SHORT).show();
                                getAllPrompts(userId);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("ERROR", error.toString());
                                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                queue.add(jsonObjectRequest);
                messageBox.setText("");
            }
        });
    }

    private void setuprecyclerview(List<message> list) {

        recyclerAdapter adapter = new recyclerAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
    }

    private  void getAllPrompts (String userId) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2:8080/chatgpt/prompt/" + userId;

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("prompts");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                message messages = new message();
                                messages.setUserMessage(jsonObject.getString("userMessage"));
                                messages.setGptResponse(jsonObject.getString("gptResponse"));

                            }

                            setuprecyclerview(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.e("TAG", "Error: " + error.getMessage());
                    }
                });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}

