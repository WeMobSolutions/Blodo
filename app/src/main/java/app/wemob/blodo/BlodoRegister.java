package app.wemob.blodo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import cz.msebera.android.httpclient.Header;

public class BlodoRegister extends AppCompatActivity {

    public static final String INTENT_PHONENUMBER ="number" ;
    public static final String INTENT_COUNTRY_CODE ="country" ;

    BlodoApp blodoapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_blodo_register);

        blodoapp=(BlodoApp)getApplication();

        final EditText txtname=(EditText)findViewById(R.id.txtName);
        final EditText txtmobile=(EditText)findViewById(R.id.txtMobile) ;


        final Spinner spinner = (Spinner)findViewById(R.id.spbgroup);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bgarray)); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);


        ArrayList<String> locationArray = new ArrayList<String>();

        for(int i=0;i<blodoapp.getCountries_list().size();i++)
        {
            locationArray.add(blodoapp.getCountries_list().get(i).getCity_name());
        }
        Collections.sort(locationArray);

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locationArray);
        final Spinner spncity = (Spinner)findViewById(R.id.spncity);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spncity.setAdapter(locationAdapter);

        Button btnSkip=(Button)findViewById(R.id.btnskip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDashboard();
            }
        });

        Button btnRegister=(Button) findViewById(R.id.btnregister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(txtname.getText().toString(),txtmobile.getText().toString(),spinner.getSelectedItem().toString(),spncity.getSelectedItem().toString());
            }
        });

    }
    private void callDashboard()
    {
        Intent dashboard=new Intent(this,BlodoDashboard.class);
        startActivity(dashboard);
    }
    private void registerUser(String name,String mobile,String bgroup,String city)
    {
        final String nameobj=name;
        final String mobileobj=mobile;
        final String bgobj=bgroup;
        final String cityobj=city;

        RequestParams params=new RequestParams();
        params.put("name", name);
        params.put("district",city);
        params.put("mobile",mobile);
        params.put("bgroup",bgroup);


        AsyncHttpClient client = new AsyncHttpClient();
        client.post(this, ApiLinks.baseURL + "/register", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                storeUserDetails(nameobj,cityobj,bgobj,mobileobj,1);
                String msg=new String(responseBody);
                try {
                    showMessage(msg,1);
                }catch (Exception et){

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                String msg=new String(responseBody);
                try {
                    showMessage(msg,0);
                }catch (Exception et){

                }
            }
        });

    }
    private void storeUserDetails(String name,String city,String bgroup,String mob,int status)
    {
        SharedPreferences userpreferences=getSharedPreferences("blodouser",MODE_PRIVATE);
        SharedPreferences.Editor editor = userpreferences.edit();
        editor.putString("username", name);
        editor.putString("city", city);
        editor.putString("bgroup", bgroup);
        editor.putString("mobile", mob);
        editor.putInt("status",status);
        editor.commit();

    }
    private void showMessage(String response,int status) throws Exception
    {

        JSONObject msgjson=new JSONObject(response);
        Toast.makeText(this, msgjson.getString("message"), Toast.LENGTH_SHORT).show();
        resetFields();
        if(status==1) {
            showOTPPage();
            finish();
        }
    }

    private void showOTPPage()
    {
        SharedPreferences userpreferences=getSharedPreferences("blodouser",MODE_PRIVATE);
        Intent otpPage=new Intent(this,BlodoOTPVerification.class);
        otpPage.putExtra(INTENT_PHONENUMBER,userpreferences.getString("mobile",""));
        otpPage.putExtra(INTENT_COUNTRY_CODE,"91");
        startActivity(otpPage);
    }

    private void resetFields() {

        final EditText txtname=(EditText)findViewById(R.id.txtName);
        final EditText txtmobile=(EditText)findViewById(R.id.txtMobile) ;
        txtname.setText("");
        txtmobile.setText("");

    }


}
