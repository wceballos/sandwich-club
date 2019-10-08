package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        // The json argument is a json string with the sandwich details
        // We must parse these details to create a Sandwich object
        Sandwich sandwich = null;

        String mainName;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<>();;

        JSONObject sandwichJson = new JSONObject(json);

        // Get nested 'name' object
        JSONObject nameJson = sandwichJson.getJSONObject("name");
        // Get mainName string in the 'name' object
        mainName = nameJson.getString("mainName");
        // Get 'alsoKnownAs' Json string array
        JSONArray alsoKnownAsArray = nameJson.getJSONArray("alsoKnownAs");
        for(int i = 0; i < alsoKnownAsArray.length(); i++) {
            alsoKnownAs.add(alsoKnownAsArray.getString(i));
        }

        placeOfOrigin = sandwichJson.getString("placeOfOrigin");
        description = sandwichJson.getString("description");
        image = sandwichJson.getString("image");

        JSONArray ingredientsArray = sandwichJson.getJSONArray("ingredients");
        for(int i = 0; i < alsoKnownAsArray.length(); i++) {
            ingredients.add(ingredientsArray.getString(i));
        }

        sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        return sandwich;
    }
}
