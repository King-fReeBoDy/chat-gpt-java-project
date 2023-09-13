package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import java.util.Objects;

public class register extends AppCompatActivity {
    TextView toLogin;
    EditText registerEmail,registerPassword,registerConfirmpassword;
    Button createAccount;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toLogin = findViewById(R.id.tologinbtn);
        registerEmail =findViewById(R.id.registeremail);
        registerPassword =findViewById(R.id.registerpassword);
        registerConfirmpassword= findViewById(R.id.registerconfirmpassword);
        createAccount = findViewById(R.id.registeraccount);


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(registerEmail.getText().toString().isEmpty() || registerPassword.getText().toString().isEmpty() || registerConfirmpassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplication(), "Input all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!Objects.equals(registerPassword.getText().toString(), registerConfirmpassword.getText().toString())){
                    Toast.makeText(getApplication(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }


                String url = "http://10.0.2.2:8080/auth/register";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                JSONObject postData = new JSONObject();
                try {
                    postData.put("email", registerEmail.getText().toString());
                    postData.put("password", registerPassword.getText().toString());
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
                                    Toast.makeText(getApplication(), "Account created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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
                registerEmail.setText("");
                registerPassword.setText("");
                registerConfirmpassword.setText("");
            }
        });


        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}