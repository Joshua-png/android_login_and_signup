package com.example.refresher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {
    TextView received_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        received_username = findViewById(R.id.receivedUsername);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        received_username.setText(username);
    }
}