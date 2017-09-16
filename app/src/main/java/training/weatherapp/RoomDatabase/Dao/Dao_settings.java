package training.weatherapp.RoomDatabase.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import training.weatherapp.RoomDatabase.Models.Settings_Model;

/**
 * Created by hindahmed on 24/08/17.
 */

@Dao
public interface Dao_settings    {

    @Query("SELECT * FROM settings_model")
    List<Settings_Model> getAll();

    @Insert
    void insertAll(Settings_Model... settings_model);

    @Delete
    void delete(Settings_Model... settings_model);

    @Update
    void update(Settings_Model... settings_model);
}
