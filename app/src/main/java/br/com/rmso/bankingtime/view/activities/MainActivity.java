package br.com.rmso.bankingtime.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.GridView;

import br.com.rmso.bankingtime.R;
import br.com.rmso.bankingtime.view.adapters.RecipeAdapter;

public class MainActivity extends AppCompatActivity {

    private RecipeAdapter mRecipeAdapter;
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
