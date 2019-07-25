package com.example.bakingapp;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.example.bakingapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.contrib.RecyclerViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class RecipeRecyclerViewTest {
    private final String RECIPE_NAME= "Nutella Pie";

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void clickRecyclerViewItem_OpensDetailsActivity() throws InterruptedException {
       // Thread.sleep(10000); //waite for the data
//        onView(withId(R.id.recycler_view))
//                .perform(actionOnItemAtPosition(0, click()));
        onView(allOf(isDisplayed(), withId(R.id.recycler_view)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

//        onData(withId(R.id.recycler_view))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0, doubleClick()));

        onView(withId(R.id.title_tv)).check(ViewAssertions.matches(withText(RECIPE_NAME)));
    }
}
