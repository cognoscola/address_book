package com.guillermo.addressbookapplication;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.guillermo.addressbookapplication.activities.ContactDetailActivity;
import com.guillermo.addressbookapplication.fragments.ContactDetailFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    @Rule
    public final ActivityTestRule<ContactDetailActivity> main = new ActivityTestRule<ContactDetailActivity>(ContactDetailActivity.class);/* {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, ContactDetailActivity.class);
            result.putExtra(ContactDetailFragment.ARG_ITEM_ID, 0);

            return result;
        }*/

    @Test
    public void shouldFragmentShow(){
        onView(withId(R.id.tv_email));
        onView(withId(R.id.tv_cell));
        onView(withId(R.id.tv_full_name));
        onView(withId(R.id.tv_city));
        onView(withId(R.id.tv_adress));
        onView(withId(R.id.tv_phone));
        onView(withId(R.id.tv_province));
    }

}
