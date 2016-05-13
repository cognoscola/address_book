package com.guillermo.addressbookapplication.network;

import android.util.Log;

import com.guillermo.addressbookapplication.libraries.RandomUserApi.RandomUser;
import com.guillermo.addressbookapplication.libraries.RandomUserApi.entities.Result;
import com.guillermo.addressbookapplication.libraries.RandomUserApi.entities.UserRandomResponse;
import com.guillermo.addressbookapplication.libraries.RandomUserApi.services.UserServiceAsync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Helper class to help use Retrieve data from the network
 */
public class ContactsRetriever {
    private static final String TAG = ContactsRetriever.class.getName();

    /**
     * An array of sample (Contact) items.
     */
    public static final List<ContactData> contacts = new ArrayList<ContactData>();

    /**
     * A map of sample (Contact) items, by ID so that we can fetch from other activites
     */
    public static final Map<String, ContactData> contacts_map = new HashMap<String, ContactData>();

    private ContactRetrieverListener listener;

    public ContactsRetriever(ContactRetrieverListener listener) {
        this.listener = listener;
    }

    public static void addItem(ContactData data) {
        contacts.add(data);
        contacts_map.put(data.getId(), data);
    }

    /**
     * Asynchronously fetch contacts from the network
     * @param numContacts the number of contacts to fetch
     */
    public void fetchDataAsynchronously(int numContacts) {

        RandomUser randomUser = new RandomUser();
        UserServiceAsync userServiceAsync = randomUser.userServicesAsync();

        userServiceAsync.listUserAsync(numContacts,new Callback<UserRandomResponse>() {
            @Override
            public void success(UserRandomResponse userRandomResponse, Response response) {

                //Store result data in our Lists/MAps
                for (int i = 0; i < userRandomResponse.getResults().size(); i++) {

                    Result result = userRandomResponse.getResults().get(i);
                    addItem(
                            new ContactData(
                                    String.valueOf(i),
                                    result
                            ));
                }

                //notify activity that we are done fetching
                listener.onDataReceived();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure: ",error.getCause());
                //notify activity that we are done fetching
                listener.onDataReceived();
            }
        });
    }

    /**
     * Housekeeping
     */
    public static void dispose(){
        contacts.clear();
        contacts_map.clear();
    }

}
