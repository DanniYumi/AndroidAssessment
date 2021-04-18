package com.example.a8477_daniellaqueroz_androidprototype;

import android.content.Context;

import com.example.a8477_daniellaqueroz_androidprototype.database.ListDatabase;

import java.util.List;

public class DataService {
    private ListDatabase sqlite;

    public void init(Context context){

        sqlite = sqlite.getInstance(context);

    }
    //add recipes in the database and return the id of it
    public Long add(ProductsList productsList){
        return sqlite.insert(productsList.getRecipeName(), productsList.getIngredients(), productsList.getDuration());
    }
    //delete a recipe
    public boolean delete(ProductsList productsList){

        return sqlite.delete(productsList.getId());
    }

    //update the recipe by the id
    public boolean update(ProductsList productsList){
        return sqlite.update(productsList.getId(), productsList.getRecipeName(), productsList.getIngredients(),productsList.getDuration());
    }

    //return the list
    public List<ProductsList> getProductsList(){
        List<ProductsList> productsLists = sqlite.getProductsList();
        return productsLists;
    }
    public ProductsList getProductsList(Long id){

        return sqlite.getProductsList(id);
    }

    public boolean rateRecipe(Long id, Integer stars)
    {
        return sqlite.rateRecipe(id, stars);
    }
}
