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
 * Used to retrieve dummy contact data from the network
 */
public class ContactsRetriever {
    private static final String TAG = ContactsRetriever.class.getName();

    /**
     * An array of sample (Contact) items.
     */
    public static final List<ContactData> contacts = new ArrayList<ContactData>();

    /**
     * A map of sample (Contact) items, by ID.
     */
    public static final Map<String, ContactData> contacts_map = new HashMap<String, ContactData>();

    private ContactRetrieverListener listener;

    private static final int COUNT = 25;

/*    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }*/

    public ContactsRetriever(ContactRetrieverListener listener) {
        this.listener = listener;
    }

    private static void addItem(ContactData data) {
        contacts.add(data);
        contacts_map.put(data.getId(), data);
    }

  /*  private static ContactData createDummyItem(int position) {
        return new ContactData(String.valueOf(position), "Item " + position, makeDetails(position));
    }*/

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
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

                Log.d(TAG, "status: " + response.getStatus());

                for (int i = 0; i < userRandomResponse.getResults().size(); i++) {

                    Result result = userRandomResponse.getResults().get(i);
                    addItem(
                            new ContactData(
                                    String.valueOf(i),
                                    result
                            ));
                }

                listener.onDataReceived();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure: ",error.getCause());
                listener.onDataReceived();
            }
        });
    }


    public static void dispose(){
        contacts.clear();
        contacts_map.clear();
    }

}
