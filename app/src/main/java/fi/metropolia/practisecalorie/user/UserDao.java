package fi.metropolia.practisecalorie.user;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

//    @Query("SELECT calorieRequirement FROM users WHERE userName = :userName")
//    User calorie(String userName);


}
