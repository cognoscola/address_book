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
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ContactsRetriever {
    private static final String TAG = ContactsRetriever.class.getName();

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ContactData> ITEMS = new ArrayList<ContactData>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ContactData> ITEM_MAP = new HashMap<String, ContactData>();

    private ContactRetrieverListener listener;


    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }


    public ContactsRetriever(ContactRetrieverListener listener) {
        this.listener = listener;
    }

    private static void addItem(ContactData item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static ContactData createDummyItem(int position) {
        return new ContactData(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


    /**
     * A dummy item representing a piece of content.
     */
    public static class ContactData {
        public final String id;
        public final String content;
        public final String details;

        public ContactData(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }


    /**
     * Asynchronously fetch contacts from the network
     * @param numContacts the number of contacts to fetch
     */
    public void fetchData(int numContacts) {

        RandomUser randomUser = new RandomUser();
        randomUser.setIsDebug(true);

        UserServiceAsync userServiceAsync = randomUser.userServicesAsync();

        Log.d(TAG, "fetchData: FETCHING");
        userServiceAsync.userAsync(new Callback<UserRandomResponse>() {
            @Override
            public void success(UserRandomResponse userRandomResponse, Response response) {

                Log.d(TAG, "status: " + response.getStatus());
                for (Result result : userRandomResponse.getResults()) {
                    Log.d(TAG, "Fetched:" + result.getUser().getName().getFirst());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure: ",error.getCause());
            }
        });
    }

}
