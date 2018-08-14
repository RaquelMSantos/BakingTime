package br.com.rmso.cooking.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.rmso.cooking.models.Recipe;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.gson.FieldNamingPolicy.IDENTITY;

/**
 * Created by Raquel on 09/08/2018.
 */

public class RecipeClient {

    static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private static Retrofit retrofit;

    public RecipeClient() {
        if (retrofit == null){
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(IDENTITY)
                    .create();

            this.retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
    }

    public RecipeService getRecipeService(){
        return this.retrofit.create(RecipeService.class);
    }
}
