package app.wemob.blodo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 11/7/2016.
 */

public class Validator {

    // validating email id
    public boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    public boolean isValid(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }
}
