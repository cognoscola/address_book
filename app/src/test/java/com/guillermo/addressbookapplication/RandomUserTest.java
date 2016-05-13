package com.guillermo.addressbookapplication;

import com.guillermo.addressbookapplication.libraries.RandomUserApi.RandomUser;
import com.guillermo.addressbookapplication.libraries.RandomUserApi.entities.UserRandomResponse;

import junit.framework.TestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by alvaregd on 13/05/16.
 * Test that the RandomUserApi is returning valid results and in a timely manner
 */
public class RandomUserTest extends TestCase {

    private CountDownLatch lock = new CountDownLatch(1);
    private UserRandomResponse responseUserRandom;
    private final RandomUser manager = new RandomUser();

    public void testUserAsync() throws Exception {

        manager.userServicesAsync().listUserAsync(20, new Callback<UserRandomResponse>() {
            @Override
            public void success(UserRandomResponse userRandomResponse, Response response) {
                responseUserRandom = userRandomResponse;
                lock.countDown();
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });

        lock.await(2000, TimeUnit.MILLISECONDS);
        assertNotNull(responseUserRandom);
        assertEquals(responseUserRandom.getResults().size(), 20);
    }
}
