package training.weatherapp.RoomDatabase.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import training.weatherapp.RoomDatabase.Models.Weather_hours_model;

/**
 * Created by hindahmed on 23/08/17.
 */

@Dao
public interface Dao_WHours {

    @Query("SELECT * FROM weather_hours_model")
    List<Weather_hours_model> getAll();

    @Insert
    void insertAll(Weather_hours_model... weather_hours_model);

    @Delete
    void delete(Weather_hours_model weather_hours_model);

    @Update
    void update(Weather_hours_model weather_hours_model);
}
