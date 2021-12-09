package fi.metropolia.practisecalorie.User;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static UserDatabase userDatabase;

    public static synchronized UserDatabase getUserDatabase(Context context){

        if ( null == userDatabase) {
            userDatabase = Room.databaseBuilder(context, UserDatabase.class, "user.db").allowMainThreadQueries().build();
        }
        return userDatabase;
    }
}
