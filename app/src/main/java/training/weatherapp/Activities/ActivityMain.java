package training.weatherapp.Activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import training.weatherapp.R;
import training.weatherapp.RecycleLists.Adapters.D_Adapter;
import training.weatherapp.RecycleLists.Adapters.H_Adapter;
import training.weatherapp.RoomDatabase.AppDatabase;
import training.weatherapp.RoomDatabase.Models.Cities_Model;
import training.weatherapp.RoomDatabase.Models.Settings_Model;
import training.weatherapp.RoomDatabase.Models.Weather_days_model;
import training.weatherapp.RoomDatabase.Models.Weather_hours_model;
import training.weatherapp.Volley.Model_12Hours.Model12hour;
import training.weatherapp.Volley.Model_5Days.Model5days;

public class ActivityMain extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    static View rootView;

    static ArrayList<Weather_days_model> Days_temp_list;
    static ArrayList<Weather_hours_model> Hours_temp_list;

    Weather_days_model W_Days;
    Weather_hours_model w_Hours;
    public static AppDatabase db;

    static Boolean isInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create database
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "weather-app").allowMainThreadQueries().build();
        // set default settings
        db.settings_Dao().insertAll(new Settings_Model(0, "English", "f"));
        db.cities_Dao().insertAll(new Cities_Model("Egypt", ""));


        ///////////////////////

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        int size_cities_list = db.cities_Dao().getAll().size();
        List<Cities_Model> cities_list = db.cities_Dao().getAll();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), size_cities_list, cities_list);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        ////////////

        Days_temp_list = new ArrayList<>();

        db.WDays_Dao().insertAll(new Weather_days_model("Tomorrow,1sep", "17ْ ", "10ْ ", "", "cloud"));
        db.WDays_Dao().insertAll(new Weather_days_model("Fri,2sep", "25ْ ", "20ْ ", "", "sunny"));
        db.WDays_Dao().insertAll(new Weather_days_model("Sat,3sep", "23ْ ", "19ْ ", "", "sunny"));
        db.WDays_Dao().insertAll(new Weather_days_model("Sun,4sep", "28ْ ", "20ْ ", "", "sunny"));

        List<Weather_days_model> all1 = db.WDays_Dao().getAll();

        /////////////////////////////////////////////////////////


        if (isInternetOn()) {
            isInternet = true;
        } else {
            isInternet = false;
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_page_content = "page_content";

        RecyclerView recyclerView;
        RecyclerView recyclerView2;
        H_Adapter H_adapter;
        D_Adapter D_adapter;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, String page_content) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_page_content, page_content);
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_tab, container, false);

            Tool_bar(rootView);
            Recycle_design(rootView);

            TextView City_Name = rootView.findViewById(R.id.city_name);
            City_Name.setText(getArguments().getString(ARG_page_content));

            return rootView;
        }

        private void Recycle_design(View rootView) {

            // set up the  Hours RecyclerListView
            recyclerView = rootView.findViewById(R.id.Recycle_ViewList_hours);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            // set up the Days RecyclerListView
            recyclerView2 = rootView.findViewById(R.id.Recycle_ViewList_days);
            recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


            if (isInternet) {
                Get_data_of_5_days();
                Get_data_of_12_Hours();

            } else {

                Get_data_of_5_days_from_db();
                Get_data_of_12_Hours_from_db();


            }
        }

        private void Get_data_of_12_Hours_from_db() {


        }


        private void Get_data_of_5_days_from_db() {


        }


        // add toolbar to fragment
        private void Tool_bar(View rootView) {

            Toolbar myToolbar = rootView.findViewById(R.id.toolbar);
            ImageView settings_img = rootView.findViewById(R.id.Settings_img);
            ImageView cities_img = rootView.findViewById(R.id.Cities_img);
            settings_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), ActivitySettings.class);
                    startActivity(i);
                }
            });
            cities_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), ActivityCities.class);
                    startActivity(i);
                }
            });

            ((AppCompatActivity) getActivity()).setSupportActionBar(myToolbar);

        }

        private void Get_data_of_12_Hours() {

            String url = "http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/55489?apikey=jaWgzA5fF1XDAFBcAogi4TDGWFGh7phv&language=en-us";
            StringRequest req = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("code", response);

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    Model12hour[] model12hours = gson.fromJson(response, Model12hour[].class);
                    H_Adapter H_adapter = new H_Adapter(getActivity(), model12hours);
                    recyclerView.setAdapter(H_adapter);
                    add_data_of_12hour_in_database(model12hours);

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error", error.getMessage());

                }
            });

            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(req);
        }

        private void add_data_of_12hour_in_database(Model12hour[] response) {

            for (int i = 0; i < response.length; i++) {

                String epochDateTime = String.valueOf(response[i].getEpochDateTime());
                String value = String.valueOf(response[i].getTemperature().getValue());
                String iconPhrase = response[i].getIconPhrase();
                String weatherIcon = String.valueOf(response[i].getWeatherIcon());
                db.WHours_Doa().insertAll(new Weather_hours_model(epochDateTime, value, weatherIcon, iconPhrase));
            }

        }


        private void Get_data_of_5_days() {

            String url = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/55489?apikey=jaWgzA5fF1XDAFBcAogi4TDGWFGh7phv&language=en-us";
            StringRequest req = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("code", response);

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    Model5days model5dayses = gson.fromJson(response, Model5days.class);
                    D_Adapter D_adapter = new D_Adapter(getActivity(), model5dayses);
                    recyclerView2.setAdapter(D_adapter);

                    add_data_of_5Days_in_database(model5dayses);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error", error.getMessage());

                }
            });

            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(req);
        }

        private void add_data_of_5Days_in_database(Model5days response) {

            for (int i = 0; i < response.getDailyForecasts().size(); i++) {

                String epochDateTime = String.valueOf(response.getDailyForecasts().get(i).getDate());
                String Max_value = String.valueOf(response.getDailyForecasts().get(i).getTemperature().getMaximum());
                String Min_value = String.valueOf(response.getDailyForecasts().get(i).getTemperature().getMinimum());
                String iconPhrase = response.getDailyForecasts().get(i).getNight().getIconPhrase();
                String weatherIcon = String.valueOf(response.getDailyForecasts().get(i).getNight().getIcon());
                db.WDays_Dao().insertAll(new Weather_days_model(epochDateTime, Max_value, Min_value, weatherIcon, iconPhrase));
            }

        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        int size_of_list;
        List<Cities_Model> cities;

        public SectionsPagerAdapter(FragmentManager fm, int size_of_list, List<Cities_Model> cities) {
            super(fm);
            this.size_of_list = size_of_list;
            this.cities = cities;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1, cities.get(position).getCites());
        }

        @Override
        public int getCount() {
            return size_of_list;
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            // if connected with internet

            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

}
