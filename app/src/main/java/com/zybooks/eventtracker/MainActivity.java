package com.zybooks.eventtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    String userString, passString;
    Button submitButton, createButton;
    UserDb userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        submitButton = (Button) findViewById(R.id.submitButton);
        createButton = (Button) findViewById(R.id.createAccount);
        userDb = new UserDb(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userString = username.getText().toString();
                passString = password.getText().toString();

                Boolean checkUser = userDb.checkUser(userString, passString);
                if (checkUser == false) {
                    Toast.makeText(MainActivity.this, "Invalid credentials. Verify data or create account.", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), ListEvent.class);
                    startActivity(intent);
                }
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userString = username.getText().toString();
                passString = password.getText().toString();

                Boolean checkUser = userDb.checkUser(userString, passString);
                if (checkUser == false) {
                    Boolean insert = userDb.insertData(userString, passString);
                    if (insert == true) {
                        Intent intent = new Intent(getApplicationContext(), ListEvent.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "This username already exists.", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "User already exists. Sign in.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}