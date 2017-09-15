package training.weatherapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import training.weatherapp.R;
import training.weatherapp.RecycleLists.Adapters.Cities_widget_Adapter;

import static training.weatherapp.Activities.ActivityMain.rootView;
import static training.weatherapp.Activities.ActivitySettings.isRTL;

public class ActivityWeatherWidget extends AppCompatActivity {

    ImageView icon6;
    LinearLayout widget_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_widget);
        String i[] = {"none ","NewYork", "Cairo", "London"};

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cities_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Cities_widget_Adapter cities_adapter = new Cities_widget_Adapter(this,i);
        recyclerView.setAdapter(cities_adapter);

        icon6 = (ImageView) findViewById(R.id.icon6);
        widget_layout = (LinearLayout) findViewById(R.id.widget_layout);


        if (!isRTL(rootView)) {
            icon6.setRotation(180);
            widget_layout.setRotation(360);

        }
    }

    public void back_img_click(View v) {
        Intent i = new Intent(ActivityWeatherWidget.this,ActivitySettings.class);
        startActivity(i);
    }
}
