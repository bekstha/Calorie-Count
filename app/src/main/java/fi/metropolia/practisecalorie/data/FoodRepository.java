package fi.metropolia.practisecalorie.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fi.metropolia.practisecalorie.user.LoggedUser;
import fi.metropolia.practisecalorie.user.UserDatabase;

public class FoodRepository {
    private FoodDAO foodDAO;
    private LiveData<List<Food>> allFoods;
    private Double total;

    //try to sort food by user and date
    private LiveData<List<Food>> foodsByDate;


    private LocalDate day;

    public FoodRepository(Application application){
        UserDatabase userDatabase = UserDatabase.getUserDatabase(application);
        foodDAO = userDatabase.foodDAO();
        allFoods = foodDAO.getByDay(LocalDate.now(), LoggedUser.getUserID());
        total = foodDAO.getTotal(LocalDate.now(), LoggedUser.getUserID());

        foodsByDate = foodDAO.getByDay(day, LoggedUser.getUserID());

    }

    public void create (Food food) {
        ExecutorService createService = Executors.newSingleThreadExecutor();
        createService.execute(new Runnable() {
            @Override
            public void run() {
                    foodDAO.create(food);
            }
        });
        }

    public void update (Food food){
        ExecutorService updateService = Executors.newSingleThreadExecutor();
        updateService.execute(new Runnable() {
            @Override
            public void run() {
                foodDAO.update(food);
            }
        });
    }

    public void delete (Food food){
        ExecutorService deleteService = Executors.newSingleThreadExecutor();
        deleteService.execute(new Runnable() {
            @Override
            public void run() {
                foodDAO.delete(food);
            }
        });
    }

    public double total(){
      return total;
    }

    public void setDay(LocalDate day){
        this.day = day;
    }


    public LiveData<List<Food>> getFoodsByDate(LocalDate day) {
        this.day = day;
        foodsByDate = foodDAO.getByDay(day,LoggedUser.getUserID());
        return foodsByDate;
    }

    //try to sort food by user and today
    public LiveData<List<Food>> getAllFoods(){
        return allFoods;
    }



}
