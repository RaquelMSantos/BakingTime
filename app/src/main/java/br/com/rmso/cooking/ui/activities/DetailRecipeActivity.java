package br.com.rmso.cooking.ui.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.rmso.cooking.R;
import br.com.rmso.cooking.models.Recipe;
import br.com.rmso.cooking.ui.fragments.DetailRecipeFragment;
import br.com.rmso.cooking.ui.fragments.DetailStepFragment;
import br.com.rmso.cooking.utils.Utility;
import butterknife.ButterKnife;

public class DetailRecipeActivity extends AppCompatActivity implements DetailRecipeFragment.StepClickListener{

    private int index = 0;
    private Recipe recipe;
    private FragmentManager fragmentManager;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            bundle = getIntent().getExtras();
            if (bundle.containsKey(Utility.RECIPE)) {
                recipe = bundle.getParcelable(Utility.RECIPE);
            }

            fragmentManager = getSupportFragmentManager();

            DetailRecipeFragment detailRecipeFragment = new DetailRecipeFragment();
            detailRecipeFragment.setArguments(bundle);
            detailRecipeFragment.setRecipe(recipe);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, detailRecipeFragment)
                    .commit();

            if (findViewById(R.id.mode_tablet) != null){
                Utility.isTablet = true;
                showStep(recipe, index);
            }else {
                Utility.isTablet = false;
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recipe != null){
            outState.putParcelable(Utility.RECIPE, recipe);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recipe = savedInstanceState.getParcelable(Utility.RECIPE);
    }

    private void showStep (Recipe recipe, int position){
        if (Utility.isTablet){
            index = position;
        }

        DetailStepFragment detailStepFragment = DetailStepFragment.newInstance(recipe, position);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.step_container, detailStepFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void stepClicked(Recipe recipe, int position) {
        if (!Utility.isTablet){
            Intent intent = new Intent(this, DetailStepActivity.class);
            intent.putExtra(Utility.INDEX, position);
            intent.putExtra(Utility.RECIPE, recipe);
            startActivity(intent);
        }else {
            showStep (recipe, position);
        }
    }
}
