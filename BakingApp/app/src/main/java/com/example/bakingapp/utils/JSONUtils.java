package com.example.bakingapp.utils;



import com.example.bakingapp.Models.RecipeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class JSONUtils {

    public static final String KEY_NAME = "name";
    public static final String KEY_SERVINGS = "servings";
    public static final String KEY_IMAGE = "image";

    public static final String KEY_INGREDIENTS_ARRAY = "ingredients";
    public static final String KEY_QUANTITY = "quantity";
    public static final String KEY_MEASURE = "measure";
    public static final String KEY_INGREDIENT = "ingredient";

    public static final String KEY_STEPS = "steps";
    public static final String KEY_SHORT_DEC = "shortDescription";
    public static final String KEY_DEC = "description";
    public static final String KEY_VIDEO = "videoURL";
    public static final String KEY_THUMBNAIL = "thumbnailURL";

    public static List<RecipeModel> getRecipe(String searchResults){

        List<RecipeModel>  recipeList = new ArrayList<>();
        try {

            JSONArray jsonResults = new JSONArray(searchResults);



            for (int i=0; i<jsonResults.length(); i++){
                RecipeModel recipeObj = new RecipeModel();
                List<String> measure = new ArrayList<>();
                List<String> quantity = new ArrayList<>();
                List<String> ingredient = new ArrayList<>();

                JSONObject recipeIndex = jsonResults.getJSONObject(i);
                recipeObj.setName( recipeIndex.optString(KEY_NAME));
                recipeObj.setServings( recipeIndex.optInt(KEY_SERVINGS,-1));

                JSONArray ingredients= recipeIndex.getJSONArray(KEY_INGREDIENTS_ARRAY);
                 for (int j=0; j<ingredients.length(); j++){
                     JSONObject ingredientsIndex = ingredients.getJSONObject(j);

                     measure.add(ingredientsIndex.optString(KEY_MEASURE));
                     String s = ingredientsIndex.optInt(KEY_QUANTITY,-1)+"";
                     quantity.add(s);
                     //quantity.add(ingredientsIndex.optInt(KEY_QUANTITY,-1)+"");
                     ingredient.add(ingredientsIndex.optString(KEY_INGREDIENT));
//                     System.out.println(recipeObj.getName()+"==============="+ ingredientsIndex.optInt(KEY_QUANTITY));
//                     System.out.println("================="+ quantity.get(i));
                 }
                 recipeObj.setMeasure(measure);
                 recipeObj.setQuantity(quantity);


                 recipeObj.setIngredient(ingredient);

                JSONArray steps = recipeIndex.getJSONArray(KEY_STEPS);
                for (int j=0; j<steps.length(); j++){
                    JSONObject ingredientsIndex = steps.getJSONObject(j);
                    recipeObj.addShortDescription(ingredientsIndex.optString(KEY_SHORT_DEC));
                    recipeObj.addDescription(ingredientsIndex.optString(KEY_DEC));
                    recipeObj.addVideoURL(ingredientsIndex.optString(KEY_VIDEO));
                    recipeObj.addThumbnailURL(ingredientsIndex.optString(KEY_THUMBNAIL));
                }
                recipeList.add(recipeObj);
            }
        }  catch (JSONException e) {
            e.printStackTrace();
        }

    return recipeList;
    }

}
