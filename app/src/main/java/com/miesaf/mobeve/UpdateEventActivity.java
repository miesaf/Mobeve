package com.miesaf.mobeve;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import static com.miesaf.mobeve.DetailEventActivity.EXTRA_ID_EDIT;

public class UpdateEventActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_EVN_ID = "evn_id";
    private static final String KEY_EVN_NAME = "evn_name";
    private static final String KEY_EVN_LEADER = "evn_leader";
    private static final String KEY_EVN_START = "evn_start";
    private static final String KEY_EVN_END = "evn_end";
    private static final String KEY_EVN_TYPE = "evn_type";
    private static final String KEY_EMPTY = "";

    private EditText etUpdateEventName;
    private EditText etUpdateStartDate;
    private EditText etUpdateEndDate;
    private EditText etUpdateEventType;

    Button btnEvnStart, btnEvnEnd;

    private TextView tvEvnId;

    private String EventName;
    private String StartDate;
    private String EndDate;
    private String EventType;

    private int mYear, mMonth, mDay;

    String EventNameHolder, StartDateHolder, EndDateHolder, EventTypeHolder;

    private ProgressDialog pDialog;
    private String update_url = "https://miesaf.ml/dev/mobeve/update_event.php";
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        final User user = session.getUserDetails();

        if(!session.isLoggedIn()){
            loadLogin();
        }

        setContentView(R.layout.activity_update_event);

        Intent intent = getIntent();

        String evn_id_edit = intent.getStringExtra(EXTRA_ID_EDIT);
        String evn_name_edit = intent.getStringExtra(DetailEventActivity.EXTRA_NAME_EDIT);
        String evn_start_edit = intent.getStringExtra(DetailEventActivity.EXTRA_START_EDIT);
        String evn_end_edit = intent.getStringExtra(DetailEventActivity.EXTRA_END_EDIT);
        String evn_type_edit = intent.getStringExtra(DetailEventActivity.EXTRA_TYPE_EDIT);

        etUpdateEventName = findViewById(R.id.etUpdateEventName);
        etUpdateStartDate = findViewById(R.id.etUpdateStartDate);
        etUpdateEndDate = findViewById(R.id.etUpdateEndDate);
        etUpdateEventType = findViewById(R.id.etUpdateEventType);

        tvEvnId = findViewById(R.id.tvEvnId);
        tvEvnId.setText("Event ID: " + evn_id_edit);

        btnEvnStart = findViewById(R.id.btnEvnStart);
        btnEvnEnd = findViewById(R.id.btnEvnEnd);
        Button EvnUpdate = findViewById(R.id.btnEvnUpdate);
        Button BtnCancel = findViewById(R.id.btnCancel);

        btnEvnStart.setOnClickListener(this);
        btnEvnEnd.setOnClickListener(this);

        // Receive Student ID, Name , Phone Number , Class Send by previous ShowSingleRecordActivity.
        EventNameHolder = evn_name_edit;
        StartDateHolder = evn_start_edit;
        EndDateHolder = evn_end_edit;
        EventTypeHolder = evn_type_edit;

        // Setting Received Student Name, Phone Number, Class into EditText.
        etUpdateEventName.setText(evn_name_edit);
        etUpdateStartDate.setText(evn_start_edit);
        etUpdateEndDate.setText(evn_end_edit);
        etUpdateEventType.setText(evn_type_edit);

        etUpdateEventType.setVisibility(View.GONE);

        EvnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                EventName = etUpdateEventName.getText().toString().trim();
                StartDate = etUpdateStartDate.getText().toString().trim();
                EndDate = etUpdateEndDate.getText().toString().trim();
                EventType = etUpdateEventType.getText().toString().trim();

                if (validateInputs()) {
                    Intent intent = getIntent();
                    EvnUpdate(user.getUsername(), intent.getStringExtra(EXTRA_ID_EDIT));
                }
            }
        });

        //Launch previous Event Detail Activity screen when Cancel Button is clicked
        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        if (v == btnEvnStart) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            etUpdateStartDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " 00:00:00");
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == btnEvnEnd) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            etUpdateEndDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " 00:00:00");
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(UpdateEventActivity.this);
        pDialog.setMessage("Updating event.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void EvnUpdate(String username, String evn_id_update) {
        displayLoader();
        JSONObject request = new JSONObject();
        JSONObject response = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_EVN_ID, evn_id_update);
            request.put(KEY_EVN_LEADER, username);
            request.put(KEY_EVN_NAME, EventName);
            request.put(KEY_EVN_START, StartDate);
            request.put(KEY_EVN_END, EndDate);
            request.put(KEY_EVN_TYPE, EventType);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, update_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Set the user session
                                Toast.makeText(getApplicationContext(), "Event update successful!",
                                        Toast.LENGTH_LONG).show();

                                loadList();
                                finish();

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
                                error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);

        System.out.println(response);
    }

    private boolean validateInputs() {
        if (KEY_EMPTY.equals(EventName)) {
            etUpdateEventName.setError("Event name cannot be empty");
            etUpdateEventName.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(StartDate)) {
            etUpdateStartDate.setError("Start date cannot be empty");
            etUpdateStartDate.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(EndDate)) {
            etUpdateEndDate.setError("End date cannot be empty");
            etUpdateEndDate.requestFocus();
            return false;
        }

        return true;
    }

    private void loadLogin() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void loadList() {
        Intent i = new Intent(getApplicationContext(), ListEventActivity.class);
        startActivity(i);
        finish();
    }
}
