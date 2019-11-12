package com.miesaf.mobeve;

public class Events {

    private String evn_id;
    private String evn_name;
    private String evn_leader;
    private String evn_start;
    private String evn_end;
    private String evn_type;

    public Events(String evn_id, String evn_name, String evn_leader, String evn_start, String evn_end, String evn_type) {
        this.evn_id = evn_id;
        this.evn_name = evn_name;
        this.evn_leader = evn_leader;
        this.evn_start = evn_start;
        this.evn_end = evn_end;
        this.evn_type = evn_type;
    }

    public String getEvn_id() {
        return evn_id;
    }

    public void setEvn_id(String evn_id) {
        this.evn_id = evn_id;
    }

    public String getEvn_name() {
        return evn_name;
    }

    public void setEvn_name(String evn_name) {
        this.evn_name = evn_name;
    }

    public String getEvn_leader() {
        return evn_leader;
    }

    public void setEvn_leader(String evn_leader) {
        this.evn_leader = evn_leader;
    }

    public String getEvn_start() {
        return evn_start;
    }

    public void setEvn_start(String evn_start) {
        this.evn_start = evn_start;
    }

    public String getEvn_end() {
        return evn_end;
    }

    public void setEvn_end(String evn_end) {
        this.evn_end = evn_end;
    }

    public String getEvn_type() {
        return evn_type;
    }

    public void setEvn_type(String evn_type) {
        this.evn_type = evn_type;
    }
}