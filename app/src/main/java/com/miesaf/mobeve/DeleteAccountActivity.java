package com.miesaf.mobeve;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class DeleteAccountActivity extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_USER_ID = "user_uname";
    private static final String KEY_USER_PW = "user_pass";
    private static final String KEY_EMPTY = "";

    private EditText etDelAcc;
    private String DelAcc;

    private ProgressDialog pDialog;
    private String update_url = "https://miesaf.ml/dev/mobeve/delete_account.php";
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        final User user = session.getUserDetails();

        if(!session.isLoggedIn()){
            loadLogin();
        }
        setContentView(R.layout.activity_delete_account);

        etDelAcc = findViewById(R.id.etDelAcc);

        Button BtnDelAcc = findViewById(R.id.btnDelAcc);
        Button BtnCancel = findViewById(R.id.btnCancel);

        BtnDelAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                DelAcc = etDelAcc.getText().toString().trim();
                if (validateInputs()) {
                    fnDelAcc(user.getUsername(), DelAcc);
                }
            }
        });

        //Launch previous Event Detail Activity screen when Cancel Button is clicked
        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDashboard();
                finish();
            }
        });
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(DeleteAccountActivity.this);
        pDialog.setMessage("Changing password.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void fnDelAcc(String username, String DelAcc) {
        displayLoader();
        JSONObject request = new JSONObject();
        JSONObject response = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USER_ID, username);
            request.put(KEY_USER_PW, DelAcc);

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
                                Toast.makeText(getApplicationContext(), "Account deletion successful!", Toast.LENGTH_LONG).show();

                                session.logoutUser();
                                Intent i = new Intent(DeleteAccountActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                                pDialog.dismiss();

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
        if (KEY_EMPTY.equals(DelAcc)) {
            etDelAcc.setError("Password cannot be empty");
            etDelAcc.requestFocus();
            return false;
        }

        return true;
    }

    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(i);
        finish();
    }

    private void loadLogin() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();
    }
}
