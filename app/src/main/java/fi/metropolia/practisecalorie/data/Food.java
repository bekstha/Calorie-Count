package fi.metropolia.practisecalorie.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class Food {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private LocalDate day;
    private String foodName;
    private double kcalPerPortion;
    private double portion;
    private double totalKcalPerEntry;

    public Food(long id, LocalDate day, String foodName, double kcalPerPortion, double portion, double totalKcalPerEntry) {
        this.id = id;
        this.day = day;
        this.foodName = foodName;
        this.kcalPerPortion = kcalPerPortion;
        this.portion = portion;
        this.totalKcalPerEntry = totalKcalPerEntry;
    }


    public long getId() {
        return id;
    }

    public LocalDate getDay() {
        return day;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getKcalPerPortion() {
        return kcalPerPortion;
    }

    public double getPortion() {
        return portion;
    }

    public double getTotalKcalPerEntry() {
        return totalKcalPerEntry;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", day=" + day +
                ", foodName='" + foodName + '\'' +
                ", kcalPerPortion=" + kcalPerPortion +
                ", portion=" + portion +
                ", totalKcalPerEntry=" + totalKcalPerEntry +
                '}';
    }
}
