package fi.metropolia.practisecalorie.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodDAO {

    @Insert
    long create(Food food);

    @Update
    void update(Food food);

    @Delete
    void delete (Food food);

    @Query("DELETE FROM food")
    void deleteALlEntry();

    @Query("Select * FROM food")
    LiveData<List<Food>>getAll();

    @Query("Select * FROM food ORDER BY day")
    LiveData<List<Food>> getAllByDate();

    @Query("Select * FROM food WHERE id = :foodId")
    Food getById(long foodId);
}
