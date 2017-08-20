package training.weatherapp.Activities;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import training.weatherapp.R;
import training.weatherapp.RecycleLists.Adapters.D_Adapter;
import training.weatherapp.RecycleLists.Adapters.H_Adapter;
import training.weatherapp.RecycleLists.Models.Temp_Model_hours;
import training.weatherapp.RecycleLists.Models.Temp_model_days;

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

    static ArrayList<Temp_Model_hours> Simple_temp_list;
    static ArrayList<Temp_model_days> Simple_temp_list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///////////////////////

        String i[] = {"Mansoura", "Cairo", "London"};

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), i.length, i);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        ////////////

        // data to populate the RecyclerView with
        Simple_temp_list = new ArrayList<>();
        Simple_temp_list.add(0, new Temp_Model_hours("10 PM", "", "28"));
        Simple_temp_list.add(1, new Temp_Model_hours("11 PM", "", "27"));
        Simple_temp_list.add(2, new Temp_Model_hours("12 PM", "", "28"));
        Simple_temp_list.add(3, new Temp_Model_hours("1 AM", "", "28"));
        Simple_temp_list.add(4, new Temp_Model_hours("2 AM", "", "28"));
        Simple_temp_list.add(5, new Temp_Model_hours("3 AM", "", "28"));
        Simple_temp_list.add(6, new Temp_Model_hours("4 AM", "", "28"));
        Simple_temp_list.add(7, new Temp_Model_hours("5 AM", "", "27"));
        Simple_temp_list.add(8, new Temp_Model_hours("6 AM", "", "28"));
        Simple_temp_list.add(9, new Temp_Model_hours("7 AM", "", "28"));
        Simple_temp_list.add(10, new Temp_Model_hours("8 AM", "", "28"));
        Simple_temp_list.add(11, new Temp_Model_hours("9 AM", "", "28"));


        // data to populate the RecyclerView with

        Simple_temp_list2 = new ArrayList<>();
        Simple_temp_list2.add(new Temp_model_days("Tomorrow,9Aug", "", "25", "16"));
        Simple_temp_list2.add(new Temp_model_days("Fri,10Aug", "", "25", "16"));
        Simple_temp_list2.add(new Temp_model_days("Sat,11Aug", "", "25", "16"));
        Simple_temp_list2.add(new Temp_model_days("Sun,12Aug", "", "25", "16"));


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
            Recycle_design (rootView);

            TextView City_Name = rootView.findViewById(R.id.city_name);
            City_Name.setText(getArguments().getString(ARG_page_content));

            return rootView;
        }

        private void Recycle_design(View rootView) {

            // set up the  Hours RecyclerListView
            RecyclerView recyclerView = rootView.findViewById(R.id.Recycle_ViewList_hours);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            H_Adapter H_adapter = new H_Adapter(getActivity(), Simple_temp_list);
            recyclerView.setAdapter(H_adapter);
            ////////////

            // set up the Days RecyclerListView
            RecyclerView recyclerView2 = rootView.findViewById(R.id.Recycle_ViewList_days);
            recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            D_Adapter D_adapter = new D_Adapter(getActivity(), Simple_temp_list2);
            recyclerView2.setAdapter(D_adapter);

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
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        int number;
        String cities[];

        public SectionsPagerAdapter(FragmentManager fm, int number, String[] cities) {
            super(fm);
            this.number = number;
            this.cities = cities;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1, cities[position]);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return cities.length;
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
