package br.com.rmso.cooking.views.activities;

import android.app.LoaderManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.rmso.cooking.R;
import br.com.rmso.cooking.models.Recipe;
import br.com.rmso.cooking.utilities.RecipeClient;
import br.com.rmso.cooking.utilities.Utility;
import br.com.rmso.cooking.views.adapters.RecipeAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Raquel on 06/08/2018.
 */

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    private RecipeAdapter mRecipeAdapter;
    private LinearLayoutManager layoutManager;
    public static int positionList = 0;
    private static final String BUNDLE_STATE_RECIPE = "stateRecipeBundle";
    public static final String LIST_STATE = "list_state";

    @BindView(R.id.rv_recipes)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecipeAdapter = new RecipeAdapter(getApplicationContext(), this);
        mRecyclerView.setAdapter(mRecipeAdapter);

        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(BUNDLE_STATE_RECIPE)){
                positionList = savedInstanceState.getInt(LIST_STATE);
                loadRecipeData();
            }
        }else {
            loadRecipeData();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(BUNDLE_STATE_RECIPE, "stateRecipeBundle");
        outState.putInt(LIST_STATE, positionList);
        super.onSaveInstanceState(outState);
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
        intent.putExtra(DetailRecipeActivity.EXTRA_RECIPE, recipeClicked);
        Utility.currentRecipe = itemClicked;
        startActivity(intent);
    }
}
