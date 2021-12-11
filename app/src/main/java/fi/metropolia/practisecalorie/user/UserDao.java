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
    double searchCalorieRequirement(int id);

    @Query("SELECT firstName FROM users WHERE id = :id")
    String searchFirstName(int id);

    @Query("SELECT lastName FROM users WHERE id = :id")
    String searchLastName(int id);

    @Query("SELECT userName FROM users WHERE id = :id")
    String searchUserName(int id);

    @Query("SELECT gender FROM users WHERE id = :id")
    String searchGender(int id);

    @Query("SELECT age FROM users WHERE id = :id")
    int age(int id);

    @Query("SELECT id FROM users WHERE id = :id")
    int id(int id);

    @Query("SELECT weight FROM users WHERE id = :id")
    int searchWeight(int id);

    @Query("SELECT height FROM users WHERE id = :id")
    int searchHeight(int id);

    @Query("SELECT password FROM users WHERE id = :id")
    String searchPassword(int id);



//    @Query("SELECT calorieRequirement FROM users WHERE userName = :userName")
//    User calorie(String userName);


}
