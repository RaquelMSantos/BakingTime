package br.com.rmso.cooking.ui.fragments;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.rmso.cooking.R;
import br.com.rmso.cooking.models.Ingredient;
import br.com.rmso.cooking.models.Recipe;
import br.com.rmso.cooking.models.Step;
import br.com.rmso.cooking.ui.activities.DetailRecipeActivity;
import br.com.rmso.cooking.ui.adapters.IngredientAdapter;
import br.com.rmso.cooking.ui.adapters.StepAdapter;
import br.com.rmso.cooking.ui.widget.RecipeWidgetService;
import br.com.rmso.cooking.utils.Utility;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Raquel on 07/09/2018.
 */

public class DetailRecipeFragment extends Fragment implements StepAdapter.StepAdapterOnClickHandler{

    private Recipe recipe;
    private IngredientAdapter mIngredientAdapter;
    private StepAdapter mStepAdapter;
    private StepClickListener clickListener;

    @BindView(R.id.tv_name_recipe_detail)
    TextView mNameRecipeTextView;
    @BindView(R.id.rv_ingredients)
    RecyclerView mIngredientsRecyclerView;
    @BindView(R.id.rv_steps)
    RecyclerView mStepsRecyclerView;

    public DetailRecipeFragment() {
    }

    public static DetailRecipeFragment newInstance(Recipe recipe) {
        DetailRecipeFragment fragment = new DetailRecipeFragment();
        Bundle args = new Bundle();
        args.putParcelable(Utility.RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(Utility.RECIPE);
        }else {
            recipe= getArguments().getParcelable(Utility.RECIPE);
        }

        Configuration configuration = getResources().getConfiguration();
        int smallestScreenWidthDp = configuration.smallestScreenWidthDp;
        if (smallestScreenWidthDp >= 600){
            Utility.isTablet = true;
        }else {
            Utility.isTablet = false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_recipe, container, false);
        ButterKnife.bind(this, view);

        if (recipe != null){
            mNameRecipeTextView.setText(recipe.getName());
            Utility.ingredientList = recipe.getIngredients();
            Utility.stepList = recipe.getSteps();
        }

        mIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mIngredientsRecyclerView.setHasFixedSize(true);

        mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mStepsRecyclerView.setHasFixedSize(true);

        mIngredientAdapter = new IngredientAdapter(Utility.ingredientList);
        mIngredientsRecyclerView.setAdapter(mIngredientAdapter);
        mStepAdapter = new StepAdapter(getContext(), this, Utility.stepList);
        mStepsRecyclerView.setAdapter(mStepAdapter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        Set<String> formattedIngredients = recipe.getIngredients().stream()
                .map(Ingredient::toString)
                .collect(Collectors.toSet());

        sharedPreferences.edit().putInt(Utility.PREF_KEY_LAST_RECIPE_ID, recipe.getId()).apply();
        sharedPreferences.edit().putString(Utility.PREF_KEY_LAST_RECIPE_NAME, recipe.getName()).apply();
        sharedPreferences.edit().putStringSet(Utility.PREF_KEY_LAST_RECIPE_INGREDIENTS, formattedIngredients).apply();

        RecipeWidgetService.startActionRecipe(getContext());

        return view;
    }

    public void setRecipe (Recipe recipe){
        this.recipe = recipe;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Utility.RECIPE, recipe);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            clickListener = (StepClickListener) getActivity();
        }catch (ClassCastException e){
            Log.w(getTag(), "Could not bind OnRecipeStepSelectedListener to fragment");
        }

    }

    @Override
    public void onClick(int itemClicked, Step stepClicked) {
        if (stepClicked == null) return;;
        clickListener.stepClicked(recipe, itemClicked);
        if (Utility.isTablet) mStepAdapter.setActiveIndex(itemClicked);
    }

    public interface StepClickListener {
        void stepClicked(Recipe recipe, int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            clickListener = (StepClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }
}
