package com.miesaf.mobeve;

import java.util.Date;

public class User {
    private String username;
    private String fullName;
    private Date sessionExpiryDate;

    public void setUsername(String username) {
        this.username = username;
    }

    void setFullName(String fullName) {
        this.fullName = fullName;
    }

    void setSessionExpiryDate(Date sessionExpiryDate) {
        this.sessionExpiryDate = sessionExpiryDate;
    }

   public String getUsername() {
        return username;
    }

    String getFullName() {
        return fullName;
    }

    Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }
}