package com.miesaf.mobeve;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UpdateEventActivity extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_EVN_NAME = "evn_name";
    private static final String KEY_EVN_LEADER = "evn_leader";
    private static final String KEY_EVN_START = "evn_start";
    private static final String KEY_EVN_END = "evn_end";
    private static final String KEY_EVN_TYPE = "evn_type";
    private static final String KEY_EVENT_LIST = "data";
    private static final String KEY_EMPTY = "";

    private EditText etEventName;
    private EditText etStartDate;
    private EditText etEndDate;
    private EditText etEventType;

    private String EventName;
    private String StartDate;
    private String EndDate;
    private String EventType;

    String EventNameHolder, StartDateHolder, EndDateHolder, EventTypeHolder;

    private ProgressDialog pDialog;
    private String retrieve_url = "https://miesaf.ml/dev/mobeve/retrieve_event.php?evn_id=DFjnL";
    private String update_url = "https://miesaf.ml/dev/mobeve/update_event.php";
    private SessionHandler session;

    String arr;

    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        final User user = session.getUserDetails();

        if(!session.isLoggedIn()){
            loadLogin();
        }
        setContentView(R.layout.activity_update_event);

        etEventName = findViewById(R.id.etUpdateEventName);
        etStartDate = findViewById(R.id.etUpdateStartDate);
        etEndDate = findViewById(R.id.etUpdateEndDate);
        etEventType = findViewById(R.id.etUpdateEventType);

        Button EvnUpdate = findViewById(R.id.btnEvnUpdate);
        Button BtnCancel = findViewById(R.id.btnCancel);

        //EvnRetrieve();

        // Receive Student ID, Name , Phone Number , Class Send by previous ShowSingleRecordActivity.
        EventNameHolder = "Ikan Kembung";
        StartDateHolder = "2017-02-03 10:15:14";
        EndDateHolder = "2018-02-03 10:15:14";
        EventTypeHolder = "volunteer";

        // Setting Received Student Name, Phone Number, Class into EditText.
        etEventName.setText(EventNameHolder);
        etStartDate.setText(StartDateHolder);
        etEndDate.setText(EndDateHolder);
        etEventType.setText(EventTypeHolder);

        EvnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                /*
                EventName = etEventName.getText().toString().trim();
                StartDate = etStartDate.getText().toString().trim();
                EndDate = etEndDate.getText().toString().trim();
                EventType = etEventType.getText().toString().trim();
                if (validateInputs()) {
                    EvnUpdate(user.getUsername());
                }
                */

                // Getting data from EditText after button click.
                GetDataFromEditText();

                // Sending Student Name, Phone Number, Class to method to update on server.
                StudentRecordUpdate(EventNameHolder, StartDateHolder, EndDateHolder, EventTypeHolder);
            }
        });

        //Launch Dashboard Activity screen when Cancel Button is clicked
        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdateEventActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void setData(String data){
        this.arr = data;
    }

    // Method to get existing data from EditText.
    public void GetDataFromEditText(){

        EventNameHolder = etEventName.getText().toString();
        StartDateHolder = etStartDate.getText().toString();
        EndDateHolder = etEndDate.getText().toString();
        EventTypeHolder = etEventType.getText().toString();

    }

    // Method to Update Student Record.
    public void StudentRecordUpdate(final String Name, final String StartD, final String EndD, final String EType){

        class StudentRecordUpdateClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(UpdateEventActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();

                Toast.makeText(UpdateEventActivity.this,httpResponseMsg, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("evn_name",params[0]);

                hashMap.put("evn_start",params[1]);

                hashMap.put("evn_end",params[2]);

                hashMap.put("evn_type",params[3]);

                hashMap.put("evn_id","Z970o");

                finalResult = httpParse.postRequest(hashMap, update_url);

                return finalResult;
            }
        }

        StudentRecordUpdateClass studentRecordUpdateClass = new StudentRecordUpdateClass();

        studentRecordUpdateClass.execute(Name,StartD,EndD,EType);
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(UpdateEventActivity.this);
        pDialog.setMessage("Updating event.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(i);
        finish();
    }

    /*
    private String EvnRetrieve() {
        displayLoader();
        session = new SessionHandler(getApplicationContext());
        JSONObject request = new JSONObject();

        try {
            //Populate the request parameters
            request.put(KEY_EVN_LEADER, session.getUserDetails().getUsername());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, update_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();

                        try {
                            //Check if user got logged in successfully
                            //System.out.println(response.getString(KEY_EVENT_LIST));
                            String data = response.getString(KEY_EVENT_LIST);
                            JSONArray parser = new JSONArray(data);
                            System.out.println("Raw data: " + parser);
                            setData(parser.getString(0));
                            JSONObject cuba = new JSONObject(parser.getString(0));
                            System.out.println("Yuyi " + cuba.getString("evn_name"));

                            if (response.getInt(KEY_STATUS) == 0) {
                                //Display error message whenever an error occurs
                                //cubaList.setText("Response is: "+ response);
                                Toast.makeText(getApplicationContext(),
                                        "Berjaya baca list " + cuba.getString("evn_name"), Toast.LENGTH_LONG).show();
                                //session.loginUser("miesaf",response.getString(KEY_EVENT_LIST));
                                setData(cuba.getString("evn_name"));

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

        return this.arr;
    }
    */
    private void EvnUpdate(String username) {
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
                (Request.Method.POST, retrieve_url, request, new Response.Listener<JSONObject>() {
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

    private void loadLogin() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();

    }
}
