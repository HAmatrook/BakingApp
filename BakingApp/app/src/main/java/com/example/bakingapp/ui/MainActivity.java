package com.example.bakingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bakingapp.Adapters.RecipeAdapter;
import com.example.bakingapp.Models.RecipeModel;
import com.example.bakingapp.R;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RecipeListFragment.OnRClickListener {

    public static final String KEY_SELECTED_ITEM = "selected_item";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.plant(new Timber.DebugTree());
    }

    @Override
    public void onRecipeSelected(RecipeModel item) {
        Timber.d("A recipe card  was selected");
        Intent intent = new Intent(MainActivity.this,RecipeDetails.class);
        intent.putExtra(KEY_SELECTED_ITEM, item);
        startActivity(intent);
    }
}
