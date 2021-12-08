package fi.metropolia.practisecalorie.User;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Insert
    void register(User user);

    @Update
    void update (User user);

    @Delete
    void  delete (User user);

}
