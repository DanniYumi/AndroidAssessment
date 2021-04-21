package com.example.a8477_daniellaqueroz_androidprototype.recyclerview;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a8477_daniellaqueroz_androidprototype.ProductsList;
import com.example.a8477_daniellaqueroz_androidprototype.R;
import com.google.android.material.snackbar.Snackbar;


public class RecipeViewHolder extends RecyclerView.ViewHolder {

    public final ImageView recipeImageImageView;
    public final TextView RecipeNameTextView;
    public final TextView ingredientsTextView;
    public final TextView votesTextView;
    public Button prepareButton;
    public RatingBar ratingBar= null;
    private OnRecipeListener onRecipeListener;

    public RecipeViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
        super(itemView);

        recipeImageImageView = itemView.findViewById(R.id.recipeImageImageView);
        RecipeNameTextView = itemView.findViewById(R.id.RecipeNameEditText);
        ingredientsTextView=itemView.findViewById(R.id.ingredientstTextView);
        votesTextView=itemView.findViewById(R.id.votesTextView);
        ratingBar =itemView.findViewById(R.id.votesRatingBar);
        prepareButton= itemView.findViewById(R.id.prepareButton);

        this.onRecipeListener = onRecipeListener;

    }
        //update the informations of a recipe
        public void updateRecipes(ProductsList productsList){
        RecipeNameTextView.setText(productsList.getRecipeName());
        ingredientsTextView.setText(productsList.getIngredients());
        votesTextView.setText(productsList.getVotes()+" Votes");

        View rootView= recipeImageImageView.getRootView();
        //will find in the folder drawable for the image id
        int resID= rootView.getResources().getIdentifier(productsList.getProductImage(), "drawable", rootView.getContext().getPackageName());
        recipeImageImageView.setImageResource(resID);
        this.RecipeNameTextView.setText(productsList.getRecipeName());
        this.ingredientsTextView.setText(productsList.getIngredients());
        this.votesTextView.setText(productsList.getVotes()+" votes");

        //get the stars and divide by the number of votes and gives the average
        float rate;
        if(productsList.getVotes()>0){
            rate = 1.0f* productsList.getDifficulty();
        }
        else{
            rate=0;
        }
        ratingBar.setRating(rate);
    }

    public void bind (ProductsList productsList, OnRecipeListener onRecipeListener){
        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecipeListener.onRecipeClick(productsList);
            }
        });

        prepareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(prepareButton.getRootView(),"How to prepare: " + productsList.getRecipeName(), Snackbar.LENGTH_LONG ).show();
            }
        });
    }
}
