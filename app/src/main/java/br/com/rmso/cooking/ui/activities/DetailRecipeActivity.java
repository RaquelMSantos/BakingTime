package br.com.rmso.cooking.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.android.exoplayer2.util.Util;

import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import br.com.rmso.cooking.R;
import br.com.rmso.cooking.models.Ingredient;
import br.com.rmso.cooking.models.Recipe;
import br.com.rmso.cooking.models.Step;
import br.com.rmso.cooking.ui.widget.RecipeWidgetService;
import br.com.rmso.cooking.utils.Utility;
import br.com.rmso.cooking.ui.adapters.IngredientAdapter;
import br.com.rmso.cooking.ui.adapters.StepAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailRecipeActivity extends AppCompatActivity implements StepAdapter.StepAdapterOnClickHandler{

    public static final String EXTRA_RECIPE = "extraRecipe";
    public static final String EXTRA_RECIPE_ID = "extraRecipeId";

    private Recipe recipe;
    private IngredientAdapter mIngredientAdapter;
    private StepAdapter mStepAdapter;

    @BindView(R.id.tv_name_recipe_detail)
    TextView mNameRecipeTextView;

    @BindView(R.id.rv_ingredients)
    RecyclerView mIngredientsRecyclerView;

    @BindView(R.id.rv_steps)
    RecyclerView mStepsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);
        ButterKnife.bind(this);

        recipe = getIntent().getExtras().getParcelable(EXTRA_RECIPE);
        if (recipe != null){
            mNameRecipeTextView.setText(recipe.getName());
            Utility.ingredientList = recipe.getIngredients();
            Utility.stepList = recipe.getSteps();
        }

        mIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mIngredientsRecyclerView.setHasFixedSize(true);

        mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mStepsRecyclerView.setHasFixedSize(true);

        mIngredientAdapter = new IngredientAdapter(Utility.ingredientList);
        mIngredientsRecyclerView.setAdapter(mIngredientAdapter);
        mStepAdapter = new StepAdapter(getApplicationContext(), this, Utility.stepList);
        mStepsRecyclerView.setAdapter(mStepAdapter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Set<String> formattedIngredients = recipe.getIngredients().stream()
                .map(Ingredient::toString)
                .collect(Collectors.toSet());

        sharedPreferences.edit().putInt(Utility.PREF_KEY_LAST_RECIPE_ID, recipe.getId()).apply();
        sharedPreferences.edit().putString(Utility.PREF_KEY_LAST_RECIPE_NAME, recipe.getName()).apply();
        sharedPreferences.edit().putStringSet(Utility.PREF_KEY_LAST_RECIPE_INGREDIENTS, formattedIngredients).apply();


        RecipeWidgetService.startActionRecipe(this);
    }

    @Override
    public void onClick(int itemClicked, Step stepClicked) {
        Intent intent = new Intent(this, DetailStepActivity.class);
        intent.putExtra(DetailStepActivity.EXTRA_STEP, stepClicked);
        startActivity(intent);
    }
}
