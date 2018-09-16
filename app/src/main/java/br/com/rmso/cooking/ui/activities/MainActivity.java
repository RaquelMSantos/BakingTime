package br.com.rmso.cooking.ui.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import br.com.rmso.cooking.R;
import br.com.rmso.cooking.models.Recipe;
import br.com.rmso.cooking.utils.RecipeClient;
import br.com.rmso.cooking.utils.Utility;
import br.com.rmso.cooking.ui.adapters.RecipeAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Raquel on 06/08/2018.
 */

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    private RecipeAdapter mRecipeAdapter;
    public static int positionList = 0;

    @BindView(R.id.rv_recipes)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Configuration configuration = getResources().getConfiguration();
        int smallestScreenWidthDp = configuration.smallestScreenWidthDp;

        if (smallestScreenWidthDp >= 600){
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            Utility.isTablet = true;
        }else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            Utility.isTablet = false;
        }

        mRecipeAdapter = new RecipeAdapter(getApplicationContext(), this);
        mRecyclerView.setAdapter(mRecipeAdapter);

        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(Utility.BUNDLE_STATE_RECIPE)){
                positionList = savedInstanceState.getInt(Utility.LIST_STATE);
                loadRecipeData();
            }
        }else {
            loadRecipeData();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Utility.BUNDLE_STATE_RECIPE, "stateRecipeBundle");
        outState.putInt(Utility.LIST_STATE, positionList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        positionList = ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
    }

    private void loadRecipeData() {
        showRecipeDataView();
        loadRecipeJson();
    }

    private void showRecipeDataView() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void loadRecipeJson(){
        Call<List<Recipe>> call = new RecipeClient().getRecipeService().getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Utility.recipeList = response.body();
                showRecipeDataView();
                mRecipeAdapter.setRecipeData(Utility.recipeList);
                mRecipeAdapter.notifyDataSetChanged();
                mRecyclerView.getLayoutManager().scrollToPosition(positionList);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("RecipeService ", "Erro ao buscar receitas:" + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(int itemClicked, Recipe recipeClicked) {
        Intent intent = new Intent(this, DetailRecipeActivity.class);
        intent.putExtra(Utility.RECIPE, recipeClicked);
        intent.putExtra(Utility.INDEX, itemClicked);
        Utility.currentRecipe = itemClicked;
        startActivity(intent);
    }
}
