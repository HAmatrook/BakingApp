package com.example.bakingapp.Widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.bakingapp.Models.RecipeModel;
import com.example.bakingapp.R;
import com.example.bakingapp.ui.RecipeDetails;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                RecipeModel savedRecipe, int[] appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        if( savedRecipe !=null) {
            Intent serviceIntent = new Intent(context, LinearWidgetRemote.class);
            views.setRemoteAdapter(R.id.widget_lv, serviceIntent);
            views.setTextViewText(R.id.empty_tv,savedRecipe.getName());
            //views.setEmptyView(R.id.widget_lv, R.id.empty_tv);
        }else {
            views.setTextViewText(R.id.empty_tv,"No Recipe is Selected");
        }
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//         There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            //updateAppWidget(context, appWidgetManager, appWidgetId);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
            Intent serviceIntent = new Intent(context, LinearWidgetRemote.class);

            views.setRemoteAdapter(R.id.widget_lv, serviceIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        updateAppWidget(context, appWidgetManager, null, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}

