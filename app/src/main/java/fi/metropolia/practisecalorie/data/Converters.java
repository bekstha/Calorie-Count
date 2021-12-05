package fi.metropolia.practisecalorie.data;

import androidx.room.TypeConverter;

import java.time.LocalDate;

public class Converters {

    @TypeConverter
    public static LocalDate fromString(String string){
        return null == string ? null : LocalDate.parse(string);
    }

    @TypeConverter
    public static String localDateToString (LocalDate localDate) {
        return null == localDate ? null : localDate.toString();
    }
}
