package br.com.rmso.cooking.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import br.com.rmso.cooking.R;
import br.com.rmso.cooking.models.Recipe;
import br.com.rmso.cooking.ui.activities.DetailRecipeActivity;

/**
 * Created by Raquel on 15/08/2018.
 */

public class RecipeWidgetProvider extends AppWidgetProvider {

    private static final String RECIPE_ID = "idRecipe";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Intent intent = new Intent(context, DetailRecipeActivity.class);
        Recipe recipe = WidgetHelper.getInstance().getCurrentRecipe();

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        if (recipe != null){
            views.setTextViewText(R.id.widget_name_recipe, recipe.getName());
            views.setTextViewText(R.id.widget_ingredients, recipe.getIngredientsString());

            intent.putExtra(RECIPE_ID, recipe.getId());
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_ingredients, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static void updateWidget (Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds){
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RecipeWidgetService.updateWidget(context);
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
}
