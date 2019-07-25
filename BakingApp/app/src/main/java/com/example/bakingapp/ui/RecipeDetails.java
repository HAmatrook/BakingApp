package com.example.bakingapp.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RemoteViews;

import com.example.bakingapp.Models.RecipeModel;
import com.example.bakingapp.R;
import com.example.bakingapp.Widgets.RecipeWidgetProvider;
import com.example.bakingapp.Widgets.LinearWidgetRemote;

import java.util.ArrayList;

import static com.example.bakingapp.ui.MainActivity.KEY_SELECTED_ITEM;

public class RecipeDetails  extends AppCompatActivity implements DetailsFragment.OnWidgetListener {

    public static RecipeModel savedRecipe;
    public static boolean twoPane = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        if(savedInstanceState == null) {
            Intent intent = getIntent();
            RecipeModel recipe = intent.getParcelableExtra(KEY_SELECTED_ITEM);

            FragmentManager fragmentManager = getSupportFragmentManager();
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setRecipe(recipe);


            fragmentManager.beginTransaction()
                    .add(R.id.details_container, detailsFragment)
                    .commit();

            if(findViewById(R.id.steps_container) != null){
                twoPane = true;
                FragmentManager fragmentManager2 = getSupportFragmentManager();
                StepFragment stepFragment = new StepFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(DetailsFragment.POSITION,0);
                stepFragment.setArguments(bundle);
                fragmentManager2.beginTransaction()
                        .add(R.id.steps_container, stepFragment)
                        .commit();

            }else {
                twoPane = false;
            }
        }
    }

    @Override
    public void onBackPressed() {
        //return to the previous fragment
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onWidgetButtonClicked() {
        Context context = this;
        savedRecipe = DetailsFragment.recipe;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidgetProvider.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_lv);

        RecipeWidgetProvider.updateAppWidget(context, appWidgetManager, savedRecipe, appWidgetIds);
////        ArrayList<String>a = new ArrayList<>();
//        a.clear();
//        a.addAll(DetailsFragment.recipe.getIngredient());
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
//        ComponentName thisWidget = new ComponentName(context, RecipeWidgetProvider.class);
//        //remoteViews.setTextViewText(R.id.appwidget_text, a.get(0));
//
//        Intent intent = new Intent(context, LinearWidgetRemote.class);
//        remoteViews.setRemoteAdapter(R.id.widget_lv, intent);
//
//        Intent appIntent = new Intent(context, RecipeDetails.class);
//        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setPendingIntentTemplate(R.id.widget_lv, appPendingIntent);

//        appWidgetManager.updateAppWidget(thisWidget, remoteViews);

//        UpdateWidgetService.startActionIngredientService(context,a);
    }
}
