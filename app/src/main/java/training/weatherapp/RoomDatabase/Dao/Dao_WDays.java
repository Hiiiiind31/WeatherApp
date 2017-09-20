package training.weatherapp.RoomDatabase.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import training.weatherapp.RoomDatabase.Models.Weather_days_model;

/**
 * Created by hindahmed on 23/08/17.
 */

@Dao
public interface Dao_WDays {

    @Query("SELECT * FROM weather_days_model")
    List<Weather_days_model> getAll();

    @Insert
    void insertAll(Weather_days_model... weather_days_model);

    @Delete
    void delete(Weather_days_model weather_days_model);

}
