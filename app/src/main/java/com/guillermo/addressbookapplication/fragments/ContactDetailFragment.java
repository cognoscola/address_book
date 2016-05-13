package com.guillermo.addressbookapplication.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.guillermo.addressbookapplication.R;
import com.guillermo.addressbookapplication.activities.ContactDetailActivity;
import com.guillermo.addressbookapplication.activities.ContactListActivity;
import com.guillermo.addressbookapplication.app.App;
import com.guillermo.addressbookapplication.libraries.ImageLoader.ImageLoader;
import com.guillermo.addressbookapplication.network.ContactData;
import com.guillermo.addressbookapplication.network.ContactsRetriever;
import com.guillermo.addressbookapplication.utils.StringUtils;

/**
 * A fragment representing a single Contact detail screen.
 * This fragment is either contained in a {@link ContactListActivity}
 * in two-pane mode (on tablets) or a {@link ContactDetailActivity}
 * on handsets.
 */
public class ContactDetailFragment extends Fragment {
    private static final String TAG = ContactDetailFragment.class.getName();

    private String itemId = "";

    private Context context;

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
     * Helper object that helps us load and cache image data
     */
    private ImageLoader imageLoader;

    /**
     * reference to the profile image
     */
    private ImageView bannerImageView;

    /**
     * Modify the visibility of the Contact's Profile Picture as the AppBarLayout collapses and
     * expands
     */
    private AppBarLayout.OnOffsetChangedListener appBarLayoutListener = new AppBarLayout.OnOffsetChangedListener() {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

            //only show the view (by changing alpha value) when we are collapsing
            if (verticalOffset == 0) {
                bannerImageView.setAlpha(1.0f);
            }else{
                if (Math.abs(verticalOffset) > 255) {
                    bannerImageView.setAlpha( (0.0f));
                }else{
                    bannerImageView.setAlpha( (float) ((1.0 - (float)(Math.abs(verticalOffset)/255.0))));
                }
            }
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactDetailFragment(){
    }

    public static ContactDetailFragment newInstance(){
        return new ContactDetailFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
                itemId = getArguments().getString(ARG_ITEM_ID);

            if (itemId != null ) {
                if (!itemId.isEmpty()) {

                    mItem =ContactsRetriever.contacts_map.get(itemId);

                    //configure the views in the parent activity
                    configureParentActivityViews();

                }
            }else{
                Activity activity = this.getActivity();
                Toast.makeText(activity,"Oops, Something went wrong!", Toast.LENGTH_LONG).show();
                getActivity().onBackPressed();
            }
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //When layout configuration changes (screen rotates), both activity and fragment
        //data is destroyed. We have to recreate the activity views here.
        configureParentActivityViews();
    }

    /**
     * Configure the views in the parent Activity.
     */
    private void configureParentActivityViews(){
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) ((Activity)context).findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(StringUtils.capitalizeFirst(mItem.getData().getUser().getName().getFirst()));
        }

        bannerImageView= (ImageView) ((Activity)context).findViewById(R.id.contact_banner);
        if (bannerImageView != null) {
            if (imageLoader != null) {
                imageLoader.DisplayImage(mItem.getData().getUser().getPicture().getLarge(), bannerImageView);
            }
        }

        //Listen to when the Appbarlayout changes (because we scrolled)
        AppBarLayout layout = (AppBarLayout) ((Activity)context).findViewById(R.id.app_bar);
        if (layout != null) {
            layout.addOnOffsetChangedListener(appBarLayoutListener);
        }

        //Launch a SMS intent when the user presses the floating circle
        FloatingActionButton fab = (FloatingActionButton) ((Activity)context).findViewById(R.id.fab);
        if (fab != null) {

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("address",mItem.getData().getUser().getCell());
                    startActivity(smsIntent);
                }
            });
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_detail, container, false);

        //Populate the Details View
        try {
            if (mItem != null) {

                String fullname = StringUtils.capitalizeFirst(mItem.getData().getUser().getName().getTitle()
                        + " "
                        + StringUtils.capitalizeFirst(mItem.getData().getUser().getName().getFirst()
                        + " "
                        + StringUtils.capitalizeFirst(mItem.getData().getUser().getName().getLast())));

                ((TextView) rootView.findViewById(R.id.tv_full_name)).setText(fullname );
                ((TextView) rootView.findViewById(R.id.tv_adress)).setText(mItem.getData().getUser().getLocation().getStreet());
                ((TextView) rootView.findViewById(R.id.tv_city)).setText(
                        StringUtils.capitalizeFirst(mItem.getData().getUser().getLocation().getCity()));
                ((TextView) rootView.findViewById(R.id.tv_province)).setText(
                        StringUtils.capitalizeFirst(mItem.getData().getUser().getLocation().getState()));
                ((TextView) rootView.findViewById(R.id.tv_cell)).setText("Cell:" + mItem.getData().getUser().getCell());
                ((TextView) rootView.findViewById(R.id.tv_phone)).setText("Phone:" + mItem.getData().getUser().getPhone());
                ((TextView) rootView.findViewById(R.id.tv_email)).setText( mItem.getData().getUser().getEmail());
            }
        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        imageLoader = ((App) context.getApplicationContext()).getImageLoader();
    }
}
