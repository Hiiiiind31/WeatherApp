package training.weatherapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import training.weatherapp.R ;
import training.weatherapp.RecycleLists.Adapters.Cities_View_Adapter;
import training.weatherapp.RecycleLists.Adapters.D_Adapter;
import training.weatherapp.RecycleLists.Adapters.cities_Adapter;
import training.weatherapp.RoomDatabase.Models.Cities_Model;
import training.weatherapp.Volley.Model_5Days.Model5days;
import training.weatherapp.Volley.Model_Cities.ModelCity;

import static training.weatherapp.Activities.ActivityMain.db;
import static training.weatherapp.R.id.cities_list;

public class ActivityCities extends AppCompatActivity {

    RecyclerView cities_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        cities_listview = (RecyclerView) findViewById(R.id.cities_listview);
        cities_listview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<Cities_Model> all = db.cities_Dao().getAll();
        Cities_View_Adapter cities_adapter = new Cities_View_Adapter(this,all);
        if(!db.cities_Dao().getAll().isEmpty()){

             cities_listview.setAdapter(cities_adapter);
         }

    }



    public void add_city(View v) {
        Intent i = new Intent(ActivityCities.this,ActivityAddCity.class);
        startActivity(i);
    }

    public void open_widget(View v){
        Intent i = new Intent(ActivityCities.this,ActivityWeatherWidget.class);
        startActivity(i);
    }
    public void back_img_click(View v) {
        this.setContentView(R.layout.activity_cities);
    }

    public void back_img_click2(View v) {
        Intent i = new Intent(ActivityCities.this,ActivityMain.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
