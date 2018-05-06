package com.example.toothless.monlis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    Button login, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        login = (Button) findViewById(R.id.btn_login);
        signup = (Button) findViewById(R.id.btn_signup);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent forget = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(forget);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent forget = new Intent(StartActivity.this, RegisterActivity.class);
                startActivity(forget);
                finish();
            }
        });
    }
}
