package com.miesaf.mobeve;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.miesaf.mobeve.ListEventActivity.EXTRA_ID;
import static com.miesaf.mobeve.ListEventActivity.EXTRA_NAME;
import static com.miesaf.mobeve.ListEventActivity.EXTRA_START;
import static com.miesaf.mobeve.ListEventActivity.EXTRA_END;
import static com.miesaf.mobeve.ListEventActivity.EXTRA_TYPE;
import static com.miesaf.mobeve.ListEventActivity.EXTRA_LEADER;

public class DetailEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        Intent intent = getIntent();
        String evn_id = intent.getStringExtra(EXTRA_ID);
        String evn_name = intent.getStringExtra(EXTRA_NAME);
        String evn_start = intent.getStringExtra(EXTRA_START);
        String evn_end = intent.getStringExtra(EXTRA_END);
        String evn_type = intent.getStringExtra(EXTRA_TYPE);
        String evn_leader = intent.getStringExtra(EXTRA_LEADER);

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
    }
}
