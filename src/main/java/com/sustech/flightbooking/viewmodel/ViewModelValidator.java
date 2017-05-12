package com.sustech.flightbooking.viewmodel;

import com.sustech.flightbooking.viewmodel.manage.admins.AdminEditViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ViewModelValidator {

    private static final Pattern idCardRegex = Pattern.compile("^\\d{17}(\\d|X)$");

    public static List<String> validate(PassengerEditModelViewModel model) {
        ArrayList<String> errorMessages = new ArrayList<>();
        if (model.getUserName().isEmpty()) {
            errorMessages.add("User name can not be empty.");
        }
        if (model.getDisplayName().isEmpty()) {
            errorMessages.add("Real Name can not be empty");
        }
        if (!idCardRegex.matcher(model.getIdentityNumber()).find()) {
            errorMessages.add("Invalid ID card number.");
        }
        if (model.getPassword().isEmpty()) {
            errorMessages.add("Password can not be empty.");
        }
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            errorMessages.add("Passwords do not match.");
        }
        return errorMessages;
    }

    public static List<String> validate(AdminEditViewModel model) {
        ArrayList<String> errorMessages = new ArrayList<>();
        if (model.getUserName().isEmpty()) {
            errorMessages.add("User name can not be empty.");
        }
        if (model.getPassword().isEmpty()) {
            errorMessages.add("Password can not be empty.");
        }
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            errorMessages.add("Passwords do not match.");
        }
        return errorMessages;
    }
}
