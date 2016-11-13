package app.wemob.blodo.fragments;

/**
 * Created by admin on 10/29/2016.
 */

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import app.wemob.blodo.ApiLinks;
import app.wemob.blodo.BlodoApp;
import app.wemob.blodo.R;
import app.wemob.blodo.utils.Validator;
import cz.msebera.android.httpclient.Header;

public class blodo_request_donation_dialog extends DialogFragment implements TextView.OnEditorActionListener {

    private EditText mEditText;


    public interface UserNameListener {
        void onFinishUserDialog(String user);
    }

    // Empty constructor required for DialogFragment
    public blodo_request_donation_dialog() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dlg_fragment_donate_request, container);
        final EditText txtcontactname=(EditText)view.findViewById(R.id.txtcontactperson);
       final  EditText txtcontactnumber=(EditText)view.findViewById(R.id.txtcontactnumber);

        final Spinner spinner = (Spinner)view.findViewById(R.id.spbgroup);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bgarray)); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        ArrayList<String> locationArray = new ArrayList<String>();
        BlodoApp blodoapp=((BlodoApp)getActivity().getApplication());
        for(int i = 0; i<blodoapp.getCountries_list().size(); i++)
        {
            locationArray.add(blodoapp.getCountries_list().get(i).getCity_name());
        }
        Collections.sort(locationArray);

        Log.e("Error","Inside View creation");

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, locationArray);
        final Spinner spncity = (Spinner)view.findViewById(R.id.spblocation);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spncity.setAdapter(locationAdapter);


        Button btnsubmit=(Button)view.findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRequest(txtcontactname.getText().toString(),txtcontactnumber.getText().toString(),spinner.getSelectedItem().toString(),spncity.getSelectedItem().toString());
            }
        });
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        getDialog().setTitle("Blood Donation Request");



        return view;
    }

    private void submitRequest(String name,String number,String bgroup,String location)
    {
        if(!Validator.isNetworkConnectionAvailable(getActivity()))
        {
            Validator.showToast(getActivity(),getResources().getString(R.string.network_err));
            return;
        }
        final String nameobj=name;
        final String mobileobj=number;
        final String bgobj=bgroup;
        final String cityobj=location;

        RequestParams params=new RequestParams();
        params.put("name", name);
        params.put("number",number);
        params.put("bgroup",bgroup);
        params.put("city",location);


        AsyncHttpClient client = new AsyncHttpClient();
        client.post(this.getActivity(), ApiLinks.baseURL + "/addDonationRequest", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                String msg=new String(responseBody);
                try {
                    showMessage(msg);
                }catch (Exception et){

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                String msg=new String(responseBody);
                try {
                    showMessage(msg);
                }catch (Exception et){

                }
            }
        });

    }

    private void showMessage(String response) throws Exception
    {

        JSONObject msgjson=new JSONObject(response);
        Toast.makeText(this.getActivity(), msgjson.getString("message"), Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        // Return input text to activity
        UserNameListener activity = (UserNameListener) getActivity();
        activity.onFinishUserDialog(mEditText.getText().toString());
        this.dismiss();
        return true;
    }
}