package com.sustech.flightbooking.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ViewModelValidator {

    private static Pattern idCardRegex = Pattern.compile("^\\d{17}(\\d|X)$");

    public static List<String> validate(RegisterPassengerViewModel passenger) {
        ArrayList<String> errorMessages = new ArrayList<>();
        if (passenger.getUserName().isEmpty()) {
            errorMessages.add("User name can not be empty.");
        }
        if (passenger.getDisplayName().isEmpty()) {
            errorMessages.add("Display Name can not be empty");
        }
        if (!idCardRegex.matcher(passenger.getIdentityNumber()).find()) {
            errorMessages.add("Invalid ID card number.");
        }
        if (passenger.getPassword().isEmpty()) {
            errorMessages.add("Password can not be empty.");
        }
        if (!passenger.getPassword().equals(passenger.getConfirmPassword())) {
            errorMessages.add("Passwords do not match.");
        }
        return errorMessages;
    }
}
