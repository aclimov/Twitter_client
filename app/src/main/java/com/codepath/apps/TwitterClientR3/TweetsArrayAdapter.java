package com.codepath.apps.TwitterClientR3;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.TwitterClientR3.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.R.attr.resource;
import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * Created by alex_ on 3/21/2017.
 */

//Taking the tweets object and turning them into views displayed in the list;
public class TweetsArrayAdapter  extends RecyclerView.Adapter<TweetsArrayAdapter.ViewHolder>{

    // List of tweets
    private List<Tweet> mTweets;
    // Store the context for easy access
    private Context mContext;


    public TweetsArrayAdapter(@NonNull Context context,  @NonNull List<Tweet> objects) {
        mTweets =objects;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTweets.size();
    }


    // inflate xml layout and return  viewHolder
    @Override
    public TweetsArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_tweet, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    //populate viewholder with data from list
    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(TweetsArrayAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Tweet tweet = mTweets.get(position);

        // Set item views based on your views and data model
        viewHolder.tvBody.setText(tweet.getBody());
        viewHolder.tvTimestamp.setText(tweet.getTimestamp());
        viewHolder.tvUsername.setText(tweet.getUser().getName());

        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl())
                .into(viewHolder.ivProfileImage);

    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public  TextView tvUsername;
        public TextView tvTimestamp;
        public TextView tvBody;
        public ImageView ivProfileImage;

       //Define constructor wichi accept entire row and find sub views
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvTimestamp = (TextView) itemView.findViewById(R.id.tvTimestamp);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
        }
    }
}
