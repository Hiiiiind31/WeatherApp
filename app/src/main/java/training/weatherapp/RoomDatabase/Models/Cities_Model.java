package training.weatherapp.RoomDatabase.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

/**
 * Created by hindahmed on 24/08/17.
 */
@Entity
public class Cities_Model {

    @PrimaryKey (autoGenerate = true)
    private int id ;

    @ColumnInfo(name = "city")
    private String cites ;

    @ColumnInfo(name = "city_key")
    private String cities_keys;

    public Cities_Model(String cites, String cities_keys) {
        this.cites = cites;
        this.cities_keys = cities_keys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCites() {
        return cites;
    }

    public void setCites(String cites) {
        this.cites = cites;
    }

    public String getCities_keys() {
        return cities_keys;
    }

    public void setCities_keys(String cities_keys) {
        this.cities_keys = cities_keys;
    }
}
