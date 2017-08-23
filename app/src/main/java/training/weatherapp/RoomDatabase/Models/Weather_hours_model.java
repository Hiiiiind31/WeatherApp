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

    @ColumnInfo(name = "Date")
    private String Date ;

    @ColumnInfo(name = "temp")
    private String temp ;

    @ColumnInfo(name = "icon")
    private String Icon ;

    @ColumnInfo(name = "icon_phrase")
    private String IconPhrase;


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
