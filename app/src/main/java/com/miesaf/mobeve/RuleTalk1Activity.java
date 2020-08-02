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

public class RuleTalk1Activity extends AppCompatActivity {

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
    private static final String KEY_EMPTY = "";

    private ProgressDialog pDialog;
    private String rule_url = "https://dimensikini.xyz/api/mobeve/talk_rules_engine.php";
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionHandler(getApplicationContext());
        final User user = session.getUserDetails();

        if(!session.isLoggedIn()){
            loadLogin();
        }

        setContentView(R.layout.activity_rule_talk1);

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

                ProcessRule(evn_id, Answ1, Answ2, Answ3, Answ4, Answ5, Answ6, Answ7, Answ8);

            }
        });

        //Launch Dashboard Activity screen when Cancel Button is clicked
        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RuleTalk1Activity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void ProcessRule(final String res_evn_id, String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8) {
        displayLoader();
        JSONObject request = new JSONObject();
        JSONObject response = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_EVN_ID, res_evn_id);
            request.put(KEY_RULE_LEVEL, 1);
            request.put(KEY_ANS_1, a1);
            request.put(KEY_ANS_2, a2);
            request.put(KEY_ANS_3, a3);
            request.put(KEY_ANS_4, a4);
            request.put(KEY_ANS_5, a5);
            request.put(KEY_ANS_6, a6);
            request.put(KEY_ANS_7, a7);
            request.put(KEY_ANS_8, a8);

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

                                Intent ruleIntent = new Intent(RuleTalk1Activity.this, RuleTalk2Activity.class);
                                ruleIntent.putExtra("EXTRA_ID", res_evn_id);
                                startActivity(ruleIntent);

                                //Intent i = new Intent(getApplicationContext(), ListEventActivity.class);
                                //startActivity(i);
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
