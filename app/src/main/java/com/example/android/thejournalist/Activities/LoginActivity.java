package com.example.android.thejournalist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.thejournalist.R;

public class LoginActivity extends AppCompatActivity {
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final TextInputEditText emailEditText=findViewById(R.id.et_email);

/*        SharedPreferences sharedPreferences=this.getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();*/


        Button loginButton=findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=emailEditText.getText().toString();

                if(isEmailValid(email)){
                    /*editor.putString("email",email);
                    editor.commit();*/
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
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
