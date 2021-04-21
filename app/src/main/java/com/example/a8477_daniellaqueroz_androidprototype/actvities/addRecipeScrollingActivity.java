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
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

public class addRecipeScrollingActivity extends AppCompatActivity {

    EditText recipeNameEditText;
    EditText addIngredientsEditText;
    SeekBar durationSeekBar;
    Button cancelButton;
    Button addButton;

    private ProductsList productsList;
    private Long durationValue = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add the recipe", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        recipeNameEditText = findViewById(R.id.RecipeNameEditText);
        addIngredientsEditText = findViewById(R.id.addIngredientsEditText);
        cancelButton= findViewById(R.id.cancelButton);
        addButton= findViewById(R.id.addButton);
        durationSeekBar=findViewById(R.id.durationSeekBar);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel(v);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(v);
            }
        });

        durationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                durationValue = Long.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private void add(View v) {
        String recipeName = recipeNameEditText.getText().toString();
        if (recipeName.trim().isEmpty()) {
            Snackbar.make(v, "Please insert the recipes name", Snackbar.LENGTH_LONG).show();
            recipeNameEditText.getText().clear();
            recipeNameEditText.requestFocus();
            return;
        }
        String ingredients = addIngredientsEditText.getText().toString();
        if (ingredients.trim().isEmpty()) {
            Snackbar.make(v, "Please insert the recipes name", Snackbar.LENGTH_LONG).show();
            addIngredientsEditText.getText().clear();
            addIngredientsEditText.requestFocus();
            return;
        }
        if(!recipeName.trim().isEmpty() && !ingredients.trim().isEmpty()) {

            productsList = new ProductsList();
            productsList.setRecipeName(recipeName);
            productsList.setIngredients(ingredients);
            productsList.setDuration(durationValue);

            Intent goingBack = new Intent();
            goingBack.putExtra(ProductsList.RECIPE_KEY, productsList);
            setResult(RESULT_OK, goingBack);
            finish();
        }
    }
    private void cancel(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }
}

