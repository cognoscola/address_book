package com.guillermo.addressbookapplication.fragments;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guillermo.addressbookapplication.R;
import com.guillermo.addressbookapplication.activities.ContactDetailActivity;
import com.guillermo.addressbookapplication.activities.ContactListActivity;
import com.guillermo.addressbookapplication.network.ContactData;
import com.guillermo.addressbookapplication.network.ContactsRetriever;

/**
 * A fragment representing a single Contact detail screen.
 * This fragment is either contained in a {@link ContactListActivity}
 * in two-pane mode (on tablets) or a {@link ContactDetailActivity}
 * on handsets.
 */
public class ContactDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy firstName this fragment is presenting.
     */
    private ContactData mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy firstName specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load firstName from a firstName provider.
            mItem = ContactsRetriever.contacts_map.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getData().getUser().getName().getFirst());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_detail, container, false);

        // Show the dummy firstName as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.contact_detail)).setText(mItem.getData().getUser().getEmail());
        }

        return rootView;
    }
}
