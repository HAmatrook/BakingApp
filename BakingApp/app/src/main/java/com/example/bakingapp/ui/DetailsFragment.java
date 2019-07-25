package com.example.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bakingapp.Adapters.IngredientAdapter;
import com.example.bakingapp.Adapters.StepAdapter;
import com.example.bakingapp.Models.RecipeModel;
import com.example.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;


public class DetailsFragment extends Fragment {

    public static String POSITION = "position";

    public IngredientAdapter ingredientAdapter;
    public StepAdapter stepAdapter;
    public LinearLayoutManager linearLayout;
    public Context context;
    public static RecipeModel recipe;
    public OnWidgetListener onWidgetButtonClicked;


    //public TextView servings_tv;
    //public ListView ingredient_lv;
   // public RecyclerView steps_rv;
    //public Button widget_b;

    @BindView(R.id.title_tv)
    TextView title_tv;
    Unbinder unbinder;
    @BindView(R.id.servings_tv)
    TextView servings_tv;
    @BindView(R.id.widget_b)
    Button widget_b;
    @BindView(R.id.ingredient_lv)
    ListView ingredient_lv;
    @BindView(R.id.steps_rv)
    RecyclerView steps_rv;


    public DetailsFragment() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnWidgetListener {
        void onWidgetButtonClicked();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Timber.plant(new Timber.DebugTree());
        final View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ButterKnife.bind(this, rootView);
        linearLayout = new LinearLayoutManager(getActivity());

        context = getActivity();
        if (recipe != null) {
            title_tv.setText(recipe.getName());
            servings_tv.setText("Servings: " + recipe.getServings());
            Timber.d("Set ingredient ListView");
            ingredientAdapter = new IngredientAdapter(context,
                    recipe.getQuantity(),
                    recipe.getMeasure(),
                    recipe.getIngredient());
            ingredient_lv.setAdapter(ingredientAdapter);

            widget_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onWidgetButtonClicked.onWidgetButtonClicked();
                }
            });

            Timber.d("Set steps RecyclerView");
            steps_rv.setLayoutManager(linearLayout);
            steps_rv.setHasFixedSize(true);
            stepAdapter = new StepAdapter(recipe, context, new StepAdapter.OnStepAdapterClickListener() {
                @Override
                public void onStepSelected(int position) {
                    Timber.d("A step position was selected");
                    openStepDetails(position);
                }
            });
            steps_rv.setAdapter(stepAdapter);
            stepAdapter.notifyDataSetChanged();
        }

        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void setRecipe(RecipeModel recipe) {
        this.recipe = recipe;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnWidgetListener) {
            onWidgetButtonClicked = (OnWidgetListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void openStepDetails(int position) {
        Timber.d("Open step no." + position);
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        StepFragment stepFragment = new StepFragment();
        stepFragment.setArguments(bundle);
        if (RecipeDetails.twoPane) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.steps_container, stepFragment)
                    .commit();
        } else {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.details_container, stepFragment)
                    .addToBackStack(null)
                    .commit();
        }


    }


}
