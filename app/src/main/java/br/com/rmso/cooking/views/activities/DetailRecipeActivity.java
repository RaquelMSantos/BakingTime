package br.com.rmso.cooking.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import br.com.rmso.cooking.R;
import br.com.rmso.cooking.models.Recipe;
import br.com.rmso.cooking.models.Step;
import br.com.rmso.cooking.utilities.Utility;
import br.com.rmso.cooking.views.adapters.IngredientAdapter;
import br.com.rmso.cooking.views.adapters.StepAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailRecipeActivity extends AppCompatActivity implements StepAdapter.StepAdapterOnClickHandler{

    public static final String EXTRA_RECIPE = "extraRecipe";

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
    }

    @Override
    public void onClick(int itemClicked, Step stepClicked) {
        Intent intent = new Intent(this, DetailStepActivity.class);
        intent.putExtra(DetailStepActivity.EXTRA_STEP, stepClicked);
        startActivity(intent);
    }
}
