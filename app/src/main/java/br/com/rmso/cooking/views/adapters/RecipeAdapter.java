package br.com.rmso.cooking.views.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.rmso.cooking.R;
import br.com.rmso.cooking.models.Recipe;

/**
 * Created by Raquel on 02/08/2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipeList;
    private final Context mContext;
    private final RecipeAdapterOnClickHandler mClickHandler;

    public RecipeAdapter (Context context, RecipeAdapterOnClickHandler clickHandler){
        mContext = context;
        mClickHandler = clickHandler;
    }

    public interface RecipeAdapterOnClickHandler {
        void onClick(int itemClicked, Recipe recipeClicked);
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        final Recipe recipe = mRecipeList.get(position);
        holder.nameRecipeTextView.setText(recipe.getName());
        if (!recipe.getImage().equals("")){
            Picasso.with(mContext)
                    .load(recipe.getImage())
                    .placeholder(R.drawable.placeholder_image).centerCrop()
                    .error(R.drawable.placeholder_image_error)
                    .into(holder.photoRecipeImageView);
        }else {
            holder.photoRecipeImageView.setImageResource(R.drawable.placeholder_image);
            holder.nameRecipeTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        }
    }

    @Override
    public int getItemCount() {
        if (mRecipeList != null) return  mRecipeList.size(); else  return 0;
    }

    public List<Recipe> getRecipe(){
        return mRecipeList;
    }

    public void setRecipe (List<Recipe> recipeList){
        mRecipeList = recipeList;
        notifyDataSetChanged();
    }

    public void setRecipeData(List RecipeData){
        mRecipeList = RecipeData;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView photoRecipeImageView;
        public final TextView nameRecipeTextView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            photoRecipeImageView = itemView.findViewById(R.id.img_recipe);
            nameRecipeTextView = itemView.findViewById(R.id.tv_name_recipe);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int itemPosition = getAdapterPosition();
            Recipe recipeClicked = mRecipeList.get(itemPosition);
            mClickHandler.onClick(itemPosition, recipeClicked);
        }
    }
}
