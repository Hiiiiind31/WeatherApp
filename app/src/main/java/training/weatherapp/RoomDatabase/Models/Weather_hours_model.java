package training.weatherapp.RoomDatabase.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by hindahmed on 23/08/17.
 */

@Entity
public class Weather_hours_model {


    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "city_key")
    private String city_key;

    @ColumnInfo(name = "Date")
    private String Date ;

    @ColumnInfo(name = "temp")
    private String temp ;

    @ColumnInfo(name = "icon")
    private int Icon;

    @ColumnInfo(name = "icon_phrase")
    private String IconPhrase;

    public Weather_hours_model() {
    }

    public Weather_hours_model(String city_key, String date, String temp, int icon, String iconPhrase) {
        this.city_key = city_key;
        this.Date = date;
        this.temp = temp;
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

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }

    public String getIconPhrase() {
        return IconPhrase;
    }

    public void setIconPhrase(String iconPhrase) {
        IconPhrase = iconPhrase;
    }
}
