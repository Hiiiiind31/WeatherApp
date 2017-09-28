package training.weatherapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import training.weatherapp.GPS.GPSTracker;
import training.weatherapp.R;
import training.weatherapp.RecycleLists.Adapters.Cities_widget_Adapter;
import training.weatherapp.RoomDatabase.Models.Cities_Model;
import training.weatherapp.RoomDatabase.Models.Settings_Model;
import training.weatherapp.Volley.Model_Of_Current_Location.ModelOfCurrentLocation;

import static training.weatherapp.Activities.ActivityMain.db;
import static training.weatherapp.Activities.ActivityMain.queue;
import static training.weatherapp.Activities.ActivityMain.rootView;
import static training.weatherapp.Activities.ActivitySettings.isRTL;

public class ActivityWeatherWidget extends AppCompatActivity {

    ImageView icon6;
    LinearLayout widget_layout;
    GPSTracker gps;
    Switch current_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_widget);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cities_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<Cities_Model> all_cities = db.cities_Dao().getAll();
        Cities_widget_Adapter cities_adapter = new Cities_widget_Adapter(this, all_cities);
        recyclerView.setAdapter(cities_adapter);

        current_location = (Switch) findViewById(R.id.Switch_location);
        icon6 = (ImageView) findViewById(R.id.icon6);
        widget_layout = (LinearLayout) findViewById(R.id.widget_layout);


        if (current_location.isChecked()) {

            getWeatherByLocation();
        }

        if (!isRTL(rootView)) {
            icon6.setRotation(180);
            widget_layout.setRotation(360);

        }
    }

    public void back_img_click(View v) {
        Intent i = new Intent(ActivityWeatherWidget.this, ActivityMain.class);
        startActivity(i);
    }


    public void getWeatherByLocation() {

        // create class object
        gps = new GPSTracker(ActivityWeatherWidget.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            if (latitude != 0 && longitude != 0) {
                Get_data_of_current_location(latitude + " %2C-" + longitude);
            } else {
                current_location.setChecked(false);
                Toast.makeText(ActivityWeatherWidget.this, "Can`t find your location please try again later", Toast.LENGTH_LONG).show();
            }

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

    }


    private void Get_data_of_current_location(final String Lat_Lang) {


        new Thread(new Runnable() {
            public void run() {
                try {

                    String lang = db.settings_Dao().getAll().get(0).getLang();

                    String url = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey=jaWgzA5fF1XDAFBcAogi4TDGWFGh7phv&q=" + Lat_Lang + "&language=" + lang + "&details=true&toplevel=true";
                    StringRequest req = new StringRequest(url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();

                            ModelOfCurrentLocation modelOfCurrentLocation = gson.fromJson(response, ModelOfCurrentLocation.class);
                            String key = modelOfCurrentLocation.getKey();
                            String localizedName = modelOfCurrentLocation.getCountry().getLocalizedName();

                            db.cities_Dao().insertAll(new Cities_Model(localizedName, key));
                            db.settings_Dao().update(new Settings_Model(0, db.settings_Dao().getAll().get(0).getLang(), db.settings_Dao().getAll().get(0).getMetric1(), db.settings_Dao().getAll().get(0).getLanguage(), true));

                            Log.d("data", "done");

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("error", error.getMessage());

                        }
                    });

                    RequestQueue queue = Volley.newRequestQueue(ActivityWeatherWidget.this);
                    queue.add(req);
                } catch (Exception e) {
                    db.settings_Dao().update(new Settings_Model(0, db.settings_Dao().getAll().get(0).getLang(), db.settings_Dao().getAll().get(0).getMetric1(), db.settings_Dao().getAll().get(0).getLanguage(), false));

                    Toast.makeText(ActivityWeatherWidget.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        }).start();

    }

    public void add_city(View v) {
        Intent i = new Intent(ActivityWeatherWidget.this, ActivityAddCity.class);
        startActivity(i);

    }


}
