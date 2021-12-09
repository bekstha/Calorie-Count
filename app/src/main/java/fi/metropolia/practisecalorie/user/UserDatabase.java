package fi.metropolia.practisecalorie.user;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import fi.metropolia.practisecalorie.data.Converters;
import fi.metropolia.practisecalorie.data.Food;
import fi.metropolia.practisecalorie.data.FoodDAO;

@Database(entities = {User.class, Food.class}, version = 1 ,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract FoodDAO foodDAO();

    private static UserDatabase userDatabase;

    public static synchronized UserDatabase getUserDatabase(Context context){

        if ( null == userDatabase) {
            userDatabase = Room.databaseBuilder(context, UserDatabase.class, "user.db").allowMainThreadQueries().build();
        }
        return userDatabase;
    }
}
