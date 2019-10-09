package com.miesaf.mobeve.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("evn_id")
    @Expose
    private String evnId;
    @SerializedName("evn_name")
    @Expose
    private String evnName;
    @SerializedName("evn_leader")
    @Expose
    private String evnLeader;
    @SerializedName("evn_start")
    @Expose
    private String evnStart;
    @SerializedName("evn_end")
    @Expose
    private String evnEnd;
    @SerializedName("evn_type")
    @Expose
    private String evnType;

    public String getEvnId() {
        return evnId;
    }

    public void setEvnId(String evnId) {
        this.evnId = evnId;
    }

    public String getEvnName() {
        return evnName;
    }

    public void setEvnName(String evnName) {
        this.evnName = evnName;
    }

    public String getEvnLeader() {
        return evnLeader;
    }

    public void setEvnLeader(String evnLeader) {
        this.evnLeader = evnLeader;
    }

    public String getEvnStart() {
        return evnStart;
    }

    public void setEvnStart(String evnStart) {
        this.evnStart = evnStart;
    }

    public String getEvnEnd() {
        return evnEnd;
    }

    public void setEvnEnd(String evnEnd) {
        this.evnEnd = evnEnd;
    }

    public String getEvnType() {
        return evnType;
    }

    public void setEvnType(String evnType) {
        this.evnType = evnType;
    }

}