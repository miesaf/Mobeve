package com.miesaf.mobeve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {
    private Context mContext;
    private ArrayList<Events> mEventsList;

    public EventsAdapter(Context context, ArrayList<Events> eventsList) {
        mContext = context;
        mEventsList = eventsList;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_event_card, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Events currentEvent = mEventsList.get(position);

        //String evn_id = currentEvent.getEvn_id();
        String evn_name = currentEvent.getEvn_name();
        String evn_start = currentEvent.getEvn_start();
        String evn_end = currentEvent.getEvn_end();
        String evn_type = currentEvent.getEvn_type();
        String evn_leader = currentEvent.getEvn_leader();

        holder.mEvn_name.setText(evn_name);
        holder.mEvn_leader.setText(evn_leader);
        holder.mEvn_date.setText(evn_start + " - " + evn_end);
        holder.mEvn_type.setText(evn_type);
    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView mEvn_name;
        public TextView mEvn_leader;
        public TextView mEvn_date;
        public TextView mEvn_type;

        public EventViewHolder(View eventView) {
            super(eventView);
            mEvn_name = eventView.findViewById(R.id.evnName);
            mEvn_date = eventView.findViewById(R.id.evnDate);
            mEvn_type = eventView.findViewById(R.id.evnType);
            mEvn_leader = eventView.findViewById(R.id.evnLeader);
        }
    }
}