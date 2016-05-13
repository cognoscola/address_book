package com.guillermo.addressbookapplication.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guillermo.addressbookapplication.adapters.ContactRecyclerViewAdapter;
import com.guillermo.addressbookapplication.R;
import com.guillermo.addressbookapplication.network.ContactData;
import com.guillermo.addressbookapplication.network.ContactRetrieverListener;
import com.guillermo.addressbookapplication.network.ContactsRetriever;

import java.lang.reflect.Type;
import java.util.List;

/**
 * An activity representing a list of Contacts. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ContactDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ContactListActivity extends AppCompatActivity implements ContactRetrieverListener {
    private static final String TAG = ContactListActivity.class.getName();

    private static final String DATA_KEY =  "DATA_KEY";

    /** The adapter that will fill the recycler view with data */
    private ContactRecyclerViewAdapter listAdapter;

    /** The view containing recyclable layouts for displaying our data */
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.contact_list);


        if (toolbar != null) {

            setSupportActionBar(toolbar);
            toolbar.setTitle(getTitle());
            toolbar.setBackgroundColor(Color.rgb(255,102,0));
        }

        if (recyclerView != null) {

            if (savedInstanceState == null) {
                setupRecyclerView();
            }

            if (findViewById(R.id.contact_detail_container) != null) {
                // The detail container view will be present only in the
                // large-screen layouts (res/values-w900dp).
                // If this view is present, then the
                // activity should be in two-pane mode.
                listAdapter.setmTwoPane(true);
            }
        }
    }

    /**
     * Populate the list of Contacts
     */
    private void setupRecyclerView() {

        //fetch data,
        ContactsRetriever retriever = new ContactsRetriever(this);
        retriever.fetchDataAsynchronously(1);
    }

    /**
     * Called when done receiving data from the network
     */
    @Override
    public void onDataReceived() {

        listAdapter = new ContactRecyclerViewAdapter(ContactsRetriever.contacts, this);
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        listAdapter = null;
        recyclerView.setAdapter(null);
        recyclerView = null;
        ContactsRetriever.dispose();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the fetched data while our activity is being destroyed
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ContactData>>() {}.getType();
        String data = gson.toJson(ContactsRetriever.contacts, listType);
        outState.putString(DATA_KEY, data);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<ContactData>>() {}.getType();
        String data = savedInstanceState.getString(DATA_KEY);

        List<ContactData> savedData = gson.fromJson(data, listType);
        if (savedData != null) {
            for(int i = 0; i < savedData.size(); i++) {
                ContactsRetriever.addItem(savedData.get(i));
            }
        }

        onDataReceived();

    }
}
