package app.wemob.blodo.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

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

    public static boolean isNetworkConnectionAvailable(Activity callingPage) {
        ConnectivityManager cm = (ConnectivityManager) callingPage.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }
    public static void showToast(Context contextobj,String message)
    {
        Toast.makeText(contextobj,message,Toast.LENGTH_SHORT).show();
    }
}
