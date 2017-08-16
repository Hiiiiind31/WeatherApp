package training.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ActivityCities extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(myToolbar);

    }

    public void add_city(View v) {
        this.setContentView(R.layout.add_cities_layout);
    }

    public void back_img_click(View v) {
        this.setContentView(R.layout.activity_cities);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
