package com.example.a8477_daniellaqueroz_androidprototype;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a8477_daniellaqueroz_androidprototype.entities.Constants;

public class login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signinButton = findViewById(R.id.signinButton);



        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSigninClick(v);
            }
        });

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClick(v);
            }
        });
    }

    private void onLoginClick(View v) {


        Intent UserLogin = new Intent(login.this, MainActivity.class);

        startActivity(UserLogin);





        //ends the activity
        finish();
    }

    private void onSigninClick(View v) {
        Intent signinScreenIntent =new Intent(login.this, Signin.class );


        startActivity(signinScreenIntent);
    }


}