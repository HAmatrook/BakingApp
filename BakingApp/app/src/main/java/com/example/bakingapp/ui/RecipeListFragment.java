package com.example.bakingapp.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapp.Adapters.RecipeAdapter;
import com.example.bakingapp.Models.RecipeModel;
import com.example.bakingapp.R;
import com.example.bakingapp.utils.JSONUtils;
import com.example.bakingapp.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class RecipeListFragment extends Fragment implements RecipeAdapter.OnRecipeAdapterClickListener {

    public static RecyclerView recycler;
    public static RecipeAdapter adapter;
    public static List<RecipeModel> recipeList;
    public static LinearLayoutManager linearLayout;
    public static OnRClickListener onClickListener;
    Unbinder unbinder;

    public RecipeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onRecipeSelected(RecipeModel item) {
        onClickListener.onRecipeSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnRClickListener {
        void onRecipeSelected(RecipeModel item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.plant(new Timber.DebugTree());
        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        recycler = rootView.findViewById(R.id.recycler_view);
        linearLayout = new LinearLayoutManager(getActivity());
        URL Recipe_URL = NetworkUtils.buildUrl();


        new networkTask().execute(Recipe_URL);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRClickListener) {
            onClickListener = (OnRClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onClickListener = null;
    }


    public static class networkTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String SearchResults = null;
            try {
                SearchResults = NetworkUtils.getResponse(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return SearchResults;
        }

        @Override
        protected void onPostExecute(String searchResults) {
            if (searchResults != null && !searchResults.equals("")) {
                recipeList = JSONUtils.getRecipe(searchResults);
                recycler.setLayoutManager(linearLayout);
                recycler.setHasFixedSize(true);
                adapter = new RecipeAdapter(recipeList, new RecipeAdapter.OnRecipeAdapterClickListener() {
                    @Override
                    public void onRecipeSelected(RecipeModel item) {
                        Timber.d("A card position was selected");
                        onClickListener.onRecipeSelected(item);
                    }
                });
                recycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
