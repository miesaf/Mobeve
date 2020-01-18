package com.miesaf.mobeve;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListEventActivity extends AppCompatActivity implements ListEventsAdapter.OnItemClickListener {
    private SessionHandler session;

    private RecyclerView mRecyclerView;
    private ListEventsAdapter mListEventsAdapter;
    private ArrayList<Events> mEventList;
    private RequestQueue mRequestQueue;

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_LEADER_ID = "evn_leader";
    private static final String KEY_EVENT_LIST = "data";
    private ProgressDialog pDialog;
    private String post_url = "https://miesaf.ml/dev/mobeve/list_event.php";
    private String delete_url = "https://miesaf.ml/dev/mobeve/delete_event.php";

    public static final String EXTRA_ID = "evn_id";
    public static final String EXTRA_NAME = "evn_name";
    public static final String EXTRA_START = "evn_start";
    public static final String EXTRA_END = "evn_end";
    public static final String EXTRA_TYPE = "evn_type";
    public static final String EXTRA_LEADER = "evn_leader";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionHandler(getApplicationContext());
        if(!session.isLoggedIn()){
            loadLogin();
        }

        setContentView(R.layout.activity_list_event);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mEventList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

        Button mainBtn = findViewById(R.id.mainBtn);

        //Launch Create Event Activity screen when Create Event Button is clicked
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListEventActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void parseJSON() {
        displayLoader("Getting event list.. Please wait...");
        session = new SessionHandler(getApplicationContext());
        JSONObject request = new JSONObject();

        try {
            //Populate the request parameters
            request.put(KEY_LEADER_ID, session.getUserDetails().getUsername());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest JOrequest = new JsonObjectRequest(Request.Method.POST, post_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();

                        try {
                            JSONArray jsonArray = response.getJSONArray(KEY_EVENT_LIST);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String evn_id = hit.getString("evn_id");
                                String evn_name = hit.getString("evn_name");
                                String evn_start = hit.getString("evn_start");
                                String evn_end = hit.getString("evn_end");
                                String evn_type = hit.getString("evn_type");
                                String evn_leader = hit.getString("evn_leader");

                                mEventList.add(new Events(evn_id, evn_name, evn_leader, evn_start, evn_end, evn_type));
                            }

                            mListEventsAdapter = new ListEventsAdapter(ListEventActivity.this, mEventList);
                            mRecyclerView.setAdapter(mListEventsAdapter);
                            mListEventsAdapter.setOnItemClickListener(ListEventActivity.this);

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

        mRequestQueue.add(JOrequest);
    }

    /**
     * Display Progress bar while Logging in
     */

    private void displayLoader(String message) {
        pDialog = new ProgressDialog(ListEventActivity.this);
        pDialog.setMessage(message);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    public void onItemClick(int position) {
        displayLoader("Getting event details.. Please wait...");

        Intent detailIntent = new Intent(this, DetailEventActivity.class);
        Events clickedItem = mEventList.get(position);

        detailIntent.putExtra(EXTRA_ID, clickedItem.getEvn_id());
        detailIntent.putExtra(EXTRA_NAME, clickedItem.getEvn_name());
        detailIntent.putExtra(EXTRA_START, clickedItem.getEvn_start());
        detailIntent.putExtra(EXTRA_END, clickedItem.getEvn_end());
        detailIntent.putExtra(EXTRA_TYPE, clickedItem.getEvn_type());
        detailIntent.putExtra(EXTRA_LEADER, clickedItem.getEvn_leader());

        startActivity(detailIntent);
        finish();
        pDialog.dismiss();
    }

    private void loadLogin() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();

    }
}