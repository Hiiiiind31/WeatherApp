package training.weatherapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import training.weatherapp.R;
import training.weatherapp.RecycleLists.Adapters.cities_Adapter;
import training.weatherapp.Volley.Model_Cities.ModelCity;

public class ActivityAddCity extends AppCompatActivity {

    RecyclerView cities_list;
    EditText search_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        cities_list = (RecyclerView) findViewById(R.id.cities_list2);
        cities_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        search_city = (EditText) findViewById(R.id.Search_city_id);



    }

    public void get_city(View V) {


        if (!search_city.getText().toString().isEmpty()) {

            String txt = search_city.getText().toString();
            String url = "http://dataservice.accuweather.com/locations/v1/cities/autocomplete?apikey=jaWgzA5fF1XDAFBcAogi4TDGWFGh7phv&q=" + txt + "&language=en-us";
            StringRequest req = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("code", response);

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    ModelCity[] modelCities = gson.fromJson(response, ModelCity[].class);
                    cities_Adapter Adapter = new cities_Adapter(getApplicationContext(), modelCities);
                    cities_list.setAdapter(Adapter);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error", error.getMessage());
                }
            });

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(req);

        }

    }
}
