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
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

import training.weatherapp.R;


public class ActivitySettings extends AppCompatActivity {

    RadioGroup T_radioGroup, L_radioGroup;
    RadioButton Rd_auto, Rd_F, Rd_C, Rd_Eng, Rd_Ara;
    Button Cancel, Ok;
    Dialog T_dialog, L_dialog;
    TextView Temp_txt_sett, Lang_txt_sett;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Temp_txt_sett = (TextView) findViewById(R.id.temp_txtview_sett);
        Lang_txt_sett = (TextView) findViewById(R.id.lan_txtview_sett);

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
                    Temp_txt_sett.setText("Auto(ْ F)");
                    T_dialog.dismiss();
                } else if (checkedId == R.id.rd_c) {
                    Temp_txt_sett.setText("ْ c");
                    T_dialog.dismiss();

                } else if (checkedId == R.id.rd_f) {
                    Temp_txt_sett.setText("ْ F");
                    T_dialog.dismiss();


                }
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
                L_dialog.dismiss();
            }
        });

        L_radioGroup = L_dialog.findViewById(R.id.Lang_RadioGroup);

        Rd_Eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocal("en");
                Lang_txt_sett.setText("English");
                L_dialog.dismiss();
            }
        });
        Rd_Ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocal("ara");
                Lang_txt_sett.setText("Arabic");
                L_dialog.dismiss();

            }
        });

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
        finish();
    }

    public static boolean isRTL (View view)
    {
        if(view == null)
            return false;

        // config.getLayoutDirection() only available since 4.2
        // -> using ViewCompat instead (from Android support library)
        if (ViewCompat.LAYOUT_DIRECTION_LTR == ViewCompat.getLayoutDirection(view))
        {
            return true;
        }
        return false;
    }
//    public void changeLang(String lang) {
//        if (lang.equalsIgnoreCase(""))
//            return;
//        Locale myLocale = new Locale(lang);
//        saveLocale(lang);
//        Locale.setDefault(myLocale);
//        android.content.res.Configuration config = new android.content.res.Configuration();
//        config.locale = myLocale;
//        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//    }
//
//
//    // get current language
//    public void loadLocale() {
//        String langPref = "Language";
//        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
//        String language = prefs.getString(langPref, "ara");
//        changeLang(language);
//    }
//
//    // save changed language
//    private void saveLocale(String lang) {
//        {
//            String langPref = "Language";
//            SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putString(langPref, lang);
//            editor.commit();
//        }
//
//
//    }
//

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
}
