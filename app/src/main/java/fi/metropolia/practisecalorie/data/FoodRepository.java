package fi.metropolia.practisecalorie.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FoodRepository {
    private FoodDAO foodDAO;
    private LiveData<List<Food>> allFoods;

    public FoodRepository(Application application){
        FoodDB foodDB = FoodDB.get(application);
        foodDAO = foodDB.foodDAO();
        allFoods = foodDAO.getAll();
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

    public LiveData<List<Food>> getAllFoods(){
        return allFoods;
    }


}
