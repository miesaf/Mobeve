package com.miesaf.mobeve;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailEventActivity extends AppCompatActivity {

    private SessionHandler session;

    private ProgressDialog pDialog;
    private ArrayList<Events> mEventList;

    public static final String EXTRA_ID_EDIT = "evn_id";
    public static final String EXTRA_NAME_EDIT = "evn_name";
    public static final String EXTRA_START_EDIT = "evn_start";
    public static final String EXTRA_END_EDIT = "evn_end";
    public static final String EXTRA_TYPE_EDIT = "evn_type";
    public static final String EXTRA_ID_DELETE = "evn_id";

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_EVN_ID = "evn_id";
    private String retrieve_url = "https://miesaf.ml/dev/mobeve/retrieve_event.php";
    private String delete_url = "https://miesaf.ml/dev/mobeve/delete_event.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionHandler(getApplicationContext());
        if(!session.isLoggedIn()){
            loadLogin();
        }

        setContentView(R.layout.activity_detail_event);

        mEventList = new ArrayList<>();

        Intent intent = getIntent();

        String evn_id = intent.getStringExtra(ListEventActivity.EXTRA_ID);
        String evn_name = intent.getStringExtra(ListEventActivity.EXTRA_NAME);
        String evn_start = intent.getStringExtra(ListEventActivity.EXTRA_START);
        String evn_end = intent.getStringExtra(ListEventActivity.EXTRA_END);
        String evn_type = intent.getStringExtra(ListEventActivity.EXTRA_TYPE);
        String evn_leader = intent.getStringExtra(ListEventActivity.EXTRA_LEADER);

        TextView tvEvn_id = findViewById(R.id.evnId);
        TextView tvEvn_name = findViewById(R.id.evnName);
        TextView tvEvn_start = findViewById(R.id.evnStart);
        TextView tvEvn_end = findViewById(R.id.evnEnd);
        TextView tvEvn_type = findViewById(R.id.evnType);
        TextView tvEvn_leader = findViewById(R.id.evnLeader);

        tvEvn_id.setText(evn_id);
        tvEvn_name.setText(evn_name);
        tvEvn_start.setText(evn_start);
        tvEvn_end.setText(evn_end);
        tvEvn_type.setText(evn_type);
        tvEvn_leader.setText(evn_leader);

        mEventList.add(new Events(evn_id, evn_name, evn_leader, evn_start, evn_end, evn_type));

        Button btnEvnList = findViewById(R.id.btnEvnList);
        Button btnEvnUpdate = findViewById(R.id.btnEvnEdit);
        Button btnEvnDelete = findViewById(R.id.btnEvnDelete);

        //Launch Create Event Activity screen when Create Event Button is clicked
        btnEvnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(DetailEventActivity.this, DashboardActivity.class);
                //startActivity(i);
                finish();
            }
        });

        btnEvnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();

                String evn_id = intent.getStringExtra(ListEventActivity.EXTRA_ID);
                String evn_name = intent.getStringExtra(ListEventActivity.EXTRA_NAME);
                String evn_start = intent.getStringExtra(ListEventActivity.EXTRA_START);
                String evn_end = intent.getStringExtra(ListEventActivity.EXTRA_END);
                String evn_type = intent.getStringExtra(ListEventActivity.EXTRA_TYPE);

                editEvent(evn_id, evn_name, evn_start, evn_end, evn_type);
            }
        });

        btnEvnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();

                String evn_id_del = intent.getStringExtra(ListEventActivity.EXTRA_ID);

                deleteEvent(evn_id_del);
            }
        });
    }

    private void retrieveEvn(String evn_id) {
        displayLoader("Retrieving event details.. Please wait...");
        JSONObject request = new JSONObject();
        JSONObject response = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_EVN_ID, evn_id);

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
                                Toast.makeText(getApplicationContext(), "Event update successful!",
                                        Toast.LENGTH_LONG).show();

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

    private void deleteEvent(String evn_id_del) {
        displayLoader("Deleting event.. Please wait...");

        JSONObject request = new JSONObject();
        JSONObject response = new JSONObject();

        try {
            //Populate the request parameters
            request.put(KEY_EVN_ID, evn_id_del);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest JOrequest = new JsonObjectRequest
                (Request.Method.POST, delete_url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();

                try {
                    //Check if user got registered successfully
                    if (response.getInt(KEY_STATUS) == 0) {
                        Toast.makeText(getApplicationContext(), "Event deletion successful!",
                                Toast.LENGTH_LONG).show();
                        loadEventList();

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
                error.printStackTrace();
            }
        });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(JOrequest);

        System.out.println(response);
    }

    public void editEvent(String evn_id, String evn_name, String evn_start, String evn_end, String evn_type) {

        Intent intent = new Intent(this, UpdateEventActivity.class);
        intent.putExtra(EXTRA_ID_EDIT, evn_id);
        intent.putExtra(EXTRA_NAME_EDIT, evn_name);
        intent.putExtra(EXTRA_START_EDIT, evn_start);
        intent.putExtra(EXTRA_END_EDIT, evn_end);
        intent.putExtra(EXTRA_TYPE_EDIT, evn_type);
        startActivity(intent);

        finish();
    }

    /**
     * Display Progress bar while Logging in
     */

    private void displayLoader(String message) {
        pDialog = new ProgressDialog(DetailEventActivity.this);
        pDialog.setMessage(message);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void loadLogin() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void loadEventList() {
        Intent i = new Intent(getApplicationContext(), ListEventActivity.class);
        startActivity(i);
        finish();
    }
}
