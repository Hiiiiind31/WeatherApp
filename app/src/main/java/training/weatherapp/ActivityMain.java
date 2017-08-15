package training.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView settings_img = (ImageView) findViewById(R.id.Settings_img);
        ImageView cities_img = (ImageView) findViewById(R.id.Cities_img);
        settings_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityMain.this,ActivitySettings.class);
                startActivity(i);

            }
        });
        cities_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityMain.this,ActivityCities.class);
                startActivity(i);
            }
        });

        setSupportActionBar(myToolbar);

    }



}
