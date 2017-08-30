package training.weatherapp.Activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
import training.weatherapp.Volley.Model_Autocom_cities.Model;

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
    static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create database
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "weather-app").allowMainThreadQueries().build();
        // set default settings
        db.settings_Dao().insertAll(new Settings_Model(0, "English", "f"));
        db.cities_Dao().insertAll(new Cities_Model("London", ""));
        db.cities_Dao().insertAll(new Cities_Model("Egypt", ""));
        db.cities_Dao().insertAll(new Cities_Model("Italy", ""));
        db.cities_Dao().insertAll(new Cities_Model("NewYork", ""));


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

        Hours_temp_list = new ArrayList<>();

        db.WHours_Doa().insertAll(new Weather_hours_model("1 PM", "31ْ ", "", "sunny"));
        db.WHours_Doa().insertAll(new Weather_hours_model("2 PM", "29ْ ", "", "sunny"));
        db.WHours_Doa().insertAll(new Weather_hours_model("3 PM", "24ْ ", "", "sunny"));
        db.WHours_Doa().insertAll(new Weather_hours_model("4 PM", "25ْ ", "", "sunny"));
        db.WHours_Doa().insertAll(new Weather_hours_model("5 PM", "31ْ ", "", "sunny"));
        db.WHours_Doa().insertAll(new Weather_hours_model("6 PM", "29ْ ", "", "sunny"));
        db.WHours_Doa().insertAll(new Weather_hours_model("7 PM", "24ْ ", "", "sunny"));
        db.WHours_Doa().insertAll(new Weather_hours_model("8 PM", "25ْ ", "", "sunny"));
        db.WHours_Doa().insertAll(new Weather_hours_model("9 PM", "31ْ ", "", "sunny"));
        db.WHours_Doa().insertAll(new Weather_hours_model("10 PM", "29ْ ", "", "sunny"));
        db.WHours_Doa().insertAll(new Weather_hours_model("11 PM", "24ْ ", "", "sunny"));
        db.WHours_Doa().insertAll(new Weather_hours_model("12 AM", "25ْ ", "", "sunny"));

        List<Weather_hours_model> all2 = db.WHours_Doa().getAll();


        for (int i = 0; i < 4; i++) {
            Days_temp_list.add(all1.get(i));
        }

        for (int i = 0; i < 12; i++) {
            Hours_temp_list.add(all2.get(i));
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

            Get_data_of_5_days();
            Get_data_of_12_Hours();

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

    }


