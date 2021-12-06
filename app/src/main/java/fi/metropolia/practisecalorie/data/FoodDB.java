package fi.metropolia.practisecalorie.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Food.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class FoodDB extends RoomDatabase {
    public abstract FoodDAO foodDAO();

    private static FoodDB foodDB;

    public static synchronized FoodDB get(Context context){
        if (null == foodDB){
            foodDB = Room.databaseBuilder(context.getApplicationContext(),FoodDB.class, "food.db").allowMainThreadQueries().build();
        }
        return foodDB;
    }
}
