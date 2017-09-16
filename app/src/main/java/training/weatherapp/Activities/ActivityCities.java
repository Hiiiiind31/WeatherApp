package training.weatherapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import training.weatherapp.R;
import training.weatherapp.RecycleLists.Adapters.Cities_View_Adapter;
import training.weatherapp.RoomDatabase.Models.Cities_Model;

import static training.weatherapp.Activities.ActivityMain.db;
import static training.weatherapp.Activities.ActivityMain.rootView;
import static training.weatherapp.Activities.ActivitySettings.isRTL;

public class ActivityCities extends AppCompatActivity {

    RecyclerView cities_listview;
    ImageView icon4;
    LinearLayout mag_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        icon4 = (ImageView) findViewById(R.id.icon4);
        mag_layout = (LinearLayout) findViewById(R.id.Man_layout);

        cities_listview = (RecyclerView) findViewById(R.id.cities_listview);
        cities_listview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<Cities_Model> all = db.cities_Dao().getAll();
        Cities_View_Adapter cities_adapter = new Cities_View_Adapter(this,all);
        if(!db.cities_Dao().getAll().isEmpty()){

             cities_listview.setAdapter(cities_adapter);
         }

        if (!isRTL(rootView)) {
            icon4.setRotation(180);
            mag_layout.setRotation(360);
            cities_listview.setRotation(360);

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

    public void back_img_click2(View v) {
        Intent i = new Intent(ActivityCities.this, ActivityMain.class);
        startActivity(i);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
