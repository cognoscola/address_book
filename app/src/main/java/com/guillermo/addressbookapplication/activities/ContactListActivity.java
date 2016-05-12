package com.guillermo.addressbookapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.guillermo.addressbookapplication.adapters.ContactRecyclerViewAdapter;
import com.guillermo.addressbookapplication.R;
import com.guillermo.addressbookapplication.network.ContactRetrieverListener;
import com.guillermo.addressbookapplication.network.ContactsRetriever;

/**
 * An activity representing a list of Contacts. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ContactDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ContactListActivity extends AppCompatActivity implements ContactRetrieverListener {

    /** The adapter that will fill the recycler view with data */
    private ContactRecyclerViewAdapter listAdapter;

    /** The view containing recyclable layouts for displaying our data */
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.contact_list);
        assert recyclerView != null;
        setupRecyclerView();

        if (findViewById(R.id.contact_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            listAdapter.setmTwoPane(true);
        }
    }

    /**
     * Populate the list of Contacts
     */
    private void setupRecyclerView() {

        //fetch data,
        listAdapter = new ContactRecyclerViewAdapter(ContactsRetriever.ITEMS, this);


        ContactsRetriever retriever = new ContactsRetriever(this);
        retriever.fetchData(1);


//        onDataReceived();
    }

    /**
     * Called when done receiving data from the network
     */
    @Override
    public void onDataReceived() {
        recyclerView.setAdapter(listAdapter);
    }
}
