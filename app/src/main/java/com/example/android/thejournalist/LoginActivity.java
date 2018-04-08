package com.example.android.thejournalist;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final TextInputEditText emailEditText=findViewById(R.id.et_email);


        Button loginButton=findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=emailEditText.getText().toString();

                if(isEmailValid(email)){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }else{
                    emailEditText.setError("Wrong email format");
                }
            }
        });
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
