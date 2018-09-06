package br.com.rmso.cooking.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import java.util.List;

import br.com.rmso.cooking.R;
import br.com.rmso.cooking.models.Ingredient;
import br.com.rmso.cooking.models.Recipe;
import br.com.rmso.cooking.ui.activities.DetailRecipeActivity;

/**
 * Created by Raquel on 15/08/2018.
 */

public class RecipeWidgetProvider extends AppWidgetProvider {

    static SharedPreferences sharedPreferences;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int recipeId, String recipeName, String ingredients, int appWidgetId) {

        Intent intent = new Intent(context, DetailRecipeActivity.class);
        intent.putExtra(DetailRecipeActivity.EXTRA_RECIPE_ID, recipeId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.widget_name_recipe, recipeName);
        views.setTextViewText(R.id.widget_ingredients_recipe, ingredients);
        views.setOnClickPendingIntent(R.id.widget_ingredients_recipe, pendingIntent);

//        sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        String recipe = sharedPreferences.getString("recipe", null);
//
//        Intent recipeIntent = new Intent(context, RecipeWidgetService.class);
//        recipeIntent.setAction(RecipeWidgetService.ACTION_UPDATE_RECIPE_WIDGETS);
//        String ingredients = gson.toJson(ingredientList);
//        recipeIntent.putExtra("ingredientList", ingredients);
//        recipeIntent.putExtra("recipe", recipe);
//
//        PendingIntent recipePendingIntent = PendingIntent.getService(context, 0, recipeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        views.setOnClickPendingIntent(R.id.widget_name_recipe, recipePendingIntent);
//
//       if (ingredientList != null){
//           for (Ingredient ingredient : ingredientList){
//               RemoteViews ingredientView = new RemoteViews(context.getPackageName(), R.layout.item_recipe_ingredient);
//               ingredientView.setTextViewText(R.id.tv_quantity, String.valueOf(ingredient.getQuantity()));
//               ingredientView.setTextViewText(R.id.tv_measure, ingredient.getMeasure());
//               ingredientView.setTextViewText(R.id.tv_ingredient, ingredient.getName());
//
//               views.addView(R.id.widget_linear, ingredientView);
//           }
//       }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static void updateWidget (Context context, AppWidgetManager appWidgetManager, int recipeId, String recipeName, String ingredients, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds){
            updateAppWidget(context, appWidgetManager, recipeId, recipeName, ingredients, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RecipeWidgetService.startActionRecipe(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        super.onReceive(context, intent);
//        RecipeWidgetService.startActionRecipe(context);
//    }
}
