package com.example.bakingapp.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends BaseAdapter {


    private Context context;
    private List<String> quantityList;
    private List<String> measureList;
    private List<String> ingredientList;


    public TextView ingredient_tv;
    public LayoutInflater inflater;

    public IngredientAdapter(Context context, List<String> quantityList,
                             List<String> measureList, List<String> ingredientList) {
        this.context = context;
        this.quantityList = quantityList;
        this.measureList = measureList;
        this.ingredientList = ingredientList;
    }

    @Override
    public int getCount() {
        return measureList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        inflater= LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.ingredient_item, viewGroup, false);
       ingredient_tv = row.findViewById(R.id.ingredient_tv);

       String text = quantityList.get(position)+"\t\t"
                    + measureList.get(position)+"\t\t"
                    +ingredientList.get(position);
       ingredient_tv.setText(text);

        return row;
    }

}
