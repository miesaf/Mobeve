package com.miesaf.mobeve;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tapadoo.alerter.Alerter;

//import android.support.v7.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
    private SessionHandler session;
    private ProgressDialog pDialog;

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
        Button btnResetPwd = findViewById(R.id.btnResetPwd);
        Button btnDeleteAcc = findViewById(R.id.btnDeleteAcc);
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

        //Launch Change Password Activity screen
        btnResetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, ResetPasswordActivity.class);
                startActivity(i);
                finish();
            }
        });

        //Launch Delete Account Activity screen
        btnDeleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, DeleteAccountActivity.class);
                startActivity(i);
                finish();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLoader("Logging out.. Please wait...");
                session.logoutUser();
                Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                pDialog.dismiss();
            }
        });
    }

    private void displayLoader(String message) {
        pDialog = new ProgressDialog(DashboardActivity.this);
        pDialog.setMessage(message);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void showAlerter(View v) {
        Alerter.create(this)
                .setText("You have been logged out! Thank you for using Mobeve.")
                .setIcon(R.drawable.mobeve_logo_transparent)
                .enableIconPulse(false)
                .setBackgroundColorRes(R.color.colorPrimaryDark)
                .setDuration(2500)
                .enableSwipeToDismiss()
                .enableProgress(true)
                .setProgressColorRes(R.color.colorAccent)
                .show();
    }
}