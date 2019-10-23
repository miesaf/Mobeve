package com.miesaf.mobeve;

public class Events {

    private String events;

    public Events(String events, String status) {
        this.events = events;
        this.status = status;
    }

    private String status;

    public Events() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Events(String events) {
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

}
