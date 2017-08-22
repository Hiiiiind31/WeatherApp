package training.weatherapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import training.weatherapp.R;
import training.weatherapp.RecycleLists.Adapters.Cities_Adapter;

public class ActivityWeatherWidget extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_widget);
        String i[] = {"none ","Mansoura", "Cairo", "London"};

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cities_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Cities_Adapter cities_adapter = new Cities_Adapter(this,i);
        recyclerView.setAdapter(cities_adapter);
    }
}
