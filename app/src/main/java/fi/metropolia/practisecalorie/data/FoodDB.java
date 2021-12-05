package fi.metropolia.practisecalorie.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Food.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class FoodDB extends RoomDatabase {
    public abstract FoodDAO foodDAO();

}
