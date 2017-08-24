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

    @PrimaryKey(autoGenerate = true)
    private int id ;

    @ColumnInfo(name = "language")
    private String lang ;

    @ColumnInfo(name = "metric")
    private String metric ;

    public Settings_Model(int id, String lang, String metric) {
        this.id = id;
        this.lang = lang;
        this.metric = metric;
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

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

}
