package app.wemob.blodo;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import app.wemob.blodo.data.City;

/**
 * Created by admin on 10/30/2016.
 */
public class BlodoApp extends Application {

    private ArrayList<City> countries_list;


    private ArrayList<String[]> faqs_list;
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        countries_list=new ArrayList<City>();
        faqs_list=new ArrayList<String[]>();
        try {
            populateCountryList();
            populateFaqs();
        } catch (Exception e) {
            Toast.makeText(this,"Exception"+e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void populateCountryList() throws Exception
    {
        InputStream input = getResources().openRawResource(R.raw.city);
        int size = input.available();


        byte[] buffer = new byte[size];

        input.read(buffer);

        input.close();

        String json = new String(buffer, "UTF-8");

        JSONArray countryjson=new JSONArray(json);

        for(int len=0;len<countryjson.length();len++)
        {
            JSONObject tempjson=countryjson.getJSONObject(len);
            City temp=new City();
            temp.setCity_slno(tempjson.getString("id"));
            temp.setCity_name(tempjson.getString("name"));
            temp.setCity_state(tempjson.getString("state"));

            getCountries_list().add(temp);
        }

    }

    public void populateFaqs() throws Exception
    {
        InputStream input = getResources().openRawResource(R.raw.faq);
        int size = input.available();


        byte[] buffer = new byte[size];

        input.read(buffer);

        input.close();

        String json = new String(buffer, "UTF-8");

        JSONArray faqJson=new JSONArray(json);

        for(int len=0;len<faqJson.length();len++)
        {
            JSONObject tempjson=faqJson.getJSONObject(len);
            String[] temp=new String[2];
            temp[0]=tempjson.getString("question");
            temp[1]=tempjson.getString("answer");

            getFaqs_list().add(temp);
        }

    }
    public ArrayList<City> getCountries_list() {
        return countries_list;
    }

    public void setCountries_list(ArrayList<City> countries_list) {
        this.countries_list = countries_list;
    }

    public ArrayList<String[]> getFaqs_list() {
        return faqs_list;
    }

    public void setFaqs_list(ArrayList<String[]> faqs_list) {
        this.faqs_list = faqs_list;
    }

}
