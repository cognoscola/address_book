package com.guillermo.addressbookapplication.libraries.RandomUserApi.services;

import com.guillermo.addressbookapplication.libraries.RandomUserApi.entities.UserRandomResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


public interface UserServiceAsync {

    /**
     * Returns a random user asynchronously.
     *
     * @param callback
     */
    @GET("/")
    public void userAsync(Callback<UserRandomResponse> callback);

    /**
     * Returns a list of random users asynchronously.
     *
     * @param results - Number of users
     * @param callback - Callback
     */
    @GET("/")
    public void listUserAsync(@Query("results") Integer results, Callback<UserRandomResponse> callback);

    /**
     * Returns a random user with a specific gender asynchronously.
     *
     * @param gender - Gender user to return (Male or Fermale)
     * @param callback - Callback
     */
    @GET("/")
    public void userWithGenderAsync(@Query("gender") String gender, Callback<UserRandomResponse> callback);

    /**
     * Returns a list of random users with a specific gender asynchronously.
     *
     * @param results - Number of users
     * @param gender - Gender user to return (Male or Female)
     * @param callback - Callback
     */
    @GET("/")
    public void listUsersWithGenderAsync(@Query("results") Integer results, @Query("gender") String gender, Callback<UserRandomResponse> callback);

    /**
     * Return a random user specified since the parameter seed is implemented in that way.
     *
     * @param seed - Specified a user
     * @param callback - Callback
     */
    @GET("/")
    public void userWithSeedAsync (@Query("seed") String seed, Callback<UserRandomResponse> callback);
}
