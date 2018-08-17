package br.com.rmso.cooking.ui.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import br.com.rmso.cooking.R;
import br.com.rmso.cooking.models.Ingredient;

/**
 * Created by Raquel on 15/08/2018.
 */

public class RecipeWidgetService extends IntentService{

    public static final String ACTION_UPDATE_RECIPE_WIDGETS = "br.com.rmso.cooking.action.update_recipe_widgets";
    static SharedPreferences sharedPreferences;

    public RecipeWidgetService(){
        super("RecipeWidgetService");
    }

    public static void startActionRecipe(Context context) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_UPDATE_RECIPE_WIDGETS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_RECIPE_WIDGETS.equals(action)) {
                handleActionUpdateRecipeWidgets();
            }
        }
    }

    private void handleActionUpdateRecipeWidgets() {
        Context context = getApplicationContext();
        List<Ingredient> ingredientList;
        sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String result = sharedPreferences.getString("recipe_ingredients", null);
        Ingredient[] arrayIngredient = gson.fromJson(result, Ingredient[].class);
        ingredientList = Arrays.asList(arrayIngredient);
        ingredientList = new ArrayList<>(ingredientList);
        String recipeName = sharedPreferences.getString("recipe_name", null);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));

        RecipeWidgetProvider.updateWidget(this, appWidgetManager, recipeName, ingredientList, appWidgetIds);
    }
}
