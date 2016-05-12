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

import com.guillermo.addressbookapplication.R;
import com.guillermo.addressbookapplication.activities.ContactDetailActivity;
import com.guillermo.addressbookapplication.network.ContactsRetriever;
import com.guillermo.addressbookapplication.fragments.ContactDetailFragment;

import java.util.List;

/**
 * Created by alvaregd on 12/05/16.
 */
public class ContactRecyclerViewAdapter
        extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ViewHolder> {

    private final List<ContactsRetriever.ContactData> mValues;
    private Context context;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    public ContactRecyclerViewAdapter(List<ContactsRetriever.ContactData> items, Context context) {
        mValues = items;
        this.context = context;
        mTwoPane = false;

    }

    /** getters/setters */
    public void setmTwoPane(boolean mTwoPane) {this.mTwoPane = mTwoPane;}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ContactDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                    ContactDetailFragment fragment = new ContactDetailFragment();
                    fragment.setArguments(arguments);
                    ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contact_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ContactDetailActivity.class);
                    intent.putExtra(ContactDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mIdView;
        public final TextView mContentView;
        public ContactsRetriever.ContactData mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (ImageView) view.findViewById(R.id.iv_profile_pic);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
