package fi.metropolia.practisecalorie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fi.metropolia.practisecalorie.data.Food;

public class FoodAdapter  extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {

    private List<Food> foods = new ArrayList<>();

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view_layout,parent,false);
        return new FoodHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {

        Food currentFood = foods.get(position);
        holder.tvFoodName.setText(currentFood.getFoodName());
        holder.tvCaloriePer100Gram.setText(String.valueOf(currentFood.getKcalPerPortion()));
        holder.tvTotalCalorieNum.setText(String.valueOf(currentFood.getTotalKcalPerEntry()));

    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public void setFoods(List<Food> foods){
        this.foods = foods;
        notifyDataSetChanged();
    }

    class FoodHolder extends RecyclerView.ViewHolder{
        private TextView tvFoodName,tvCaloriePer100Gram,tvPortion,tvTotalCalorieNum;

        public FoodHolder(View itemView){
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvCaloriePer100Gram = itemView.findViewById(R.id.tvCaloriePer100Gram);
            tvTotalCalorieNum = itemView.findViewById(R.id.tvTotalCalorieNum);
        }

    }
}
