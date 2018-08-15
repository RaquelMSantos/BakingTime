package br.com.rmso.cooking.views.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.rmso.cooking.R;
import br.com.rmso.cooking.models.Ingredient;
import br.com.rmso.cooking.models.Recipe;

/**
 * Created by Raquel on 14/08/2018.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredientList;

    public IngredientAdapter(List<Ingredient> mIngredientList) {
        ingredientList = mIngredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_ingredient, parent, false);
        return new IngredientAdapter.IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        final Ingredient ingredient = ingredientList.get(position);
        holder.mQuantityTextView.setText(ingredient.getQuantity() + "");
        holder.mMeasureTextView.setText(ingredient.getMeasure());
        holder.mIngredientTextView.setText(ingredient.getName());
    }

    @Override
    public int getItemCount() {
        if (ingredientList !=null) return ingredientList.size(); else return 0;
    }

    public void setIngredient(ArrayList<Ingredient> ingredientList) {
        if (ingredientList != null){
            this.ingredientList = ingredientList;
            notifyDataSetChanged();
        }
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        public final TextView mQuantityTextView;
        public final TextView mMeasureTextView;
        public final TextView mIngredientTextView;

        public IngredientViewHolder (View view){
            super(view);
            mQuantityTextView = itemView.findViewById(R.id.tv_quantity);
            mMeasureTextView = itemView.findViewById(R.id.tv_measure);
            mIngredientTextView = itemView.findViewById(R.id.tv_ingredient);
        }
    }
}
