package fi.metropolia.practisecalorie;

import android.annotation.SuppressLint;
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

public class HistoryAdapter extends RecyclerView.Adapter <HistoryAdapter.HistoryHolder>{

    /**
     * List of foods
     */
    private List<Food> foods = new ArrayList<>();


    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view_layout,parent,false);
        return new HistoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryHolder holder, int position) {

        Food currentFood = foods.get(position);
        holder.tvFoodName.setText(currentFood.getFoodName());
        holder.tvCaloriePer100Gram.setText(String.valueOf(currentFood.getKcalPerPortion()));
        holder.tvPortion.setText(String.valueOf(currentFood.getPortion()));
        holder.tvTotalCalorieNum.setText(String.valueOf(currentFood.getTotalKcalPerEntry()));
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

    static class HistoryHolder extends RecyclerView.ViewHolder{
        private final TextView tvFoodName,tvCaloriePer100Gram,tvPortion,tvTotalCalorieNum;
        CardView cardView;

        /**
         * View holds each food with food's name and caloric information
         * @param itemView is food item entered by the user
         */
        public HistoryHolder(View itemView){
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvCaloriePer100Gram = itemView.findViewById(R.id.tvCaloriePer100Gram);
            tvPortion = itemView.findViewById(R.id.tvPortion);
            tvTotalCalorieNum = itemView.findViewById(R.id.tvTotalCalorieNum);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

}
