package app.wemob.blodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class BlodoRegister extends AppCompatActivity {

    BlodoApp blodoapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_blodo_register);

        blodoapp=(BlodoApp)getApplication();

        Spinner spinner = (Spinner)findViewById(R.id.spbgroup);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bgarray)); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);


//        ArrayList<String> locationArray = new ArrayList<String>();
//        for(int i=0;i<blodoapp.getCountries_list().size();i++)
//        {
//            locationArray.add(blodoapp.getCountries_list().get(i).getCountry_name());
//        }
//        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locationArray);
//        Spinner spncountry = (Spinner)findViewById(R.id.spcountry);
//        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spncountry.setAdapter(locationAdapter);

        Button btnSkip=(Button)findViewById(R.id.btnskip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDashboard(false);
            }
        });

    }
    private void callDashboard(boolean register)
    {
        Intent dashboard=new Intent(this,BlodoDashboard.class);
        dashboard.putExtra("register_status",register);
        startActivity(dashboard);
    }

}
