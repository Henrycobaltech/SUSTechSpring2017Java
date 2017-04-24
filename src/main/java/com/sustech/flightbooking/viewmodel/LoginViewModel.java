package com.sustech.flightbooking.viewmodel;

/**
 * Created by Henry on 4/24/2017.
 */
public class LoginViewModel {
    private String userName;
    private String password;
    private Boolean isPersistent;
    private String returnUri;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsPersistent() {
        return isPersistent;
    }

    public void setIsPersistent(Boolean persistent) {
        isPersistent = persistent;
    }

    public String getReturnUri() {
        return returnUri;
    }

    public void setReturnUri(String returnUri) {
        this.returnUri = returnUri;
    }
}