/*
 * Copyright 2014 Jose Luis Franconetti
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.guillermo.addressbookapplication.libraries.RandomUserApi;

import com.guillermo.addressbookapplication.libraries.RandomUserApi.services.UserService;
import com.guillermo.addressbookapplication.libraries.RandomUserApi.services.UserServiceAsync;

import retrofit.RestAdapter;

public class RandomUser {

    // END POINT
    public static final String END_POINT = "http://api.randomuser.me/0.4.1";

    private boolean isDebug;
    private RestAdapter restAdapter;

    /**
     * Default constructor
     */
    public RandomUser() {}

    /**
     * Set the {@link retrofit.RestAdapter} log level.
     *
     * @param isDebug If true, the log level is set to {@link retrofit.RestAdapter.LogLevel#FULL}.
     *                Otherwise {@link retrofit.RestAdapter.LogLevel#NONE}.
     */
    public RandomUser setIsDebug (boolean isDebug) {
        this.isDebug = isDebug;
        if (restAdapter != null) {
            restAdapter.setLogLevel(isDebug ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE);
        }
        return this;
    }

    /**
     * Create a new {@link retrofit.RestAdapter.Builder}. Override this to e.g. set your own client or executor.
     *
     * @return A {@link retrofit.RestAdapter.Builder} with no modifications.
     */
    protected RestAdapter.Builder newRestAdapterBuilder () {
        return new RestAdapter.Builder();
    }

    /**
     * Return the current {@link retrofit.RestAdapter} instance. If none exists builds a new one.
     */
    protected RestAdapter getRestAdapter () {
        if (restAdapter == null) {
            RestAdapter.Builder builder = newRestAdapterBuilder();
            builder.setEndpoint(END_POINT);

            if (isDebug) {
                builder.setLogLevel(RestAdapter.LogLevel.FULL);
            }

            restAdapter = builder.build();
        }

        return restAdapter;
    }

    /**
     * Return the {@link com.guillermo.addressbookapplication.libraries.RandomUserApi.services.UserService} instance.
     *
     */
    public UserService userServices () {
        return getRestAdapter().create(UserService.class);
    }

    /**
     * Return the {@link com.guillermo.addressbookapplication.libraries.RandomUserApi.services.UserServiceAsync} instance.
     *
     */
    public UserServiceAsync userServicesAsync () {
        return getRestAdapter().create(UserServiceAsync.class);
    }

    /**
     * Return the {@link com.terro.services.UserServiceObservable} instance.
     *
     */
    /*public UserServiceObservable userServicesObservable () {
        return getRestAdapter().create(UserServiceObservable.class);
    }*/
}
