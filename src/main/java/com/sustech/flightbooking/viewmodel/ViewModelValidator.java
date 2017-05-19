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
        return errorMessages;
    }

    public static List<String> validate(AdminEditViewModel model) {
        ArrayList<String> errorMessages = new ArrayList<>();
        if (model.getUserName().isEmpty()) {
            errorMessages.add("User name can not be empty.");
        }
        return errorMessages;
    }

    public static List<String> validate(ChangePasswordViewModel model) {
        ArrayList<String> errorMessages = new ArrayList<>();
        if (model.getCurrentPassword().isEmpty() || model.getConfirmPassword().isEmpty() || model.getNewPassword().isEmpty()) {
            errorMessages.add("All fields can not be empty.");
        }
        if (!model.getNewPassword().equals(model.getConfirmPassword())) {
            errorMessages.add("Passwords does not match.");
        }
        return errorMessages;
    }
}
