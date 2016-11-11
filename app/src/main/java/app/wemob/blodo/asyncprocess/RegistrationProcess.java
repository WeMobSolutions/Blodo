package app.wemob.blodo.asyncprocess;

import android.os.AsyncTask;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import app.wemob.blodo.ApiLinks;
import app.wemob.blodo.BlodoApp;
import app.wemob.blodo.BlodoRegister;
import cz.msebera.android.httpclient.Header;

/**
 * Created by admin on 11/3/2016.
 */

public class RegistrationProcess extends AsyncTask {
    BlodoRegister parentObject;
    ArrayList<String> dataToStore;
  public RegistrationProcess(BlodoRegister parentActivity, ArrayList<String> data)
    {
        parentObject=parentActivity;
        dataToStore=data;

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Object[] objects)
    {
               return null;
    }
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
