package br.com.rmso.cooking.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raquel on 02/08/2018.
 */

public class Recipe implements Parcelable{

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients;

    @SerializedName("steps")
    @Expose
    private List<Step> steps;

    @SerializedName("servings")
    @Expose
    private int servings;

    @SerializedName("image")
    @Expose
    private String image;

    public Recipe() {
    }

    public Recipe (Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.ingredients = new ArrayList<Ingredient>();
        in.readList(this.ingredients, Ingredient.class.getClassLoader());
        this.steps = new ArrayList<Step>();
        in.readList(this.steps, Step.class.getClassLoader());
        this.servings = in.readInt();
        this.image = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public String getIngredientsString() {
        StringBuilder ingredientsString = new StringBuilder();
        List<Ingredient> ingredientsList = this.ingredients;

        // Iterate through the Ingredient list to make a readable string of ingredients.
        for (int i = 0; i < ingredientsList.size(); i++) {
            // For the last ingredient in the list, end sentence with a period.
            if (i == ingredientsList.size() - 1) {
                ingredientsString.append(ingredientsList.get(i).getName()).append(".");
            } else {
                // Adding a comma and space after each ingredient item.
                ingredientsString.append(ingredientsList.get(i).getName()).append(", ");
            }
        }

        return ingredientsString.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeList(ingredients);
        parcel.writeList(steps);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }

    public static final Parcelable.Creator<Recipe>CREATOR = new Parcelable.Creator<Recipe>(){

        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int i) {
            return new Recipe[i];
        }
    };
}
