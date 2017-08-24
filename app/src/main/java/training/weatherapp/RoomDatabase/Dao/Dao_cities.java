package training.weatherapp.RoomDatabase.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import training.weatherapp.RoomDatabase.Models.Cities_Model;
import training.weatherapp.RoomDatabase.Models.Settings_Model;

/**
 * Created by hindahmed on 24/08/17.
 */

@Dao
public interface Dao_cities {

    @Query("SELECT * FROM cities_model")
    List<Cities_Model> getAll();

    @Insert
    void insertAll(Cities_Model... cities_model);

    @Delete
    void delete(Cities_Model cities_model);
}
