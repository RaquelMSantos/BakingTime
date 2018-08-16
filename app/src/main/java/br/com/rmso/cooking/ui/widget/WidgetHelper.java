package br.com.rmso.cooking.ui.widget;

import br.com.rmso.cooking.models.Recipe;

/**
 * Created by Raquel on 15/08/2018.
 */

public class WidgetHelper {
    private static WidgetHelper sInstance = null;
    private Recipe mCurrentRecipe;

    private WidgetHelper() {}

    public static WidgetHelper getInstance() {
        if (sInstance == null) {
            sInstance = new WidgetHelper();
        }

        return sInstance;
    }

    public void setCurrentRecipe(Recipe recipe) {
        mCurrentRecipe = recipe;
    }

    public Recipe getCurrentRecipe() {
        return mCurrentRecipe;
    }
}
