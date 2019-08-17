package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView mainNameTextView;
    TextView alsoKnownTextView;
    TextView originTextView;
    TextView ingredientsTextView;
    TextView descriptionTextView;
    TextView alsoKnownTextViewTitle;
    TextView originTextViewTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mainNameTextView = findViewById(R.id.main_name_tv);
        alsoKnownTextView = findViewById(R.id.also_known_as_tv);
        originTextView = findViewById(R.id.place_of_origin_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        descriptionTextView = findViewById(R.id.description_tv);

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
        Log.i("DetailActivity",json);
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        alsoKnownTextViewTitle = findViewById(R.id.also_known_as_tv_title);
        originTextViewTitle = findViewById(R.id.place_of_origin_tv_title);

        mainNameTextView.setText(sandwich.getMainName());
        if (sandwich.getAlsoKnownAs().size()!= 0) {
            alsoKnownTextView.setVisibility(View.VISIBLE);
            alsoKnownTextViewTitle.setVisibility(View.VISIBLE);
            for (int i = 0; i < sandwich.getAlsoKnownAs().size() - 1; i++) {
                alsoKnownTextView.append(sandwich.getAlsoKnownAs().get(i) + "\n");
            }
            alsoKnownTextView.append(sandwich.getAlsoKnownAs().get(sandwich.getAlsoKnownAs().size() - 1));
        }
        else {
            alsoKnownTextView.setVisibility(View.GONE);
            alsoKnownTextViewTitle.setVisibility(View.GONE);

        }
        if (!sandwich.getPlaceOfOrigin().equals("") ) {
            originTextView.setVisibility(View.VISIBLE);
            originTextViewTitle.setVisibility(View.VISIBLE);
            originTextView.setText(sandwich.getPlaceOfOrigin());
        }else {
            originTextView.setVisibility(View.GONE);
            originTextViewTitle.setVisibility(View.GONE);
        }
        if (sandwich.getIngredients() !=null) {

            for (int i =0;i<sandwich.getIngredients().size()-1;i++) {
                ingredientsTextView.append(sandwich.getIngredients().get(i)+"\n");
            }
            ingredientsTextView.append(sandwich.getIngredients().get(sandwich.getIngredients().size()-1));
        }
        descriptionTextView.setText(sandwich.getDescription());

    }
}
