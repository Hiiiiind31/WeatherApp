package training.weatherapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class ActivitySettings extends AppCompatActivity {

     RadioGroup T_radioGroup , L_radioGroup;
     RadioButton Rd_auto, Rd_F, Rd_C, Rd_Eng, Rd_Ara;
     Button Cancel, Ok;
     Dialog T_dialog, L_dialog;
    TextView Temp_txt_sett ,Lang_txt_sett ;
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

                } else if(checkedId == R.id.rd_f) {
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

        L_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rd_eng) {
                    Lang_txt_sett.setText("English");
                    L_dialog.dismiss();

                } else if (checkedId == R.id.rd_ara) {
                    Lang_txt_sett.setText("Arabic");

                    L_dialog.dismiss();


                }
            }

        });


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
}
