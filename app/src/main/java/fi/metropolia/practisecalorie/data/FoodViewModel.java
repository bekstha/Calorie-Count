package fi.metropolia.practisecalorie.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class FoodViewModel extends AndroidViewModel {
    private FoodRepository foodRepository;
    private LiveData<List<Food>> allFoods;

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
}
