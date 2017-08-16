package training.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import training.weatherapp.RecycleListViewHours.H_Adapter;
import training.weatherapp.RecycleListViewHours.Temp_Model_hours;
import training.weatherapp.RecyleListViewDays.D_Adapter;
import training.weatherapp.RecyleListViewDays.Temp_model_days;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView settings_img = (ImageView) findViewById(R.id.Settings_img);
        ImageView cities_img = (ImageView) findViewById(R.id.Cities_img);
        settings_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityMain.this,ActivitySettings.class);
                startActivity(i);

            }
        });
        cities_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityMain.this,ActivityCities.class);
                startActivity(i);
            }
        });

        setSupportActionBar(myToolbar);

        ///////////////////////

        // data to populate the RecyclerView with
        ArrayList<Temp_Model_hours> Simple_temp_list = new ArrayList<>();
        Simple_temp_list.add(0,new Temp_Model_hours("10 PM","","28"));
        Simple_temp_list.add(1,new Temp_Model_hours("11 PM","","27"));
        Simple_temp_list.add(2,new Temp_Model_hours("12 PM","","28"));
        Simple_temp_list.add(3,new Temp_Model_hours("1 AM","","28"));
        Simple_temp_list.add(4,new Temp_Model_hours("2 AM","","28"));
        Simple_temp_list.add(5,new Temp_Model_hours("3 AM","","28"));
        Simple_temp_list.add(6,new Temp_Model_hours("4 AM","","28"));
        Simple_temp_list.add(7,new Temp_Model_hours("5 AM","","27"));
        Simple_temp_list.add(8,new Temp_Model_hours("6 AM","","28"));
        Simple_temp_list.add(9,new Temp_Model_hours("7 AM","","28"));
        Simple_temp_list.add(10,new Temp_Model_hours("8 AM","","28"));
        Simple_temp_list.add(11,new Temp_Model_hours("9 AM","","28"));
        // set up the RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Recycle_ViewList_hours);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        H_Adapter H_adapter = new H_Adapter(this, Simple_temp_list);
        recyclerView.setAdapter(H_adapter);


        // data to populate the RecyclerView with

        ArrayList<Temp_model_days> Simple_temp_list2 = new ArrayList<>();
        Simple_temp_list2.add(new Temp_model_days("Tomorrow,9Aug","","25","16"));
        Simple_temp_list2.add(new Temp_model_days("Fri,10Aug","","25","16"));
        Simple_temp_list2.add(new Temp_model_days("Sat,11Aug","","25","16"));
        Simple_temp_list2.add(new Temp_model_days("Sun,12Aug","","25","16"));


        // set up the RecyclerView
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.Recycle_ViewList_days);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        D_Adapter D_adapter = new D_Adapter(this, Simple_temp_list2);
        recyclerView2.setAdapter(D_adapter);



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
