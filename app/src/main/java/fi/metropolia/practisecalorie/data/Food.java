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
    private double kcalPer100gm;
    private double portion;
    private double totalCaloriePerEntry;

    public Food(long id, LocalDate day, String foodName, double kcalPer100gm, double portion, double totalCaloriePerEntry) {
        this.id = id;
        this.day = day;
        this.foodName = foodName;
        this.kcalPer100gm = kcalPer100gm;
        this.portion = portion;
        this.totalCaloriePerEntry = totalCaloriePerEntry;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getKcalPer100gm() {
        return kcalPer100gm;
    }

    public void setKcalPer100gm(double kcalPer100gm) {
        this.kcalPer100gm = kcalPer100gm;
    }

    public double getPortion() {
        return portion;
    }

    public void setPortion(double portion) {
        this.portion = portion;
    }

    public double getTotalCaloriePerEntry() {
        return totalCaloriePerEntry;
    }

    public void setTotalCaloriePerEntry(double totalCaloriePerEntry) {
        this.totalCaloriePerEntry = totalCaloriePerEntry;
    }


    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", day=" + day +
                ", foodName='" + foodName + '\'' +
                ", kcalPer100gm=" + kcalPer100gm +
                ", portion=" + portion +
                ", totalCaloriePerEntry=" + totalCaloriePerEntry +
                '}';
    }
}
