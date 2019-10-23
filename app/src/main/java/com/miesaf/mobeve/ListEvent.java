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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListEvent extends AppCompatActivity {
    private SessionHandler session;

    private Events event;
    private String parser;
    String arr;

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_LEADER_ID = "leader_id";
    private static final String KEY_EVENT_LIST = "data";
    private ProgressDialog pDialog;
    private String post_url = "https://miesaf.ml/dev/member/list_event.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        
        session = new SessionHandler(getApplicationContext());
        JSONObject request = new JSONObject();

        try {
            //Populate the request parameters
            request.put(KEY_LEADER_ID, session.getUserDetails().getUsername());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, post_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();

                        try {
                            String data = response.getString(KEY_EVENT_LIST);
                            JSONArray parser = new JSONArray(data);
                            System.out.println("berjaya ke: " + parser);
                            System.out.println("row pertama " + parser.getString(0));
                            setData(parser.getString(0));
                            JSONObject cuba = new JSONObject(parser.getString(0));
                            //System.out.println("Yuyi " + cuba.getString("evn_name"));

                            if (response.getInt(KEY_STATUS) == 0) {
                                //System.out.println(arr);
                                //Display error message whenever an error occurs
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





        //event = new Events();
        TextView testText = findViewById(R.id.testText);

        String bacas = eventList();

        testText.setText("Lists of events: "+ parser);

        Button mainBtn = findViewById(R.id.mainBtn);

        //Launch Create Event Activity screen when Create Event Button is clicked
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListEvent.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void setData(String data){
        this.arr = data;
    }

    private String eventList() {
        displayLoader();
        session = new SessionHandler(getApplicationContext());
        JSONObject request = new JSONObject();

        try {
            //Populate the request parameters
            request.put(KEY_LEADER_ID, session.getUserDetails().getUsername());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, post_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();

                        try {
                            //Check if user got logged in successfully
                            //System.out.println(response.getString(KEY_EVENT_LIST));
                            String data = response.getString(KEY_EVENT_LIST);
                            JSONArray parser = new JSONArray(data);
                            System.out.println(parser);
                            System.out.println("hehe " + parser.getString(0));
                            setData(parser.getString(0));
                            JSONObject cuba = new JSONObject(parser.getString(0));
                            System.out.println("Yuyi " + cuba.getString("evn_name"));
                            //Object obj=arr;
                            //arr = arr.replace("},{", "}, {");
                            //arr = arr.replaceAll("\\[", "").replaceAll("\\]","");
                            //arr = arr.replaceAll("\\{", " ");
                            //String[] parts = arr.split(", ");
                            //System.out.println(parts[0]);

                            //String[] strArray = new String[] {arr};
                            //System.out.println(strArray[0]);

                            /*
                            HashMap<String, String> ev = new HashMap<String, String>();
                            ArrayList<JSONObject> arrays = new ArrayList<JSONObject>();
                            JSONArray jsonArr = new JSONArray(arr);

                            System.out.println(jsonArr.length());

                            for (int i = 0; i < jsonArr.length(); i++)
                            {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);

                                arrays.add(jsonObj);

                            }
                            */

                            if (response.getInt(KEY_STATUS) == 0) {
                                //System.out.println(arr);
                                //Display error message whenever an error occurs
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

    /**
     * Display Progress bar while Logging in
     */

    private void displayLoader() {
        pDialog = new ProgressDialog(ListEvent.this);
        pDialog.setMessage("Getting event list.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
}
