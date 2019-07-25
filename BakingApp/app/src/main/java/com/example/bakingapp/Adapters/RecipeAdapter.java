package com.example.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.bakingapp.Models.RecipeModel;
import com.example.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RViewHolder> {
    public TextView title_tv;
    public TextView servings_tv;
    public Context context;
    private final List<RecipeModel> recipeList;
    private OnRecipeAdapterClickListener clickListener;

    public RecipeAdapter(List<RecipeModel> recipeList, OnRecipeAdapterClickListener clickListener) {
        this.recipeList = recipeList;
        this.clickListener = clickListener;
    }

    public interface OnRecipeAdapterClickListener {
        void onRecipeSelected (RecipeModel item);
    }

    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean AttachImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, AttachImmediately);
        RViewHolder viewHolder = new RViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RViewHolder rViewHolder, int position) {
        rViewHolder.bind(recipeList.get(position),clickListener);
    }

    @Override
    public int getItemCount() {return recipeList.size();

    }

    class RViewHolder extends RecyclerView.ViewHolder {//================================== class

        public RViewHolder(View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.title_tv);
            servings_tv = itemView.findViewById(R.id.servings_tv);
        }

        void bind(final RecipeModel item, final OnRecipeAdapterClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onRecipeSelected(item);
                }
            });
             title_tv.setText(item.getName());
             servings_tv.setText("Servings: "+item.getServings());

        }

    }

}
