package training.weatherapp.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

import training.weatherapp.PrefManager;
import training.weatherapp.R;
import training.weatherapp.RoomDatabase.Models.Settings_Model;

import static training.weatherapp.Activities.ActivityMain.db;
import static training.weatherapp.Activities.ActivityMain.main_max_min_temp;
import static training.weatherapp.Activities.ActivityMain.rootView;


public class ActivitySettings extends AppCompatActivity {

    RadioGroup T_radioGroup, L_radioGroup;
    RadioButton Rd_F, Rd_C, Rd_Eng, Rd_It, Rd_Fr, Rd_Tr;
    Button Cancel, Ok;
    Dialog T_dialog, L_dialog;
    TextView Temp_txt_sett, Lang_txt_sett;
    ImageView icon1, icon2, icon3;
    Toolbar toolbar;
    LinearLayout sett_layout;
    String language, metric, lang;


    public static boolean isRTL(View view) {
        if (view == null)
            return false;

        // config.getLayoutDirection() only available since 4.2
        // -> using ViewCompat instead (from Android support library)
        return ViewCompat.LAYOUT_DIRECTION_LTR == ViewCompat.getLayoutDirection(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        icon1 = (ImageView) findViewById(R.id.icon1);
        icon2 = (ImageView) findViewById(R.id.icon2);
        icon3 = (ImageView) findViewById(R.id.icon3);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sett_layout = (LinearLayout) findViewById(R.id.sett_layout);


        Temp_txt_sett = (TextView) findViewById(R.id.temp_txtview_sett);
        Lang_txt_sett = (TextView) findViewById(R.id.lan_txtview_sett);

        lang = db.settings_Dao().getAll().get(0).getLang();
        language = db.settings_Dao().getAll().get(0).getLanguage();
        metric = db.settings_Dao().getAll().get(0).getMetric1();


        switch (language) {
            case "English":
                Lang_txt_sett.setText(R.string.English);
                break;
            case "Italian":
                Lang_txt_sett.setText(R.string.Italian);
                break;
            case "French":
                Lang_txt_sett.setText(R.string.French);
                break;
            case "Turkish":
                Lang_txt_sett.setText(R.string.Turkish);
                break;
        }

        switch (metric) {

            case "metric":
                Temp_txt_sett.setText(R.string.Celsius);

                break;
            case "imperial":
                Temp_txt_sett.setText(R.string.Fahrenheit);

                break;
        }

        // Temp custom dialog
        T_dialog = new Dialog(this, R.style.FullHeightDialog);
        T_dialog.setContentView(R.layout.temp_custom_dialog);
        T_dialog.setCanceledOnTouchOutside(false);
        T_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Rd_F = T_dialog.findViewById(R.id.rd_f);
        Rd_C = T_dialog.findViewById(R.id.rd_c);
        Cancel = T_dialog.findViewById(R.id.cancel);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                T_dialog.dismiss();
            }
        });


        T_radioGroup = T_dialog.findViewById(R.id.Temp_RadioGroup);

        T_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rd_c) {
                    Temp_txt_sett.setText(R.string.Celsius);

                    db.settings_Dao().update(new Settings_Model(0, db.settings_Dao().getAll().get(0).getLang(), "metric", db.settings_Dao().getAll().get(0).getLanguage(), db.settings_Dao().getAll().get(0).isCurrent_location()));


                } else if (checkedId == R.id.rd_f) {
                    Temp_txt_sett.setText(R.string.Fahrenheit);
                    db.settings_Dao().update(new Settings_Model(0, db.settings_Dao().getAll().get(0).getLang(), "imperial", db.settings_Dao().getAll().get(0).getLanguage(), db.settings_Dao().getAll().get(0).isCurrent_location()));

                }
                T_dialog.dismiss();
                Intent intent = new Intent(ActivitySettings.this, ActivityMain.class);
                startActivity(intent);


            }

        });


        // Language custom dialog
        L_dialog = new Dialog(this, R.style.FullHeightDialog);
        L_dialog.setContentView(R.layout.lang_custom_dialog);
        L_dialog.setCanceledOnTouchOutside(false);
        L_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Rd_Eng = L_dialog.findViewById(R.id.rd_eng);
        Rd_Tr = L_dialog.findViewById(R.id.rd_tr);
        Rd_It = L_dialog.findViewById(R.id.rd_it);
        Rd_Fr = L_dialog.findViewById(R.id.rd_fr);
        Ok = L_dialog.findViewById(R.id.ok);
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Rd_Eng.isChecked()) {
                    lang = "en-us";
                    language = "English";
                    Rd_Eng.setChecked(true);

                } else if (Rd_Fr.isChecked()) {
                    lang = "fr";
                    language = "French";
                    Rd_Fr.setChecked(true);

                } else if (Rd_It.isChecked()) {
                    lang = "it";
                    language = "Italian";

                } else if (Rd_Tr.isChecked()) {
                    lang = "tr";
                    language = "Turkish";
                }
                db.settings_Dao().update(new Settings_Model(0, lang, db.settings_Dao().getAll().get(0).getMetric1(), language, db.settings_Dao().getAll().get(0).isCurrent_location()));
                setLocal(lang);
                L_dialog.dismiss();
            }
        });

        L_radioGroup = L_dialog.findViewById(R.id.Lang_RadioGroup);


        update_design();

    }

    private void setLocal(String language) {
        Locale myLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        conf.setLayoutDirection(myLocale);
        res.updateConfiguration(conf, dm);
        Intent intent = new Intent(this, ActivityMain.class);
        startActivity(intent);
    }

    //for arabic version
    private void update_design() {

        if (!isRTL(rootView)) {
            icon1.setRotation(180);
            icon2.setRotation(180);
            icon3.setRotation(180);
            sett_layout.setRotation(360);

        }
    }

    public void temp_units(View v) {
        T_dialog.show();
    }

    public void lang_sett(View v) {
        L_dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void back_img_click(View v) {
        Intent i = new Intent(ActivitySettings.this, ActivityMain.class);
        startActivity(i);
    }
}

