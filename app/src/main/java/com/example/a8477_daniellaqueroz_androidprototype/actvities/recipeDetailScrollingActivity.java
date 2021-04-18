package com.example.a8477_daniellaqueroz_androidprototype.actvities;

import android.content.Intent;
import android.os.Bundle;

import com.example.a8477_daniellaqueroz_androidprototype.ProductsList;
import com.example.a8477_daniellaqueroz_androidprototype.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class recipeDetailScrollingActivity extends AppCompatActivity {
        RatingBar ratingBar;
        ProductsList productsList;
        Integer rate=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setIntentWithData();
                }
            });


        ImageView recipeImageImageView = findViewById(R.id.recipeImageViewDetailActivity);
        TextView RecipeNameTextView = findViewById(R.id.RecipeNameTextViewDetailActivity);
        ratingBar = findViewById(R.id.reciperRatingBarDetailActivity);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //set the rate the user selected
                rate = (int)ratingBar.getRating();
            }
        });

        //check if everything is ok until here
        Intent intentThatCalled = getIntent();
        if(intentThatCalled.hasExtra(ProductsList.RECIPE_KEY)){
            productsList =(ProductsList) intentThatCalled.getSerializableExtra(ProductsList.RECIPE_KEY);
            RecipeNameTextView.setText(productsList.getRecipeName());

            View rootView = recipeImageImageView.getRootView();
            //find the image and select the image
            int resID = rootView.getResources().getIdentifier(productsList.getProductImage(), "drawable", rootView.getContext().getPackageName());
            recipeImageImageView.setImageResource(resID);
        }
    }

    @Override
    public void onBackPressed() {
        setIntentWithData();
        super.onBackPressed();
    }

    private void setIntentWithData() {
        Intent goingBack=new Intent();
        //monster information rate the monster
        goingBack.putExtra(ProductsList.RECIPE_KEY, productsList);
        goingBack.putExtra(ProductsList.RECIPE_DIFFICULTY, rate);

        setResult(RESULT_OK, goingBack);
        finish();

    }
}