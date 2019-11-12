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

public class RetrieveEventActivity extends AppCompatActivity {
    private SessionHandler session;

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_EVN_ID = "evn_id";
    private static final String KEY_EVENT_LIST = "data";
    private ProgressDialog pDialog;
    private String post_url = "https://miesaf.ml/dev/mobeve/retrieve_event.php";

    String arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_event);

        session = new SessionHandler(getApplicationContext());
        JSONObject request = new JSONObject();

        final TextView cubaRetrieve = findViewById(R.id.cubaRetrieve);

        String bacas = eventRetrieve(cubaRetrieve);

        Button mainBtn = findViewById(R.id.mainBtn);

        //Launch Create Event Activity screen when Create Event Button is clicked
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RetrieveEventActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void setData(String data){
        this.arr = data;
    }

    private String eventRetrieve(final TextView cubaRetrieve) {
        displayLoader();
        JSONObject request = new JSONObject();

        try {
            //Populate the request parameters
            String eh = "Hs4DC";
            request.put(KEY_EVN_ID, eh);

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
                            String data = response.getString(KEY_EVENT_LIST);
                            JSONArray parser = new JSONArray(data);
                            System.out.println(" Respon dia: " + parser);
                            System.out.println("hehe " + parser.getString(0));
                            setData(parser.getString(0));
                            JSONObject cuba = new JSONObject(parser.getString(0));
                            System.out.println("Yuyi " + cuba.getString("evn_name"));

                            if (response.getInt(KEY_STATUS) == 0) {
                                //Display error message whenever an error occurs
                                //System.out.println("Event ID: " + response.getString("evn_id"));
                                //cubaRetrieve.setText(cuba.toString());
                                Toast.makeText(getApplicationContext(),
                                        "Berjaya baca event " + cuba.getString("evn_name"), Toast.LENGTH_LONG).show();

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
        pDialog = new ProgressDialog(RetrieveEventActivity.this);
        pDialog.setMessage("Getting event details.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
}
