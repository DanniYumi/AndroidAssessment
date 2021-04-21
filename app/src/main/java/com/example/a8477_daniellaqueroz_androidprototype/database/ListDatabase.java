package com.example.a8477_daniellaqueroz_androidprototype.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.a8477_daniellaqueroz_androidprototype.ProductsList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListDatabase extends SQLiteOpenHelper {

    private static final String TAG = ListDatabase.class.getName();

    private static ListDatabase mInstance = null;
    private Context context;

    //create database constants
    private static final String DATABASE_NAME = "recipeList.db";
    private static final Integer DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "recipeList";

    //create constants for the table's column name
    private static final String COL_ID = "ID";
    private static final String COL_RECIPENAME = "RECIPENAME";
    private static final String COL_INGREDIENTS = "INGREDIENTS";
    private static final String COL_DURATION = "DURATION";
    private static final String COL_PRODUCTIMAGE = "PRODUCTIMAGE";
    private static final String COL_VOTES = "VOTES";
    private static final String COL_DIFFICULTY = "DIFFICULTY";

    //create sql statements initial version
    private static final String CREATE_TABLE_ST = "CREATE TABLE " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_RECIPENAME + " TEXT, " +
            COL_INGREDIENTS + " TEXT, " +
            COL_DURATION + " INTEGER, " +
            COL_PRODUCTIMAGE + " TEXT, " +
            COL_VOTES + " INTEGER DEFAULT 0, " +
            COL_DIFFICULTY + " INTEGER DEFAULT 0 )";

    private static final String DROP_TABLE_ST = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String GET_ALL_ST = "SELECT * FROM " + TABLE_NAME;
    private static final String GET_RERCIPE_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + "= ?";
    private static final String UPDATE_RECIPE_VOTES = "UPDATE " + TABLE_NAME + " SET " + COL_DIFFICULTY + " = " + COL_DIFFICULTY + " + ? " + ", " + COL_VOTES + " = " + COL_VOTES + " + 1" + " WHERE " + COL_ID + "= ?";

    public static synchronized ListDatabase getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new ListDatabase(ctx.getApplicationContext());
        }
        return mInstance;
    }
    private ListDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_ST);
        onCreate(sqLiteDatabase);
    }
    //will insert a new recipe in the database
    public Long insert(String recipeName, String ingredients, Long duration) {
        //create an instance of SQLITE database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_RECIPENAME, recipeName);
        contentValues.put(COL_INGREDIENTS, ingredients);
        contentValues.put(COL_DURATION, duration);
        //we store the image name, after using
        contentValues.put(COL_PRODUCTIMAGE, getRandomProductImage());

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        //if result is -1  insert was not performed due to an error, otherwise will have the row ID of the newly inserted row
        return result;
    }

    private String getRandomProductImage() {
        Random ran = new Random();
        int value = ran.nextInt(30) + 1;
        return "product_" + value;
    }

    //is going to show all the recipes
    private Cursor getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(GET_ALL_ST, null);
    }

    // will update the recipe in the database
    public boolean update(Long id, String recipeName, String ingredients, Long duration) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, id);
        contentValues.put(COL_RECIPENAME, recipeName);
        contentValues.put(COL_INGREDIENTS, ingredients);
        contentValues.put(COL_DURATION, duration);

        int numOfRowsUpdated = db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id.toString()});
        db.close();
        return numOfRowsUpdated == 1; //if your query is going to update more than 1 record (this is not the case) then the condition will be numRowsUpdated > 0
    }
    //will delete a recipe from the database
    public boolean delete(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int numOfRowsDeleted = db.delete(TABLE_NAME, "ID = ?", new String[]{id.toString()});
        db.close();
        return numOfRowsDeleted == 1;
    }

    //list of all the recipes that there are in the database
    public List<ProductsList> getProductsList() {
        List<ProductsList> productsLists = new ArrayList<>();
        Cursor cursor = getAll();

        if(cursor.getCount() > 0) {
            ProductsList productsList;
            while (cursor.moveToNext()) {
                Long id = cursor.getLong(0);
                String recipeName = cursor.getString(1);
                String ingredients = cursor.getString(2);
                Long duration = cursor.getLong(3);
                String productImage = cursor.getString(4);
                Integer votes = cursor.getInt(5);
                Integer difficulty = cursor.getInt(6);

                productsList = new ProductsList(id, recipeName, ingredients, duration, productImage, votes, difficulty);
                productsLists.add(productsList);
            }
        }
        cursor.close();
        return productsLists;
    }


    public ProductsList getProductsList(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ProductsList productsList= null;
        Cursor cursor = db.rawQuery(GET_RERCIPE_BY_ID, new String[]{id.toString()});

        if(cursor.getCount() > 0 ){
            while (cursor.moveToNext()){
                String recipeName = cursor.getString(1);
                String ingredients = cursor.getString(2);
                Long duration = cursor.getLong(3);
                String productImage = cursor.getString(4);
                Integer votes = cursor.getInt(5);
                Integer difficulty = cursor.getInt(6);

                productsList = new ProductsList(id, recipeName , ingredients,  duration, productImage, votes, difficulty);
            }
        }
        cursor.close();
        return productsList;
    }

    public boolean rateRecipe(Long id, Integer stars){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(UPDATE_RECIPE_VOTES, new String[ ]{ stars.toString(), id.toString() });
        return true;
    }
}
