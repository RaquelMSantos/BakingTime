package br.com.rmso.cooking.views.activities;

import android.app.LoaderManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;

import br.com.rmso.cooking.R;
import br.com.rmso.cooking.views.adapters.RecipeAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Raquel on 06/08/2018.
 */

public class MainActivity extends AppCompatActivity {

    private RecipeAdapter mRecipeAdapter;
    private GridView mGridView;
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

        mRecipeAdapter = new RecipeAdapter(this);
        mRecyclerView.setAdapter(mRecipeAdapter);

        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(BUNDLE_STATE_RECIPE)){
                positionList = savedInstanceState.getInt(LIST_STATE);
            }
        }else {
            loadRecipeData();
        }
    }

    private void loadRecipeData() {
        showRecipeDataView();
        new FetchRecipeTask().execute();
    }

    private void showRecipeDataView() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public class FetchRecipeTask extends AsyncTask<ArrayList, Void, ArrayList> {

        @Override
        protected ArrayList doInBackground(ArrayList... arrayLists) {

            return null;
        }
    }
}
