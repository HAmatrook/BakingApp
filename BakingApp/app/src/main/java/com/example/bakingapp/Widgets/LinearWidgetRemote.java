package com.example.bakingapp.Widgets;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.R;
import com.example.bakingapp.ui.RecipeDetails;

import java.util.ArrayList;
import java.util.List;

public class LinearWidgetRemote extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new  LinerRemoteViewsFactory(this.getApplicationContext());
    }


    public class LinerRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {//================Class

        Context context;
         List<String> ingredients = new ArrayList<>();

        public LinerRemoteViewsFactory(Context applicationContext) {
            context = applicationContext;
            if (RecipeDetails.savedRecipe != null) {

                ingredients = RecipeDetails.savedRecipe.getFullIngredient();
            }
        }
        @Override
        public void onCreate() { }

        @Override
        public void onDataSetChanged() {
            if (RecipeDetails.savedRecipe != null) {
                ingredients = RecipeDetails.savedRecipe.getFullIngredient();
            }
        }

        @Override
        public void onDestroy() {}

        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_item);
            views.setTextViewText(R.id.ingredient_tv, ingredients.get(position));
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

    }
}

