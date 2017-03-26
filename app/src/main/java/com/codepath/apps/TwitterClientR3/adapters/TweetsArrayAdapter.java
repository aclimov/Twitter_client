package com.codepath.apps.TwitterClientR3.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.TwitterClientR3.models.Hashtag;
import com.codepath.apps.TwitterClientR3.models.Mention;
import com.codepath.apps.TwitterClientR3.R;
import com.codepath.apps.TwitterClientR3.RoundedCornersTransformation;
import com.codepath.apps.TwitterClientR3.models.Tweet;

import java.util.List;

import static com.codepath.apps.TwitterClientR3.R.id.tvLike;

/**
 * Created by alex_ on 3/21/2017.
 */

//Taking the tweets object and turning them into views displayed in the list;
public class TweetsArrayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int IMG = 0, VIDEO = 1, SIMPLE = 2, URL = 3;
    // List of tweets
    private List<Tweet> mTweets;
    // Store the context for easy access
    private Context mContext;

    public TweetsArrayAdapter(@NonNull Context context, @NonNull List<Tweet> objects) {
        mTweets = objects;
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        // Inflate the custom layout
        switch (viewType) {
            case IMG:
                View v1 = inflater.inflate(R.layout.item_tweet_img, parent, false);
                viewHolder = new ViewHolder_img(v1);
                break;
            default:
                View v2 = inflater.inflate(R.layout.item_tweet, parent, false);
                viewHolder = new ViewHolder_simple(v2);
                break;
        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        String type = mTweets.get(position).getType();

        switch (type) {
            case "photo":
                return IMG;
            default:
                return SIMPLE;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case IMG:
                ViewHolder_img vh1 = (ViewHolder_img) viewHolder;
                configureViewHolder_img(vh1, position);
                break;
            default:
                ViewHolder_simple vh2 = (ViewHolder_simple) viewHolder;
                configureViewHolder_simple(vh2, position);
                break;
        }
    }

    private void configureViewHolder_img(ViewHolder_img vh1, int position) {
        // Get the data model based on position
        Tweet tweet = mTweets.get(position);
        fetchCommon(vh1, tweet);

        //load image from media
        Glide.with(getContext()).load(tweet.getMediaObjects().get(0).getMediaUrl())
                .bitmapTransform(new RoundedCornersTransformation(getContext(), 15, 2))
                .into(vh1.ivTweetMedia);

    }

    private void configureViewHolder_simple(ViewHolder_simple vh2, int position) {
        // Get the data model based on position
        Tweet tweet = mTweets.get(position);
        fetchCommon(vh2, tweet);
    }

    //populate main fields
    public void fetchCommon(ViewHolder_simple viewHolder, Tweet tweet) {

        // Set item views based on your views and data model

        SpannableString ss = new SpannableString(tweet.getBody());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                // startActivity(new Intent(MyActivity.this, NextActivity.class));
               // Toast.makeText(getContext(), "clickable span", Toast.LENGTH_LONG).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        for(Hashtag ht:tweet.getHashtags()){
            ss.setSpan(clickableSpan, ht.start, ht.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for(Mention mt:tweet.getMentions()){
            ss.setSpan(clickableSpan, mt.start, mt.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        viewHolder.tvBody.setText(ss);
        viewHolder.tvBody.setMovementMethod(LinkMovementMethod.getInstance());
        viewHolder.tvBody.setHighlightColor(Color.CYAN);


        viewHolder.tvTimestamp.setText(tweet.getTimestamp());
        viewHolder.tvUsername.setText( tweet.getUser().getScreenName());
        viewHolder.tvDisplayName.setText(tweet.getUser().getName());

        if(tweet.getFavoriteCount()>0){
            viewHolder.tvLike.setText(String.valueOf(tweet.getFavoriteCount()));
        }

        if(tweet.getRetweetCount()>0){
            viewHolder.tvRetweet.setText(String.valueOf(tweet.getRetweetCount()));
        }

        if (!TextUtils.isEmpty(tweet.getRetweetedBy())) {
            viewHolder.tvRetweeted.setText(tweet.getRetweetedBy());
        } else {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewHolder.llLeftColumn.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

            viewHolder.llLeftColumn.setLayoutParams(params); //causes layout update*/
        }


        Glide.with(getContext()).load(tweet.getUser().getProfileImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(getContext(), 15, 2))
                .into(viewHolder.ivProfileImage);
    }

    public static class ViewHolder_simple extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvUsername;
        public TextView tvTimestamp;
        public TextView tvBody;
        public ImageView ivProfileImage;
        public TextView tvDisplayName;
        public TextView tvRetweeted;
        public RelativeLayout llLeftColumn;

        public TextView tvRetweet;
        public TextView tvLike;

        //Define constructor wichi accept entire row and find sub views
        public ViewHolder_simple(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tvUsername = (TextView) itemView.findViewById(R.id.tvName);
            tvTimestamp = (TextView) itemView.findViewById(R.id.tvTimestamp);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvDisplayName = (TextView) itemView.findViewById(R.id.tvDisplayName);
            tvRetweeted = (TextView) itemView.findViewById(R.id.tvRetweeted);

            llLeftColumn = (RelativeLayout) itemView.findViewById(R.id.llLeftColumn);

            tvRetweet = (TextView) itemView.findViewById(R.id.tvRetweet);
            tvLike = (TextView) itemView.findViewById(R.id.tvLike);
        }
    }

    public static class ViewHolder_img extends ViewHolder_simple {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        public ImageView ivTweetMedia;

        //Define constructor wichi accept entire row and find sub views
        public ViewHolder_img(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            ivTweetMedia = (ImageView) itemView.findViewById(R.id.ivTweetMedia);
        }
    }
}
