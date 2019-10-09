package com.miesaf.mobeve;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.miesaf.mobeve.beans.Event;

import java.util.List;

/**
 * Created by Abhi on 23 Sep 2017 023.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.CustomViewHolder> {
    private List<Event> events;

    public EventsAdapter(List<Event> employees) {
        this.events = events;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Event event = events.get(position);
        holder.evnId.setText(event.getEvnId());
        holder.evnName.setText(event.getEvnName());
        holder.evnLeader.setText(event.getEvnLeader());
        holder.evnStart.setText(event.getEvnStart());
        holder.evnEnd.setText(event.getEvnEnd());
        holder.evnType.setText(event.getEvnType());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView evnId, evnName, evnLeader, evnStart, evnEnd, evnType;

        public CustomViewHolder(View view) {
            super(view);
            evnId = (TextView) view.findViewById(R.id.evnId);
            evnName = (TextView) view.findViewById(R.id.evnName);
            evnLeader = (TextView) view.findViewById(R.id.evnLeader);
            evnStart = (TextView) view.findViewById(R.id.evnStart);
            evnEnd = (TextView) view.findViewById(R.id.evnEnd);
            evnType = (TextView) view.findViewById(R.id.evnType);
        }
    }
}