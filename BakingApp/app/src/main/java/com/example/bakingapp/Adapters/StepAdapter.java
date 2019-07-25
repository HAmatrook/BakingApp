package com.example.bakingapp.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bakingapp.Models.RecipeModel;
import com.example.bakingapp.R;


public class StepAdapter extends RecyclerView.Adapter<StepAdapter.SViewHolder>{

    public static final String KEY_SELECTED_STEP= "selected_step";
    public   View selectedView;
    public Button step_b;
    public Context context;
    private OnStepAdapterClickListener clickListener;
    RecipeModel selectedRecipe;



    public StepAdapter(RecipeModel selectedRecipe, Context context, OnStepAdapterClickListener clickListener){
        this.selectedRecipe = selectedRecipe;
        this.context = context;
        this.clickListener = clickListener;
    }

    public interface OnStepAdapterClickListener {
        void onStepSelected (int position);
    }

    @NonNull
    @Override
    public SViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.step_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean AttachImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, AttachImmediately);
        SViewHolder viewHolder = new SViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SViewHolder sViewHolder, int position) {
        sViewHolder.bind(position,clickListener);
    }

    @Override
    public int getItemCount() { return selectedRecipe.getShortDescription().size();}

    class SViewHolder extends RecyclerView.ViewHolder {//================================== class

        public SViewHolder(View itemView) {
            super(itemView);
            step_b = itemView.findViewById(R.id.step_b);
        }

        void bind(final int position , final OnStepAdapterClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    v.setSelected(true);
                    if (selectedView != null)
                         selectedView.setSelected(false);
                    selectedView = v;
                    listener.onStepSelected(position);
                }
            });
            if(position==0){
                step_b.setText(selectedRecipe.getShortDescription().get(position));
            }else
            step_b.setText(position+": " +selectedRecipe.getShortDescription().get(position));

        }

    }
}
