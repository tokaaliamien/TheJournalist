package com.example.android.thejournalist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.thejournalist.R;
import com.example.android.thejournalist.Utilites.Helper;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        /*final TextInputEditText emailEditText=findViewById(R.id.et_email);

         *//*        SharedPreferences sharedPreferences=this.getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();*//*


        Button loginButton=findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=emailEditText.getText().toString();

                if(isEmailValid(email)){
                    *//*editor.putString("email",email);
                    editor.commit();*//*
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    finish();
                }else{
                    emailEditText.setError("Wrong email format");
                }
            }
        });*/

//        List<AuthUI.IdpConfig> providers = Arrays.asList(
//                new AuthUI.IdpConfig.EmailBuilder().build(),
//                new AuthUI.IdpConfig.GoogleBuilder().build());

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.ic_action_fav)
                        .build()
                , RC_SIGN_IN);
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Helper.displayToast(LoginActivity.this, "Welcome " + user.getDisplayName());
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            } else {
                Helper.displayToast(LoginActivity.this, "Login Failed");
            }
        }
    }
}
