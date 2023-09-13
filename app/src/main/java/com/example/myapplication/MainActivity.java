package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    TextView toCreateaccount;
    Button login;
    EditText signinEmail,signinPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toCreateaccount = findViewById(R.id.tocreateaccountbtn);
        login = findViewById(R.id.loginbtn);
        signinEmail = findViewById(R.id.signinemail);
        signinPassword = findViewById(R.id.signinpassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2:8080/auth/login";
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                JSONObject postData = new JSONObject();
                try {
                    postData.put("email", signinEmail.getText().toString());
                    postData.put("password", signinPassword.getText().toString());
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

                                String id = "";
                                String token = "";

                                try {
                                    id = response.getString("id");
                                    token = response.getString("token");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Toast.makeText(getApplication(), "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Homepage.class);
                                intent.putExtra("id", id);
                                intent.putExtra("token", token);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("ERROR", error.toString());
                                Toast.makeText(getApplication(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                queue.add(jsonObjectRequest);
                signinEmail.setText("");
                signinPassword.setText("");
            }
        });



        toCreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),register.class);
                startActivity(intent);
            }
        });
    }
}