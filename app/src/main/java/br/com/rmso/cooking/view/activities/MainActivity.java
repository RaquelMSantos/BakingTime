package br.com.rmso.cooking.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import br.com.rmso.cooking.R;
import br.com.rmso.cooking.view.adapters.RecipeAdapter;

/**
 * Created by Raquel on 06/08/2018.
 */

public class MainActivity extends AppCompatActivity{

    private RecipeAdapter mRecipeAdapter;
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
