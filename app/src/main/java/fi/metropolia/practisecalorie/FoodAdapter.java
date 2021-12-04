package fi.metropolia.practisecalorie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodVH>{

    List<String> foodItems;

    public FoodAdapter(List<String> foodItems) {
        this.foodItems = foodItems;
    }

    @NonNull
    @Override
    public FoodVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fooditems, parent,false);
        return new FoodVH(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodVH holder, int position) {
        holder.etFoodName.setText(foodItems.get(position));
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }
}

class FoodVH extends RecyclerView.ViewHolder{

    TextView etFoodName;
    private FoodAdapter adapter;

    public FoodVH(@NonNull View itemView) {
        super(itemView);

        etFoodName = itemView.findViewById(R.id.etFoodName);

        itemView.findViewById(R.id.foodDeleteBtn).setOnClickListener(v -> {
            adapter.foodItems.remove(getAdapterPosition());
            adapter.notifyItemRemoved(getAdapterPosition());

        });
    }

    public FoodVH linkAdapter(FoodAdapter adapter){
        this.adapter = adapter;
        return this;
    }
}
