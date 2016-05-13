package com.guillermo.addressbookapplication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.guillermo.addressbookapplication.activities.ContactListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ListActivityTest  {

    final CountDownLatch signal = new CountDownLatch(1);

    @Rule
    public final ActivityTestRule<ContactListActivity> main = new ActivityTestRule<>(ContactListActivity.class);


    @Test
    public void shouldBeAbleToLaunchMainScreen(){
    }

    @Test
    public void shouldHaveItemsLoaded(){

        signal.countDown();
        onView(withId(R.id.contact_list));
    }

}
