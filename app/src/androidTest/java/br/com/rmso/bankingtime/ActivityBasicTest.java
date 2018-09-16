package br.com.rmso.bankingtime;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import br.com.rmso.cooking.R;
import br.com.rmso.cooking.ui.activities.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ActivityBasicTest extends ActivityInstrumentationTestCase2<MainActivity>{

    public static final String RECIPE_NAME = "Brownies";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    public ActivityBasicTest() {
        super(MainActivity.class);
    }


    @Test
    public void checkRecyclerView_MainActivity(){
        onView(withId(R.id.rv_recipes))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickRecyclerViewItem_OpenDetailRecipeActivity (){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.img_recipe_detail))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkNameRecipe_DetailRecipeActivity (){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.tv_name_recipe_detail))
                .check(matches(withText(RECIPE_NAME)));
    }

}
