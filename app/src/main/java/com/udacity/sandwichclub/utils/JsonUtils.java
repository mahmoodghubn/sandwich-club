package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich;
        String mainName;
        ArrayList<String> alsoKnownAsArray;
        String placeOfOrigin;
        String description;
        ArrayList<String> ingredientsArray;
        String image;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject("name");
//            sandwich.setMainName(;
            mainName = name.getString("mainName");
            JSONArray alsoKnownAsJSONArray = name.getJSONArray("alsoKnownAs");
            alsoKnownAsArray = new ArrayList<String>();
            if (alsoKnownAsJSONArray == null) {
                Log.d("JsonUtil", "json is empty");
            } else {
                int length = alsoKnownAsJSONArray.length();

                for (int i = 0; i < length; i++) {
                    alsoKnownAsArray.add(alsoKnownAsJSONArray.get(i).toString());
                }
            }
//            sandwich.setAlsoKnownAs(alsoKnownAsArray);
//            sandwich.setPlaceOfOrigin(jsonObject.getString("placeOfOrigin"));
            placeOfOrigin = jsonObject.getString("placeOfOrigin");
//            sandwich.setDescription(jsonObject.getString("description"));
            description = jsonObject.getString("description");
            JSONArray ingredientsJSONArray = jsonObject.getJSONArray("ingredients");
            ingredientsArray = new ArrayList<String>();
            if (ingredientsJSONArray == null) {
                Log.d("JsonUtil", "json is empty");
            } else {
                int length = ingredientsJSONArray.length();

                for (int i = 0; i < length; i++) {
                    ingredientsArray.add(ingredientsJSONArray.get(i).toString());
                }
            }
//            sandwich.setImage(jsonObject.getString("image"));
            image = jsonObject.getString("image");
//            sandwich.setIngredients(ingredientsArray);


            sandwich = new Sandwich(mainName, alsoKnownAsArray, placeOfOrigin, description, image, ingredientsArray);
            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
