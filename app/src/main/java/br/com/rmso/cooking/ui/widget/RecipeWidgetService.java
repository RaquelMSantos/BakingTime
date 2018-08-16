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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import br.com.rmso.cooking.R;

/**
 * Created by Raquel on 15/08/2018.
 */

public class RecipeWidgetService extends IntentService{

    public static final String ACTION_UPDATE_RECIPE_WIDGETS = "br.com.rmso.cooking.action.update_recipe_widgets";

    public RecipeWidgetService(){
        super("RecipeWidgetService");
    }

    public static void updateWidget(Context context) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_UPDATE_RECIPE_WIDGETS);
        context.startService(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_RECIPE_WIDGETS.equals(action)) {
                handleActionUpdateRecipeWidgets();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void handleActionUpdateRecipeWidgets() {
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));

        RecipeWidgetProvider.updateWidget(this, appWidgetManager, appWidgetIds);
    }
}
