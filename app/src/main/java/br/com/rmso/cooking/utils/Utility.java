package br.com.rmso.cooking.utils;

import java.util.ArrayList;
import java.util.List;

import br.com.rmso.cooking.models.Ingredient;
import br.com.rmso.cooking.models.Recipe;
import br.com.rmso.cooking.models.Step;

/**
 * Created by Raquel on 14/08/2018.
 */

public final class Utility {

    public static List<Recipe> recipeList = new ArrayList<>();
    public static List<Ingredient> ingredientList = new ArrayList<>();
    public static List<Step> stepList = new ArrayList<>();
    public static int currentRecipe = 0;

    public static boolean isTablet = false;

    public static final String PREF_KEY_LAST_RECIPE_ID = "last_recipe_id";
    public static final String PREF_KEY_LAST_RECIPE_NAME = "last_recipe_name";
    public static final String PREF_KEY_LAST_RECIPE_INGREDIENTS = "last_recipe_ingredients";
    public static final String BUNDLE_STATE_RECIPE = "stateRecipeBundle";
    public static final String LIST_STATE = "list_state";
    public static final String KEY_PLAYER_POSITION = "player_position";
    public static final String KEY_PLAYER_PLAY_STATE = "player_play_state";
    public static final String RECIPE = "recipe";
    public static final String RECIPE_ID = "recipe_id";
    public static final String INDEX = "index";
}
