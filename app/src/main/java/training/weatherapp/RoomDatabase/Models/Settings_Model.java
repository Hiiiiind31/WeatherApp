package training.weatherapp.RoomDatabase.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

/**
 * Created by hindahmed on 24/08/17.
 */

@Entity
public class Settings_Model {

    @PrimaryKey(autoGenerate = false)
    private int id;

    @ColumnInfo(name = "lang")
    private String lang;

    @ColumnInfo(name = "metric1")
    private String metric1;

    @ColumnInfo(name = "language")
    private String language;


    @ColumnInfo(name = "current")
    private boolean current_location;


    public Settings_Model(int id, String lang, String metric1, String language, boolean current_location) {
        this.id = id;
        this.lang = lang;
        this.metric1 = metric1;
        this.language = language;
        this.current_location = current_location;
    }

    public boolean isCurrent_location() {
        return current_location;
    }

    public void setCurrent_location(boolean current_location) {
        this.current_location = current_location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getMetric1() {
        return metric1;
    }

    public void setMetric1(String metric1) {
        this.metric1 = metric1;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


}
