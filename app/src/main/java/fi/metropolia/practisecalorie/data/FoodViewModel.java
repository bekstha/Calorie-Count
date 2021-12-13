package fi.metropolia.practisecalorie.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.time.LocalDate;
import java.util.List;


public class FoodViewModel extends AndroidViewModel {
    private FoodRepository foodRepository;
    private LiveData<List<Food>> allFoods;
    private LiveData<List<Food>> foodsByDate;


    public FoodViewModel(@NonNull Application application) {
        super(application);
        foodRepository = new FoodRepository(application);
        allFoods = foodRepository.getAllFoods();
    }

    public void create(Food food) {
        foodRepository.create(food);
    }

    public void update (Food food) {
        foodRepository.update(food);
    }

    public  void delete (Food food) {
        foodRepository.delete(food);
    }

    public LiveData<List<Food>> getAllFoods(){
        return allFoods;
    }

    public LiveData<List<Food>> getFoodsByDate(LocalDate of) {
        return foodRepository.getFoodsByDate(of);
    }
}
