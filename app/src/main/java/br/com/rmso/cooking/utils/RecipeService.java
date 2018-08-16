package br.com.rmso.cooking.utils;

import java.util.List;

import br.com.rmso.cooking.models.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

 /**
 * Created by Raquel on 09/08/2018.
 */

public interface RecipeService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();

}
