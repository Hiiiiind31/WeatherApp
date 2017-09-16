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
    RadioButton Rd_auto, Rd_F, Rd_C, Rd_Eng, Rd_Ara;
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
        metric = db.settings_Dao().getAll().get(0).getMetric2();

        if (language.equals("Arabic")) {
            Lang_txt_sett.setText(R.string.Arabic);
        } else {
            Lang_txt_sett.setText(R.string.English);
        }

        switch (metric) {
            case "Auto (F)":
                Temp_txt_sett.setText(R.string.auto);
                break;
            case " F ":
                Temp_txt_sett.setText(R.string.F);
                break;
            case " C ":
                Temp_txt_sett.setText(R.string.C);
                break;
        }

        // Temp custom dialog
        T_dialog = new Dialog(this, R.style.FullHeightDialog);
        T_dialog.setContentView(R.layout.temp_custom_dialog);
        T_dialog.setCanceledOnTouchOutside(false);
        T_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Rd_auto = T_dialog.findViewById(R.id.rd_auto);
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

                if (checkedId == R.id.rd_auto) {
                    Temp_txt_sett.setText(R.string.auto);
                    db.settings_Dao().update(new Settings_Model(0, db.settings_Dao().getAll().get(0).getLang(), "false", db.settings_Dao().getAll().get(0).getLanguage(), "Auto (F)"));

                } else if (checkedId == R.id.rd_c) {
                    Temp_txt_sett.setText(R.string.C);

                    db.settings_Dao().update(new Settings_Model(0, db.settings_Dao().getAll().get(0).getLang(), "true", db.settings_Dao().getAll().get(0).getLanguage(), " C "));


                } else if (checkedId == R.id.rd_f) {
                    Temp_txt_sett.setText(R.string.F);
                    db.settings_Dao().update(new Settings_Model(0, db.settings_Dao().getAll().get(0).getLang(), "false", db.settings_Dao().getAll().get(0).getLanguage(), " F "));
                    Log.d("langn", db.settings_Dao().getAll().get(0).getLang() + db.settings_Dao().getAll().get(0).getMetric1() + db.settings_Dao().getAll().get(0).getMetric2());

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
        Rd_Ara = L_dialog.findViewById(R.id.rd_ara);
        Ok = L_dialog.findViewById(R.id.ok);
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Rd_Eng.isChecked()) {
                    lang = "en-us";
                    language = "English";
                    db.settings_Dao().update(new Settings_Model(0, lang, db.settings_Dao().getAll().get(0).getMetric1(), language, db.settings_Dao().getAll().get(0).getMetric2()));
                    setLocal(lang);
                    L_dialog.dismiss();
                } else if (Rd_Ara.isChecked()) {
                    lang = "ar";
                    language = "Arabic";
                    db.settings_Dao().update(new Settings_Model(0, lang, db.settings_Dao().getAll().get(0).getMetric1(), language, db.settings_Dao().getAll().get(0).getMetric2()));
                    setLocal(lang);
                    L_dialog.dismiss();
                }
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

