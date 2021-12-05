package fi.metropolia.practisecalorie.data;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodDAO {

    @Insert
    long create(Food food);

    @Query("SELECT* FROM food")
    List<Food> getAll();

    @Query("SELECT * FROM food ORDER BY day")
    List<Food>getAllByDate();

    @Query("SELECT * FROM food WHERE id = :foodId ")
    Food getById(long foodId);


}
