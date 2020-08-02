package com.miesaf.mobeve;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private SessionHandler session;

    private ProgressDialog pDialog;
    private ArrayList<Events> mEventList;

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_EVN_ID = "evn_id";
    private static final String KEY_EVN_RLVL = "rule_level";
    private String result_url = "https://dimensikini.xyz/api/mobeve/wshop_rules_engine.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionHandler(getApplicationContext());
        if(!session.isLoggedIn()){
            loadLogin();
        }

        setContentView(R.layout.activity_result);

        Button btnEvnDetail = findViewById(R.id.btnEvnDetail);
        Button btnEvnList = findViewById(R.id.btnEvnList);

        Intent intent = getIntent();
        String evn_id = intent.getExtras().getString("EXTRA_ID");
        String evn_type = intent.getExtras().getString("EXTRA_TYPE");
        int rule_level = intent.getExtras().getInt("EXTRA_RULE_LVL");

        retrieveResult(evn_id, evn_type, rule_level);

        btnEvnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getApplicationContext(), ListEventActivity.class);
                //startActivity(i);
                finish();
            }
        });

        btnEvnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListEventActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void retrieveResult(String evn_id, String evn_type, int rule_level) {
        displayLoader("Retrieving event result details.. Please wait...");

        if(evn_type.equals("Workshop")){
            result_url = "https://dimensikini.xyz/api/mobeve/wshop_rules_engine.php";
            rule_level = 3;
        }else if(evn_type.equals("Talk")){
            result_url = "https://dimensikini.xyz/api/mobeve/talk_rules_engine.php";
            rule_level = 3;
        }else if(evn_type.equals("Sport")){
            result_url = "https://dimensikini.xyz/api/mobeve/sport_rules_engine.php";
            rule_level = 3;
        }else if(evn_type.equals("Volunteerism")){
            result_url = "https://dimensikini.xyz/api/mobeve/volun_rules_engine.php";
            rule_level = 3;
        }

        JSONObject request = new JSONObject();
        JSONObject response = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_EVN_ID, evn_id);
            request.put(KEY_EVN_RLVL, rule_level);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, result_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if response returned successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                JSONObject resp = response.getJSONObject("data");
                                JSONObject result_data = resp.getJSONObject("result");

                                TextView tvEvn_result = findViewById(R.id.evnResult);
                                
                                String disp_res = "";

                                //JSONObject object = new JSONObject ();
                                JSONArray keys = result_data.names ();

                                for (int i = 0; i < keys.length (); ++i) {
                                    String key = keys.getString (i); // Here's your key
                                    String value = result_data.getString (key); // Here's your value

                                    disp_res += "<b>" + key + "</b>" + "<small><ol>";

                                    JSONArray values = new JSONArray(value);

                                    for (int j=0; j<values.length(); j++)
                                    {
                                        String vars = values.getString(j);
                                        disp_res += "<li>" + vars + "</li>";
                                    }

                                    disp_res += "</ol></small><br/>";
                                }
                                
                                //tvEvn_result.setText(resp.getString("result"));
                                //tvEvn_result.setText(disp_res);
                                tvEvn_result.setText(HtmlCompat.fromHtml(disp_res, 0));
                                tvEvn_result.setLineSpacing(50, 0);

                                //Toast.makeText(getApplicationContext(), disp_res, Toast.LENGTH_LONG).show();
                                //Toast.makeText(getApplicationContext(), "Display event details successful!", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(getApplicationContext(), response.getString(KEY_MESSAGE), Toast.LENGTH_LONG).show();

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

    /**
     * Display Progress bar while Logging in
     */

    private void displayLoader(String message) {
        pDialog = new ProgressDialog(ResultActivity.this);
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
}
