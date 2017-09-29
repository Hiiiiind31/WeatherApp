package training.weatherapp.Activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
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
import android.util.DisplayMetrics;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    PrefManager prefManager;

    static TextView main_temp;
    static TextView main_max_min_temp;
    static TextView main_w_phrase;
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
            db.settings_Dao().insertAll(new Settings_Model(0, "en-us", "metric", "English", false));
            db.cities_Dao().insertAll(new Cities_Model("London,us", "55489"));
        }

        setLocal(db.settings_Dao().getAll().get(0).getLang());




        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        int size_cities_list = db.cities_Dao().getAll().size();
        List<Cities_Model> cities_list = db.cities_Dao().getAll();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), size_cities_list, cities_list);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        /////////////////////////////////////////////////////////


        isInternet = isInternetOn();


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


    private void setLocal(String language) {
        Locale myLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        conf.setLayoutDirection(myLocale);
        res.updateConfiguration(conf, dm);

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
                dots[i].setTextSize(25);
                dots[i].setTextColor(Color.GRAY);
                dotsLayout.addView(dots[i]);
            }
            if (dots.length > 0)
                dots[currentPage].setTextColor(Color.WHITE);
        }


        private void Recycle_design(View rootView) {

            main_temp = rootView.findViewById(R.id.main_temp);
            main_max_min_temp = rootView.findViewById(R.id.main_max_min_temp);
            main_w_phrase = rootView.findViewById(R.id.main_w_phrase);


            // set up the Days RecyclerListView
            recyclerView = rootView.findViewById(R.id.Recycle_ViewList_hours);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            // set up the Days RecyclerListView
            recyclerView2 = rootView.findViewById(R.id.Recycle_ViewList_days);
            recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


            if (isInternet) {
                Get_data_of_5_days(db.cities_Dao().getAll().get(getArguments().getInt(ARG_SECTION_NUMBER) - 1));

            } else {

                Get_offline_data_of_5_days_from_db();


            }
        }


        private void Get_offline_data_of_5_days_from_db() {


            String Max_temp, Min_temp, Date, temp, description;

            int Icon;
            ArrayList<Offline_model_5Days> offlineModel5Dayses = new ArrayList<>();

            //data of days
            for (int i = 0; i < 5; i++) {

                Min_temp = db.WDays_Dao().getAll().get(i).getMin_temp();
                Max_temp = db.WDays_Dao().getAll().get(i).getMax_temp();
                Date = db.WDays_Dao().getAll().get(i).getDate();
                Icon = db.WDays_Dao().getAll().get(i).getIcon();
                Offline_model_5Days offline_model_5Days = new Offline_model_5Days(Date, Icon, Max_temp, Min_temp);

                offlineModel5Dayses.add(offline_model_5Days);
            }
            Log.d("data", offlineModel5Dayses.get(0).getD_Max_Temp() + "");
            Offline_5D_Adapter_ offline_5D_adapter_ = new Offline_5D_Adapter_(getContext(), offlineModel5Dayses);
            recyclerView2.setAdapter(offline_5D_adapter_);

            //data of hours

            ArrayList<Offline_Model_12Hours> offlineModel12Hour = new ArrayList<>();

            for (int i = 0; i < 8; i++) {

                temp = db.WHours_Doa().getAll().get(i).getTemp();
                description = db.WHours_Doa().getAll().get(i).getIconPhrase();
                Date = db.WHours_Doa().getAll().get(i).getDate();
                Icon = db.WHours_Doa().getAll().get(i).getIcon();
                Offline_Model_12Hours offline_Model_12Hour = new Offline_Model_12Hours(Date, Icon, temp, description);

                offlineModel12Hour.add(offline_Model_12Hour);
            }
            Offline_12H_Adapter offline_12H_adapter_ = new Offline_12H_Adapter(getContext(), offlineModel12Hour);
            recyclerView.setAdapter(offline_12H_adapter_);


            main_temp.setText(db.WHours_Doa().getAll().get(0).getTemp() + " ْ ");
            //  main_max_min_temp.setText(model5dayses.getList().get(0).getMain().getTempMax()+" ْ "+model5dayses.getList().get(0).getMain().getTempMin()+" ْ ");
            main_w_phrase.setText(db.WHours_Doa().getAll().get(0).getIconPhrase());



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



        private void Get_data_of_5_days(final Cities_Model cities_model) {


            new Thread(new Runnable() {
                public void run() {
                    try {

                        String city = cities_model.getCites();
                        String lang = db.settings_Dao().getAll().get(0).getLang();
                        String metric = db.settings_Dao().getAll().get(0).getMetric1();

                        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&mode=json&appid=fe6f25028b52c2bb6adda4031e39a6c1&units=" + metric + "&lang=" + lang;
                        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.d("code", response);

                                GsonBuilder builder = new GsonBuilder();
                                Gson gson = builder.create();

                                Model5days model5dayses = gson.fromJson(response, Model5days.class);
                                int[] mtimes = {0, 8, 16, 24, 32};
                                D_Adapter D_adapter = new D_Adapter(getActivity(), model5dayses, mtimes);
                                H_Adapter H_adapter = new H_Adapter(getActivity(), model5dayses);
                                recyclerView2.setAdapter(D_adapter);
                                recyclerView.setAdapter(H_adapter);

                                main_temp.setText(model5dayses.getList().get(0).getMain().getTemp().intValue() + " ْ ");
                                //  main_max_min_temp.setText(model5dayses.getList().get(0).getMain().getTempMax()+" ْ "+model5dayses.getList().get(0).getMain().getTempMin()+" ْ ");
                                main_w_phrase.setText(model5dayses.getList().get(0).getWeather().get(0).getDescription());


                                add_data_of_5Days_in_database(model5dayses, mtimes);


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


        private void add_data_of_5Days_in_database(Model5days response, int[] mtimes) {

            for (int i = 0; i < mtimes.length; i++) {

                int pos = mtimes[i];

                Log.d("po", pos + "");
                // add 5 days data

                String epochDateTime = String.valueOf(response.getList().get(pos).getDt().intValue());
                //String Max_value = String.valueOf(response.getDailyForecasts().get(i).getTemperature().getMaximum().getValue().intValue());
                //String Min_value = String.valueOf(response.getDailyForecasts().get(i).getTemperature().getMinimum().getValue().intValue());
                String temp = String.valueOf(response.getList().get(pos).getMain().getTemp().intValue());
                String temp2 = String.valueOf(response.getList().get(pos).getMain().getTemp().intValue() - 3);
                String iconPhrase = response.getList().get(pos).getWeather().get(0).getDescription();
                String weatherIcon = response.getList().get(pos).getWeather().get(0).getIcon();
                int icon = select_icon(weatherIcon);
                String city_keys = db.cities_Dao().getAll().get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).getCities_keys();
                db.WDays_Dao().insertAll(new Weather_days_model(city_keys, epochDateTime, temp, temp2, icon, iconPhrase));


            }

            for (int i = 0; i < 8; i++) {

                // add 12hours data

                String epochDateTime = String.valueOf(response.getList().get(i).getDt().intValue());
//                String Max_value = String.valueOf(response.getDailyForecasts().get(i).getTemperature().getMaximum().getValue().intValue());
//                String Min_value = String.valueOf(response.getDailyForecasts().get(i).getTemperature().getMinimum().getValue().intValue());
                String temp = String.valueOf(response.getList().get(i).getMain().getTemp().intValue());
                String iconPhrase = response.getList().get(i).getWeather().get(0).getDescription();
                String weatherIcon = response.getList().get(i).getWeather().get(0).getIcon();
                int icon = select_icon(weatherIcon);
                String city_keys = db.cities_Dao().getAll().get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).getCities_keys();
                db.WHours_Doa().insertAll(new Weather_hours_model(city_keys, epochDateTime, temp, icon, iconPhrase));

            }

            // Main Maxtemp , Mintemp ;

            //String Max_temp = String.valueOf(response.getList().get(0).getMain().getTemp().intValue());
            // String Min_temp = String.valueOf(response.getDailyForecasts().get(0).getTemperature().getMinimum().getValue().intValue());
            // main_max_min_temp.setText(Max_temp + " ْ /");


        }

        public static int select_icon(String weatherIcon) {

            int Icon;

            switch (weatherIcon) {
                case "01d":
                    Icon = R.drawable.d01;
                    break;
                case "02d":
                    Icon = R.drawable.d02;
                    break;
                case "03d":
                    Icon = R.drawable.d03;
                    break;
                case "04d":
                    Icon = R.drawable.d04;
                    break;
                case "09d":
                    Icon = R.drawable.d09;
                    break;
                case "10d":
                    Icon = R.drawable.d10;
                    break;
                case "11d":
                    Icon = R.drawable.d11;
                    break;
                case "13d":
                    Icon = R.drawable.d13;
                    break;
                case "50d":
                    Icon = R.drawable.d50;
                    break;
                case "01n":
                    Icon = R.drawable.d01;
                    break;
                case "02n":
                    Icon = R.drawable.d02;
                    break;
                case "03n":
                    Icon = R.drawable.d03;
                    break;
                case "04n":
                    Icon = R.drawable.d04;
                    break;
                case "09n":
                    Icon = R.drawable.d09;
                    break;
                case "10n":
                    Icon = R.drawable.d10;
                    break;
                case "11n":
                    Icon = R.drawable.d11;
                    break;
                case "13n":
                    Icon = R.drawable.d13;
                    break;
                case "50n":
                    Icon = R.drawable.d50;
                    break;
                default:
                    Icon = R.drawable.d01;
                    break;
            }
            return Icon;
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