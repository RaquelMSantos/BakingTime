package br.com.rmso.cooking.utilities;

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
}
