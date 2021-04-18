package com.example.a8477_daniellaqueroz_androidprototype;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.a8477_daniellaqueroz_androidprototype.actvities.addRecipeScrollingActivity;
import com.example.a8477_daniellaqueroz_androidprototype.actvities.recipeDetailScrollingActivity;
import com.example.a8477_daniellaqueroz_androidprototype.entities.Constants;
import com.example.a8477_daniellaqueroz_androidprototype.recyclerview.OnRecipeListener;
import com.example.a8477_daniellaqueroz_androidprototype.recyclerview.recipeRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static com.example.a8477_daniellaqueroz_androidprototype.entities.Constants.VIEW_DETAILS_ACTIVITY_CODE;

public class MainActivity extends AppCompatActivity implements OnRecipeListener {

    private EditText recipeNameEditText;
    private EditText ingredientsEditText;
    private EditText deleteEditText;
    private EditText durationEditText;
    private RecyclerView recipeRecyclerView;
    private List<ProductsList> productsList;
    private recipeRecyclerViewAdapter adapter;

    DataService productListDataService;
    View rootview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recipeRecyclerView = findViewById(R.id.recipesRecyclerView);

        recipeRecyclerView.setLayoutManager(linearLayoutManager);

        productListDataService = new DataService();
        productListDataService.init(this);
        rootview= findViewById(R.id.toolbar).getRootView();

        productsList= productListDataService.getProductsList();

        adapter = new recipeRecyclerViewAdapter(productsList, this, this);

        recipeRecyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //     Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //            .setAction("Action", null).show();
                addNewRecipe();


            }

        });
    }

    private void addNewRecipe() {
        Intent goToAddNewRecipe = new Intent(MainActivity.this, addRecipeScrollingActivity.class);
        startActivityForResult(goToAddNewRecipe, Constants.ADD_RECIPE_ACTIVITY_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Constants.ADD_RECIPE_ACTIVITY_CODE){
            if(resultCode==RESULT_OK){
                addNewRecipeData(data);
            }
        }
        if( requestCode== VIEW_DETAILS_ACTIVITY_CODE){
            if(resultCode==RESULT_OK){
                //modify in the database
                modifyRecipe(data);
            }
        }
    }

    private void modifyRecipe(Intent data) {
        Integer stars;
        Long id;

        if(data.hasExtra(ProductsList.RECIPE_KEY)&& data.hasExtra(ProductsList.getRecipeDifficulty())){
            ProductsList productsList = (ProductsList)data.getSerializableExtra(ProductsList.RECIPE_KEY);
            stars = data.getIntExtra(ProductsList.RECIPE_DIFFICULTY, 0);
            id= productsList.getId();
            if (stars > 0) {
                boolean result = productListDataService.rateRecipe(id, stars);
                int position = adapter.getProductsLists().indexOf(productsList);
                //wil list the recipe in position
                if(position>0){
                    productsList= (ProductsList) productListDataService.getProductsList(id);
                    adapter.replaceItem(position, productsList);

                }
            }
        }
    }

    private void addNewRecipeData(Intent data) {
        ProductsList productsList =(ProductsList) data.getSerializableExtra(ProductsList.RECIPE_KEY);
        Long result= productListDataService.add(productsList);
        String message;
        if (result > 0) {
            message="Your recipe" + productsList.getRecipeName() +" was add";
        }
        else{
            message="Something went wrong and we coudn't add your recipe";
        }
        Snackbar.make(rootview, message, Snackbar.LENGTH_LONG ).show();
    }

    @Override
    public void onRecipeClick(ProductsList productsList) {
        showRecipeDetail(productsList);
    }

    private void showRecipeDetail(ProductsList productsList) {
        Intent goToRecipeDetail = new Intent(MainActivity.this, recipeDetailScrollingActivity.class);
        goToRecipeDetail.putExtra(ProductsList.RECIPE_KEY, productsList);
        startActivityForResult(goToRecipeDetail, VIEW_DETAILS_ACTIVITY_CODE);
    }
}