package com.guillermo.addressbookapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.guillermo.addressbookapplication.R;
import com.guillermo.addressbookapplication.activities.ContactDetailActivity;
import com.guillermo.addressbookapplication.app.App;
import com.guillermo.addressbookapplication.fragments.ContactDetailFragment;
import com.guillermo.addressbookapplication.libraries.ImageLoader.ImageLoader;
import com.guillermo.addressbookapplication.network.ContactData;
import com.guillermo.addressbookapplication.utils.StringUtils;

import java.util.List;

/**
 * Created by alvaregd on 12/05/16.
 * Populates our list of fetched Contacts
 */
public class ContactRecyclerViewAdapter
        extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ViewHolder> {

    /** List containing our data **/
    private final List<ContactData> mValues;

    /** the activity context **/
    private Context context;

    /** helper object that helps use retrieve images from the network on an on-demand basis */
    private ImageLoader imageLoader;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;


    public ContactRecyclerViewAdapter(List<ContactData> items, Context context) {
        mValues = items;
        this.context = context;
        mTwoPane = false;
        imageLoader = ((App) context.getApplicationContext()).getImageLoader();
    }

    /** getters/setters */
    public void setmTwoPane(boolean mTwoPane) {this.mTwoPane = mTwoPane;}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_content, parent, false);
        return new ViewHolder(view);
    }

    /** When a recyclerview item comes into view, we get a viewholder and fill it with information
     * In this case, a name and a thumbnail picture */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        //set the name
        String fullName =
                StringUtils.capitalizeFirst(mValues.get(position).getData().getUser().getName().getFirst()) +
                " " +
                StringUtils.capitalizeFirst(mValues.get(position).getData().getUser().getName().getLast());

        holder.mContentView.setText(fullName);

        //set the image
        imageLoader.DisplayImage(
                mValues.get(position).getData().getUser().getPicture().getThumbnail(),
                holder.mThumbnailView
        );

        //Set Ripple Effect action
        ((RippleView)holder.mView).setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ContactDetailFragment.ARG_ITEM_ID, holder.mItem.getId());
                    ContactDetailFragment fragment = new ContactDetailFragment();
                    fragment.setArguments(arguments);
                    ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contact_detail_container, fragment)
                            .commit();
                } else {
                    Context context = rippleView.getContext();
                    Intent intent = new Intent(context, ContactDetailActivity.class);
                    intent.putExtra(ContactDetailFragment.ARG_ITEM_ID, holder.mItem.getId());

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**  Manage our Recycler View's ViewHolder */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mThumbnailView;
        public final TextView mContentView;
        public ContactData mItem;
        public final RippleView rippleView;

        public ViewHolder(View view) {
            super(view);

            //get ViewHolder UI
            mView = view;
            mThumbnailView = (ImageView) view.findViewById(R.id.iv_profile_pic);
            mContentView = (TextView) view.findViewById(R.id.content);

            //configure our ripple effect
            rippleView = (RippleView) view.findViewById(R.id.ripple_view);
            rippleView.setRippleAlpha(150);
            rippleView.setRippleDuration(150);
            rippleView.setZooming(true);
            rippleView.setZoomDuration(150);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
