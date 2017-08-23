package training.weatherapp.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import training.weatherapp.RoomDatabase.Dao.Dao_WDays;
import training.weatherapp.RoomDatabase.Dao.Dao_WHours;
import training.weatherapp.RoomDatabase.Models.Weather_days_model;
import training.weatherapp.RoomDatabase.Models.Weather_hours_model;

/**
 * Created by hindahmed on 23/08/17.
 */
@Database(entities = {Weather_hours_model.class,Weather_days_model.class}, version = 1,exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    public abstract Dao_WDays WDays_Dao();

    public  abstract Dao_WHours WHours_Doa();


}
