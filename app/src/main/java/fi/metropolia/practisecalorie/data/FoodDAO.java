package fi.metropolia.practisecalorie.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.time.LocalDate;
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

    @Query("SELECT SUM(totalKcalPerEntry) FROM food WHERE day = :day AND userID = :userID")
    double getTotal(LocalDate day, int userID);

    @Query("SELECT * FROM food WHERE day = :day AND userID = :userID")
    LiveData<List<Food>> getByDay(LocalDate day, int userID);

    @Query("DELETE  FROM food WHERE userID = :userID")
    void deleteFoodALso(int userID);

    @Transaction
    @Query("SELECT * FROM users WHERE id = :id")
    UserFood getUserFood(int id);
}
