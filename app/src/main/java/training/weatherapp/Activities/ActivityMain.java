package training.weatherapp.Activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Color;
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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import training.weatherapp.PrefManager;
import training.weatherapp.R;
import training.weatherapp.RecycleLists.Adapters.D_Adapter;
import training.weatherapp.RecycleLists.Adapters.H_Adapter;
import training.weatherapp.RecycleLists.Offline_Adapter.Offline_12H_Adapter;
import training.weatherapp.RecycleLists.Offline_Adapter.Offline_5D_Adapter_;
import training.weatherapp.RecycleLists.offline_Models.Offline_Model_12Hours;
import training.weatherapp.RecycleLists.offline_Models.Offline_model_5Days;
import training.weatherapp.RoomDatabase.AppDatabase;
import training.weatherapp.RoomDatabase.Models.Cities_Model;
import training.weatherapp.RoomDatabase.Models.Settings_Model;
import training.weatherapp.RoomDatabase.Models.Weather_days_model;
import training.weatherapp.RoomDatabase.Models.Weather_hours_model;
import training.weatherapp.Volley.Model_12Hours.Model12hour;
import training.weatherapp.Volley.Model_5Days.Model5days;

import static training.weatherapp.Activities.ActivityMain.PlaceholderFragment.dots;
import static training.weatherapp.Activities.ActivityMain.PlaceholderFragment.dotsLayout;

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
    PrefManager prefManager;

    public static TextView main_temp;
    public static TextView main_max_min_temp;
    public static TextView main_w_phrase;

    static RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);

        // Create database
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "weather-app").allowMainThreadQueries().build();

        // Checking for first time launch
        prefManager = new PrefManager(this);
        if (prefManager.isFirstTimeLaunch()) {
            prefManager.setFirstTimeLaunch(false);

            // set default settings
            db.settings_Dao().insertAll(new Settings_Model(0, "en-us", "true", "English", "C"));
            db.cities_Dao().insertAll(new Cities_Model("London", "55489"));
        }

        ///////////////////////


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        int size_cities_list = db.cities_Dao().getAll().size();
        List<Cities_Model> cities_list = db.cities_Dao().getAll();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), size_cities_list, cities_list);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        ///////////
        main_temp = (TextView) findViewById(R.id.main_temp);
        main_max_min_temp = (TextView) findViewById(R.id.main_max_min_temp);
        main_w_phrase = (TextView) findViewById(R.id.main_w_phrase);


        /////////////////////////////////////////////////////////


        if (isInternetOn()) {
            isInternet = true;
        } else {
            isInternet = false;
        }


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

            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            return false;
        }
        return false;
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
        static TextView[] dots;
        static LinearLayout dotsLayout;


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

            dotsLayout = rootView.findViewById(R.id.layoutDots);
            addBottomDots(getArguments().getInt(ARG_SECTION_NUMBER) - 1);

            Tool_bar(rootView);
            Recycle_design(rootView);

            TextView City_Name = rootView.findViewById(R.id.city_name);
            City_Name.setText(getArguments().getString(ARG_page_content));
            ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    addBottomDots(position);

                }

                @Override
                public void onPageSelected(int position) {
                    addBottomDots(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            };

            return rootView;
        }

        public void addBottomDots(int currentPage) {
            dots = new TextView[db.cities_Dao().getAll().size()];

            dotsLayout.removeAllViews();
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new TextView(getContext());
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(30);
                dots[i].setTextColor(Color.GRAY);
                dotsLayout.addView(dots[i]);
            }
            if (dots.length > 0)
                dots[currentPage].setTextColor(Color.WHITE);
        }


        private void Recycle_design(View rootView) {

            // set up the  Hours RecyclerListView
            recyclerView = rootView.findViewById(R.id.Recycle_ViewList_hours);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            // set up the Days RecyclerListView
            recyclerView2 = rootView.findViewById(R.id.Recycle_ViewList_days);
            recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


            if (isInternet) {
                Get_data_of_5_days(db.cities_Dao().getAll().get(getArguments().getInt(ARG_SECTION_NUMBER) - 1));
                Get_data_of_12_Hours(db.cities_Dao().getAll().get(getArguments().getInt(ARG_SECTION_NUMBER) - 1));

            } else {

                Get_offline_data_of_5_days_from_db(db.cities_Dao().getAll().get(getArguments().getInt(ARG_SECTION_NUMBER) - 1));
                Get_offline_data_of_12_Hours_from_db(db.cities_Dao().getAll().get(getArguments().getInt(ARG_SECTION_NUMBER) - 1));


            }
        }

        private void Get_offline_data_of_12_Hours_from_db(Cities_Model cities_model) {


            String Temp, Date, Icon, Icon_phrase;
            ArrayList<Offline_Model_12Hours> offlineModel12Hourses = new ArrayList<>();

            for (int i = 0; i < 12; i++) {


                Temp = db.WHours_Doa().getAll().get(i).getTemp();
                Date = db.WHours_Doa().getAll().get(i).getDate();
                Icon = db.WHours_Doa().getAll().get(i).getIcon();
                Icon_phrase = db.WHours_Doa().getAll().get(i).getIconPhrase();

                Offline_Model_12Hours offline_model_12hours = new Offline_Model_12Hours(Date, Icon, Temp, Icon_phrase);

                offlineModel12Hourses.add(offline_model_12hours);

            }
            Offline_12H_Adapter offline_12H_adapter = new Offline_12H_Adapter(getContext(), offlineModel12Hourses);
            recyclerView.setAdapter(offline_12H_adapter);

        }


        private void Get_offline_data_of_5_days_from_db(Cities_Model cities_model) {


            String Max_temp, Min_temp, Date, Icon;
            ArrayList<Offline_model_5Days> offlineModel5Dayses = new ArrayList<>();


            for (int i = 0; i < 4; i++) {


                Min_temp = db.WDays_Dao().getAll().get(i).getMin_temp();
                Max_temp = db.WDays_Dao().getAll().get(i).getMax_temp();
                Date = db.WDays_Dao().getAll().get(i).getDate();
                Icon = db.WDays_Dao().getAll().get(i).getIcon();
                Offline_model_5Days offline_model_5Days = new Offline_model_5Days(Date, Icon, Max_temp, Min_temp);

                offlineModel5Dayses.add(offline_model_5Days);

            }
            Offline_5D_Adapter_ offline_5D_adapter_ = new Offline_5D_Adapter_(getContext(), offlineModel5Dayses);
            recyclerView2.setAdapter(offline_5D_adapter_);

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

        private void Get_data_of_12_Hours(final Cities_Model cities_model) {


            new Thread(new Runnable() {
                public void run() {
                    try {
                        String city = cities_model.getCities_keys();
                        String lang = db.settings_Dao().getAll().get(0).getLang();
                        String metric = db.settings_Dao().getAll().get(0).getMetric1();

                        String url = "http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/" + city + "?apikey=jaWgzA5fF1XDAFBcAogi4TDGWFGh7phv&language=" + lang + "&metric=" + metric;
                        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

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

                        queue.add(req);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                    }
                }
            }).start();

        }

        private void add_data_of_12hour_in_database(Model12hour[] response) {

            String city_keys = db.cities_Dao().getAll().get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).getCities_keys();

            Log.d("city_key", db.cities_Dao().getAll().contains(city_keys) + "");


            for (int i = 0; i < response.length; i++) {

                String epochDateTime = String.valueOf(response[i].getEpochDateTime().intValue());
                String value = String.valueOf(response[i].getTemperature().getValue().intValue());
                String iconPhrase = response[i].getIconPhrase();
                String weatherIcon = String.valueOf(response[i].getWeatherIcon());
                db.WHours_Doa().insertAll(new Weather_hours_model(city_keys, epochDateTime, value, weatherIcon, iconPhrase));
            }

        }


        private void Get_data_of_5_days(final Cities_Model cities_model) {


            new Thread(new Runnable() {
                public void run() {
                    try {

                        String city = cities_model.getCities_keys();
                        String lang = db.settings_Dao().getAll().get(0).getLang();
                        String metric = db.settings_Dao().getAll().get(0).getMetric1();

                        String url = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/" + city + "?apikey=jaWgzA5fF1XDAFBcAogi4TDGWFGh7phv&language=" + lang + "&metric=" + metric;
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

                        queue.add(req);

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }).start();

        }


        private void add_data_of_5Days_in_database(Model5days response) {

            for (int i = 0; i < response.getDailyForecasts().size(); i++) {


                String epochDateTime = String.valueOf(response.getDailyForecasts().get(i).getEpochDate().intValue());
                String Max_value = String.valueOf(response.getDailyForecasts().get(i).getTemperature().getMaximum().getValue().intValue());
                String Min_value = String.valueOf(response.getDailyForecasts().get(i).getTemperature().getMinimum().getValue().intValue());
                String iconPhrase = response.getDailyForecasts().get(i).getNight().getIconPhrase();
                String weatherIcon = String.valueOf(response.getDailyForecasts().get(i).getNight().getIcon());
                String city_keys = db.cities_Dao().getAll().get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).getCities_keys();

                db.WDays_Dao().insertAll(new Weather_days_model(city_keys, epochDateTime, Max_value, Min_value, weatherIcon, iconPhrase));
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
}