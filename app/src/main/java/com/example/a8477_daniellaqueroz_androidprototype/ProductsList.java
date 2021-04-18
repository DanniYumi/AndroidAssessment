package com.example.a8477_daniellaqueroz_androidprototype;

import java.io.Serializable;
import java.util.Objects;

public class ProductsList implements Serializable {

    public static final String RECIPE_KEY = "recipe_key";
    public static final String RECIPE_DIFFICULTY = "recipe_difficulty";
    public static final String RECIPE_ID = "recipe_id";

    public Long id;
    public String recipeName;
    public String ingredients;
    public String productImage;
    public Long duration;
    private Integer votes;
    private Integer difficulty;

    public ProductsList(){}

    public ProductsList(Long id, String recipeName, String ingredients, Long duration, String productImage, Integer votes, Integer difficulty) {
        this.id = id;
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.duration = duration;
        this.productImage = productImage;
        this.votes = votes;
        this.difficulty = difficulty;
    }

    public static String getRecipeKey() {
        return RECIPE_KEY;
    }

    public static String getRecipeDifficulty() {
        return RECIPE_DIFFICULTY;
    }

    public static String getRecipeId() {
        return RECIPE_ID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "ProductsList{" +
                "id=" + id +
                ", recipeName='" + recipeName + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", duration=" + duration +
                ", productImage='" + productImage + '\'' +
                ", votes=" + votes +
                ", difficulty=" + difficulty +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsList productsList = (ProductsList) o;
        return id.equals(productsList.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
