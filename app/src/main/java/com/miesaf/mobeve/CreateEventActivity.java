package com.miesaf.mobeve;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateEventActivity<adapter> extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_EVN_NAME = "evn_name";
    private static final String KEY_EVN_LEADER = "evn_leader";
    private static final String KEY_EVN_START = "evn_start";
    private static final String KEY_EVN_END = "evn_end";
    private static final String KEY_EVN_TYPE = "evn_type";
    private static final String KEY_EMPTY = "";

    private EditText etEventName;
    private EditText etStartDate;
    private EditText etEndDate;

    private String EventName;
    private String StartDate;
    private String EndDate;
    private String EventType;

    private ProgressDialog pDialog;
    private String register_url = "https://miesaf.ml/dev/mobeve/create_event.php";
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        final User user = session.getUserDetails();

        if(!session.isLoggedIn()){
            loadLogin();
        }
        setContentView(R.layout.activity_create_event);

        //get the spinner from the xml.
        final Spinner etEventType = findViewById(R.id.etCreateEventType);
        //create a list of items for the spinner.
        String[] items = new String[]{"workshops", "seminars", "talks", "sports", "volunteerism"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        etEventType.setAdapter(adapter);

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        etEventName = findViewById(R.id.etCreateEventName);
        etStartDate = findViewById(R.id.etCreateStartDate);
        etEndDate = findViewById(R.id.etCreateEndDate);

        Button EvnCreate = findViewById(R.id.btnEvnCreate);
        Button BtnCancel = findViewById(R.id.btnCancel);

        EvnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                EventName = etEventName.getText().toString().trim();
                StartDate = etStartDate.getText().toString().trim();
                EndDate = etEndDate.getText().toString().trim();
                EventType = etEventType.getSelectedItem().toString().trim();
                if (validateInputs()) {
                    EvnCreate(user.getUsername());
                }
            }
        });

        //Launch Dashboard Activity screen when Cancel Button is clicked
        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateEventActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    /**
     * Display Progress bar while registering
     */
    private void displayLoader() {
        pDialog = new ProgressDialog(CreateEventActivity.this);
        pDialog.setMessage("Creating event.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * Launch Dashboard Activity on Successful Sign Up
     */
    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(i);
        finish();
    }

    private void EvnCreate(String username) {
        displayLoader();
        JSONObject request = new JSONObject();
        JSONObject response = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_EVN_LEADER, username);
            request.put(KEY_EVN_NAME, EventName);
            request.put(KEY_EVN_START, StartDate);
            request.put(KEY_EVN_END, EndDate);
            request.put(KEY_EVN_TYPE, EventType);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, register_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Set the user session
                                //session.loginUser(username,fullName);
                                Toast.makeText(getApplicationContext(), "Event creation successful!",
                                        Toast.LENGTH_LONG).show();
                                loadDashboard();

                            }else if(response.getInt(KEY_STATUS) == 1){
                                //Display error message if username is already existing
                                etEventName.setError("Event name already taken!");
                                etEventName.requestFocus();

                            }else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);

        System.out.println(response);
    }

    /**
     * Validates inputs and shows error if any
     * @return
     */
    private boolean validateInputs() {
        if (KEY_EMPTY.equals(EventName)) {
            etEventName.setError("Event name cannot be empty");
            etEventName.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(StartDate)) {
            etStartDate.setError("Start date cannot be empty");
            etStartDate.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(EndDate)) {
            etEndDate.setError("End date cannot be empty");
            etEndDate.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Launch Login Activity if not logged in
     */
    private void loadLogin() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();

    }


}
