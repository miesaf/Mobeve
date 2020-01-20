package com.miesaf.mobeve;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RuleWshop2Activity extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_EVN_ID = "evn_id";
    private static final String KEY_RULE_LEVEL = "rule_level";
    private static final String KEY_ANS_1 = "ans_1";
    private static final String KEY_ANS_2 = "ans_2";
    private static final String KEY_ANS_3 = "ans_3";
    private static final String KEY_ANS_4 = "ans_4";
    private static final String KEY_ANS_5 = "ans_5";
    private static final String KEY_ANS_6 = "ans_6";
    private static final String KEY_ANS_7 = "ans_7";
    private static final String KEY_ANS_8 = "ans_8";
    private static final String KEY_ANS_9 = "ans_9";
    private static final String KEY_ANS_10 = "ans_10";
    private static final String KEY_ANS_11 = "ans_11";
    private static final String KEY_ANS_12 = "ans_12";
    private static final String KEY_ANS_13 = "ans_13";
    private static final String KEY_ANS_14 = "ans_14";
    private static final String KEY_EMPTY = "";

    private ProgressDialog pDialog;
    private String rule_url = "https://miesaf.ml/dev/mobeve/wshop_rules_engine.php";
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        final User user = session.getUserDetails();

        if(!session.isLoggedIn()){
            loadLogin();
        }
        setContentView(R.layout.activity_rule_wshop2);

        Intent ruleIntent = getIntent();
        final String evn_id = ruleIntent.getExtras().getString("EXTRA_ID");

        final Spinner spQues1 = findViewById(R.id.spQues1);
        final Spinner spQues2 = findViewById(R.id.spQues2);
        final Spinner spQues3 = findViewById(R.id.spQues3);
        final Spinner spQues4 = findViewById(R.id.spQues4);
        final Spinner spQues5 = findViewById(R.id.spQues5);
        final Spinner spQues6 = findViewById(R.id.spQues6);
        final Spinner spQues7 = findViewById(R.id.spQues7);
        final Spinner spQues8 = findViewById(R.id.spQues8);
        final Spinner spQues9 = findViewById(R.id.spQues9);
        final Spinner spQues10 = findViewById(R.id.spQues10);
        final Spinner spQues11 = findViewById(R.id.spQues11);
        final Spinner spQues12 = findViewById(R.id.spQues12);
        final Spinner spQues13 = findViewById(R.id.spQues13);
        final Spinner spQues14 = findViewById(R.id.spQues14);

        Button EvnNext = findViewById(R.id.btnEvnNext);
        Button BtnCancel = findViewById(R.id.btnCancel);

        EvnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                String Answ1 = spQues1.getSelectedItem().toString().trim();
                String Answ2 = spQues2.getSelectedItem().toString().trim();
                String Answ3 = spQues3.getSelectedItem().toString().trim();
                String Answ4 = spQues4.getSelectedItem().toString().trim();
                String Answ5 = spQues5.getSelectedItem().toString().trim();
                String Answ6 = spQues6.getSelectedItem().toString().trim();
                String Answ7 = spQues7.getSelectedItem().toString().trim();
                String Answ8 = spQues8.getSelectedItem().toString().trim();
                String Answ9 = spQues9.getSelectedItem().toString().trim();
                String Answ10 = spQues10.getSelectedItem().toString().trim();
                String Answ11 = spQues11.getSelectedItem().toString().trim();
                String Answ12 = spQues12.getSelectedItem().toString().trim();
                String Answ13 = spQues13.getSelectedItem().toString().trim();
                String Answ14 = spQues14.getSelectedItem().toString().trim();

                ProcessRule(evn_id, Answ1, Answ2, Answ3, Answ4, Answ5, Answ6, Answ7, Answ8, Answ9, Answ10, Answ11, Answ12, Answ13, Answ14);
            }
        });

        //Launch Dashboard Activity screen when Cancel Button is clicked
        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RuleWshop2Activity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void ProcessRule(String evn_id, String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10, String a11, String a12, String a13, String a14) {
        displayLoader();
        JSONObject request = new JSONObject();
        JSONObject response = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_EVN_ID, evn_id);
            request.put(KEY_RULE_LEVEL, 2);
            request.put(KEY_ANS_1, a1);
            request.put(KEY_ANS_2, a2);
            request.put(KEY_ANS_3, a3);
            request.put(KEY_ANS_4, a4);
            request.put(KEY_ANS_5, a5);
            request.put(KEY_ANS_6, a6);
            request.put(KEY_ANS_7, a7);
            request.put(KEY_ANS_8, a8);
            request.put(KEY_ANS_9, a9);
            request.put(KEY_ANS_10, a10);
            request.put(KEY_ANS_11, a11);
            request.put(KEY_ANS_12, a12);
            request.put(KEY_ANS_13, a13);
            request.put(KEY_ANS_14, a14);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, rule_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                Toast.makeText(getApplicationContext(), "Rule inferences accepted!",
                                        Toast.LENGTH_LONG).show();

                                //Intent i = new Intent(getApplicationContext(), RuleWshop3Activity.class);
                                Intent i = new Intent(getApplicationContext(), ListEventActivity.class);
                                startActivity(i);
                                finish();
                                //loadDashboard();

                            }else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_LONG).show();

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
     * Display Progress bar while registering
     */
    private void displayLoader() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Processing.. Please wait...");
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

    /**
     * Launch Login Activity if not logged in
     */
    private void loadLogin() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();
    }
}
