package training.weatherapp.RoomDatabase.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
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

    @ColumnInfo(name = "metric2")
    private String metric2;


    public Settings_Model(int id, String lang, String metric1, String language, String metric2) {
        this.id = id;
        this.lang = lang;
        this.metric1 = metric1;
        this.language = language;
        this.metric2 = metric2;
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

    public String getMetric2() {
        return metric2;
    }

    public void setMetric2(String metric2) {
        this.metric2 = metric2;
    }
}
