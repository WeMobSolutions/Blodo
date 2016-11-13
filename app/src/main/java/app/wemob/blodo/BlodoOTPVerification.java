package app.wemob.blodo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

import app.wemob.blodo.utils.Validator;
import cz.msebera.android.httpclient.Header;

public class BlodoOTPVerification extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, VerificationListener {

    private static final String TAG = Verification.class.getSimpleName();
    private Verification mVerification;
    TextView resend_timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blodo_otpverification);
        resend_timer = (TextView) findViewById(R.id.resend_timer);
        resend_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResendCode();
            }
        });
        startTimer();
        enableInputField(true);
        initiateVerification();
    }

    void createVerification(String phoneNumber, boolean skipPermissionCheck, String countryCode) {
        if (!skipPermissionCheck && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) ==
                PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 0);
            hideProgressBar();
        } else {
            mVerification = SendOtpVerification.createSmsVerification(this, phoneNumber, this, countryCode, true);
            mVerification.initiate();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "This application needs permission to read your SMS to automatically verify your "
                        + "phone, you may disable the permission once you have been verified.", Toast.LENGTH_LONG)
                        .show();
            }
            enableInputField(true);
        }
        initiateVerificationAndSuppressPermissionCheck();
    }

    void initiateVerification() {
        initiateVerification(false);
    }

    void initiateVerificationAndSuppressPermissionCheck() {
        initiateVerification(true);
    }

    void initiateVerification(boolean skipPermissionCheck) {
        Intent intent = getIntent();
        if (intent != null) {
            String phoneNumber = intent.getStringExtra(BlodoRegister.INTENT_PHONENUMBER);
            String countryCode = intent.getStringExtra(BlodoRegister.INTENT_COUNTRY_CODE);
            TextView phoneText = (TextView) findViewById(R.id.numberText);
            phoneText.setText("+" + countryCode + phoneNumber);
            createVerification(phoneNumber, skipPermissionCheck, countryCode);
        }
    }

    public void ResendCode() {
        startTimer();
        initiateVerificationAndSuppressPermissionCheck();
    }

    public void onSubmitClicked(View view) {
        String code = ((EditText) findViewById(R.id.inputCode)).getText().toString();
        if (!code.isEmpty()) {
            if (mVerification != null) {
                mVerification.verify(code);
                showProgress();
                TextView messageText = (TextView) findViewById(R.id.textView);
                messageText.setText("Verification in progress");
                enableInputField(false);
            }
        }
    }

    void enableInputField(boolean enable) {
        View container = findViewById(R.id.inputContainer);
        if (enable) {
            container.setVisibility(View.VISIBLE);
            EditText input = (EditText) findViewById(R.id.inputCode);
            input.requestFocus();
        } else {
            container.setVisibility(View.GONE);
        }
        TextView resend_timer = (TextView) findViewById(R.id.resend_timer);
        resend_timer.setClickable(false);
    }

    void hideProgressBarAndShowMessage(int message) {
        hideProgressBar();
        TextView messageText = (TextView) findViewById(R.id.textView);
        messageText.setText(message);
    }

    void hideProgressBar() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressIndicator);
        progressBar.setVisibility(View.INVISIBLE);
        TextView progressText = (TextView) findViewById(R.id.progressText);
        progressText.setVisibility(View.INVISIBLE);
    }

    void showProgress() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressIndicator);
        progressBar.setVisibility(View.VISIBLE);
    }

    void showCompleted() {
        ImageView checkMark = (ImageView) findViewById(R.id.checkmarkImage);
        checkMark.setVisibility(View.VISIBLE);

        SharedPreferences userpreferences=getSharedPreferences("blodouser",MODE_PRIVATE);

        if(!Validator.isNetworkConnectionAvailable(this))
        {
            Validator.showToast(this,getResources().getString(R.string.network_err));
            return;
        }

        final int userid=userpreferences.getInt("uid",0);
        final String nameobj=userpreferences.getString("username","");
        final String mobileobj=userpreferences.getString("mobile", "");
        final String bgobj=userpreferences.getString("bgroup", "");
        final String cityobj=userpreferences.getString("city", "");
        final int status=2;

        RequestParams params=new RequestParams();
        params.put("userid",userid);
        params.put("name", nameobj);
        params.put("district",cityobj);
        params.put("mobile",mobileobj);
        params.put("bgroup",bgobj);
        params.put("status",status);


        AsyncHttpClient client = new AsyncHttpClient();
        client.post(this, ApiLinks.baseURL + "/updateUser", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                String msg=new String(responseBody);
                try {
                    showDashBoard(msg,2);

                }catch (Exception et){

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                String msg=new String(responseBody);
                try {
                    showDashBoard(msg,2);
                }catch (Exception et){

                }
            }
        });


    }

    private void showDashBoard(String response,int status)
    {
        SharedPreferences userpreferences=getSharedPreferences("blodouser",MODE_PRIVATE);
        SharedPreferences.Editor editor = userpreferences.edit();
        editor.putInt("status",status);
        editor.commit();

        Intent dashboard=new Intent(this,BlodoDashboard.class);
        startActivity(dashboard);
        finish();
    }
    @Override
    public void onInitiated(String response) {
        Log.d(TAG, "Initialized!" + response);
    }

    @Override
    public void onInitiationFailed(Exception exception) {
        Log.e(TAG, "Verification initialization failed: " + exception.getMessage());
        hideProgressBarAndShowMessage(R.string.failed);
    }

    @Override
    public void onVerified(String response) {
        Log.d(TAG, "Verified!\n" + response);
        hideProgressBarAndShowMessage(R.string.verified);
        showCompleted();
    }

    @Override
    public void onVerificationFailed(Exception exception) {
        Log.e(TAG, "Verification failed: " + exception.getMessage());
        hideProgressBarAndShowMessage(R.string.failed);
        enableInputField(true);
    }

    private void startTimer() {
        resend_timer.setClickable(false);
        resend_timer.setTextColor(ContextCompat.getColor(BlodoOTPVerification.this, R.color.sendotp_grey));
        new CountDownTimer(30000, 1000) {
            int secondsLeft = 0;

            public void onTick(long ms) {
                if (Math.round((float) ms / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float) ms / 1000.0f);
                    resend_timer.setText("Resend via call ( " + secondsLeft + " )");
                }
            }

            public void onFinish() {
                resend_timer.setClickable(true);
                resend_timer.setText("Resend via call");
                resend_timer.setTextColor(ContextCompat.getColor(BlodoOTPVerification.this, R.color.colorPrimaryDark));
            }
        }.start();
    }
}