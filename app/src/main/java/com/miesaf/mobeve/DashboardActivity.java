package com.miesaf.mobeve;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        TextView welcomeText = findViewById(R.id.welcomeText);

        welcomeText.setText("Welcome "+ user.getFullName() + ", your session will expire on " + user.getSessionExpiryDate());

        Button evnCreateBtn = findViewById(R.id.btnEvnCreate);
        Button btnEvnList = findViewById(R.id.btnEvnList);
        Button btnEvnRetrieve = findViewById(R.id.btnEvnRetrieve);
        Button btnEvnUpdate = findViewById(R.id.btnEvnUpdate);
        Button btnEvnDelete = findViewById(R.id.btnEvnDelete);
        Button logoutBtn = findViewById(R.id.btnLogout);

        //Launch Create Event Activity screen when Create Event Button is clicked
        evnCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, CreateEventActivity.class);
                startActivity(i);
                finish();
            }
        });

        //Launch List Event Activity screen
        btnEvnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, ListEventActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnEvnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, RetrieveEventActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnEvnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, UpdateEventActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnEvnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, DeleteEventActivity.class);
                startActivity(i);
                finish();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });
    }
}