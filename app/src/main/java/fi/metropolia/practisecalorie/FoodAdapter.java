package fi.metropolia.practisecalorie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fi.metropolia.practisecalorie.data.Food;


/**
 * Food adapter holds the entry made by the user and shows it to the user in recycler view in the
 * overview activity
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {

    /**
     * list of foods
     */
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
        holder.tvPortion.setText(String.valueOf(currentFood.getPortion()));
        holder.tvTotalCalorieNum.setText(String.valueOf(currentFood.getTotalKcalPerEntry()));

        holder.cardView.setOnClickListener(v -> {
            String foodName = currentFood.getFoodName();
            double kcalPerPortion = currentFood.getKcalPerPortion();
            double portion = currentFood.getPortion();
            double totalCaloriePerEntry = currentFood.getTotalKcalPerEntry();

            Intent editIntent = new Intent(holder.cardView.getContext(), EditFood.class);
            editIntent.putExtra("foodName", foodName);
            editIntent.putExtra("kcalPerPortion", String.valueOf(kcalPerPortion));
            editIntent.putExtra("portion", String.valueOf(portion));
            editIntent.putExtra("totalCaloriePerEntry", String.valueOf(totalCaloriePerEntry));
            editIntent.putExtra("FoodId", currentFood.getId());
            //user can now click on each food item and edit the food
            holder.cardView.getContext().startActivity(editIntent);
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFoods(List<Food> foods){
        this.foods = foods;
        notifyDataSetChanged();
    }

    public  Food getFoodAt(int position){
        return foods.get(position);
    }

    static class FoodHolder extends RecyclerView.ViewHolder{
        private final TextView tvFoodName,tvCaloriePer100Gram,tvPortion,tvTotalCalorieNum;
        CardView cardView;

        /**
         * FoodHolder holds each food item with its name and caloric information
         * @param itemView holds the food item
         */
        public FoodHolder(View itemView){
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvCaloriePer100Gram = itemView.findViewById(R.id.tvCaloriePer100Gram);
            tvPortion = itemView.findViewById(R.id.tvPortion);
            tvTotalCalorieNum = itemView.findViewById(R.id.tvTotalCalorieNum);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
