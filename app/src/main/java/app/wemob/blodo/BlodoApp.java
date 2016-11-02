package app.wemob.blodo;

import android.app.Application;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import app.wemob.blodo.data.Country;

/**
 * Created by admin on 10/30/2016.
 */
public class BlodoApp extends Application {

    private ArrayList<Country> countries_list;
    @Override
    public void onCreate() {
        super.onCreate();

        try {
            populateCountryList();
        } catch (Exception e) {
            Toast.makeText(this,"Exception",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void populateCountryList() throws Exception
    {
        InputStream input = getResources().getAssets().open("country.json");
        int size = input.available();

        byte[] buffer = new byte[size];

        input.read(buffer);

        input.close();

        String json = new String(buffer, "UTF-8");

        JSONArray countryjson=new JSONArray(json);

        for(int len=0;len<countryjson.length();len++)
        {
            JSONObject tempjson=countryjson.getJSONObject(len);
            Country temp=new Country();
            temp.setCountry_name(tempjson.getString("name"));
            temp.setCountry_name(tempjson.getString("dial_code"));
            temp.setCountry_name(tempjson.getString("code"));
            getCountries_list().add(temp);
        }
    }

    public ArrayList<Country> getCountries_list() {
        return countries_list;
    }

    public void setCountries_list(ArrayList<Country> countries_list) {
        this.countries_list = countries_list;
    }
}
