package training.weatherapp.RoomDatabase.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by hindahmed on 23/08/17.
 */


@Entity
public class Weather_days_model {


    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "city_key")
    private String city_key;

    @ColumnInfo(name = "date")
    private String Date;

    @ColumnInfo(name = "max_temp")
    private String Max_temp;

    @ColumnInfo(name = "min_temp")
    private String Min_temp;

    @ColumnInfo(name = "icon")
    private String Icon;

    @ColumnInfo(name = "icon_phrase")
    private String IconPhrase;


    public Weather_days_model() {
    }

    public Weather_days_model( String city_key, String date, String max_temp, String min_temp, String icon, String iconPhrase) {
        this.city_key = city_key;
        this.Date = date;
        this.Max_temp = max_temp;
        this.Min_temp = min_temp;
        this.Icon = icon;
        this.IconPhrase = iconPhrase;
    }

    public String getCity_key() {
        return city_key;
    }

    public void setCity_key(String city_key) {
        this.city_key = city_key;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMax_temp() {
        return Max_temp;
    }

    public void setMax_temp(String max_temp) {
        Max_temp = max_temp;
    }

    public String getMin_temp() {
        return Min_temp;
    }

    public void setMin_temp(String min_temp) {
        Min_temp = min_temp;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getIconPhrase() {
        return IconPhrase;
    }

    public void setIconPhrase(String iconPhrase) {
        IconPhrase = iconPhrase;
    }
}
