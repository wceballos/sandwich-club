package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private Sandwich mSandwich = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            mSandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            closeOnError();
            return;
        }
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(ingredientsIv);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        TextView alsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
        TextView ingredients = (TextView) findViewById(R.id.ingredients_tv);
        TextView origin      = (TextView) findViewById(R.id.origin_tv);
        TextView description = (TextView) findViewById(R.id.description_tv);

        List<String> alsoKnownAsList = mSandwich.getAlsoKnownAs();
        List<String> ingredientsList = mSandwich.getIngredients();

        alsoKnownAs.append("\n");
        for(String otherName : alsoKnownAsList) {
            alsoKnownAs.append(otherName);
            if(otherName != alsoKnownAsList.get(alsoKnownAsList.size()-1))
                alsoKnownAs.append(", ");
        }
        alsoKnownAs.append("\n");

        for(String ingredient : ingredientsList) {
            ingredients.append("\n\u2022\t" + ingredient);
        }
        ingredients.append("\n");

        origin.append("\n" + mSandwich.getPlaceOfOrigin() +"\n");
        description.append("\n" + mSandwich.getDescription() +"\n");
    }
}
