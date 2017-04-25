package com.sustech.flightbooking.domainmodel;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by Henry on 4/17/2017.
 */

public abstract class FlightBookingUser extends EntityBase {
    private String userName;

    private String passwordHash;

    public FlightBookingUser() {

    }

    public FlightBookingUser(UUID id) {
        super(id);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.passwordHash = hashPassword(password);
    }

    public boolean authenticate(String password) {
        return passwordHash.equals(hashPassword(password));
    }

    private String hashPassword(String original) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            digest.update(original.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(digest.digest());
    }

    public abstract String getRole();
}
