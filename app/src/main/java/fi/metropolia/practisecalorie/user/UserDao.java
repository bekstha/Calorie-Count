package fi.metropolia.practisecalorie.user;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fi.metropolia.practisecalorie.data.Food;

@Dao
public interface UserDao {

    @Insert
    void register(User user);

    @Update
    void update (User user);

    @Delete
    void  delete (User user);

    @Query("SELECT * FROM users WHERE userName = :userName and password = :password")
    User login(String userName, String password);

    @Query("SELECT * FROM users WHERE userName = :userName LIMIT 1")
    User checkUser(String userName);



    @Query("SELECT calorieRequirement FROM users WHERE id = :id")
    int searchCalorieRequirement(int id);

//    @Query("SELECT calorieRequirement FROM users WHERE userName = :userName")
//    User calorie(String userName);


}
