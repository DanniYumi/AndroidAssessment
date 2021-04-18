package com.example.a8477_daniellaqueroz_androidprototype.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a8477_daniellaqueroz_androidprototype.ProductsList;
import com.example.a8477_daniellaqueroz_androidprototype.R;

import java.util.List;

public class recipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private List<ProductsList> productsLists;
    private Context context;
    private OnRecipeListener onRecipeListener;

        public List<ProductsList> getProductsLists() {
        return productsLists;
    }
    public recipeRecyclerViewAdapter(List<ProductsList> productsLists, Context context, OnRecipeListener onRecipeListener){
        this.productsLists = productsLists;
        this.context = context;
        this.onRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //define the layout
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View recipeview =inflater.inflate(R.layout.recycler_item_view, parent , false);

        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(recipeview, onRecipeListener);

        return recipeViewHolder;
    }
    //will show the position when you scroll it
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
            //get databased o the position
        ProductsList productsList = productsLists.get(position);
        holder.updateRecipes(productsList);
        holder.bind(productsList,onRecipeListener);

    }
    //count the list of items in the table
    @Override
    public int getItemCount() {
        return productsLists.size();
    }
    //change the recipe if modify
    public void replaceItem(int position, ProductsList productsList){
            productsLists.set(position, productsList);
            notifyItemChanged(position);
    }
}
