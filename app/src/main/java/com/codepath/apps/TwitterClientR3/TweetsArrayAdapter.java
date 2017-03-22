package com.codepath.apps.TwitterClientR3;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.TwitterClientR3.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.R.attr.resource;

/**
 * Created by alex_ on 3/21/2017.
 */

//Taking the tweets object and turning them into views displayed in the list;
public class TweetsArrayAdapter  extends ArrayAdapter<Tweet>{

    public TweetsArrayAdapter(@NonNull Context context,  @NonNull List<Tweet> objects) {
        super(context, 0, objects);
    }

    //Override and setup custom template

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get tweets
        Tweet tweet = getItem(position);

        //inflate template
            if(convertView==null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet,parent,false);
            }
        //find subviews to fill data in template
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvTimestamp = (TextView)convertView.findViewById(R.id.tvTimestamp);
        //populate data in subviews
        tvUserName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        tvTimestamp.setText(tweet.getTimestamp());

        ivProfileImage.setImageResource(android.R.color.transparent);  //cleaer old image for recycler view
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);


        //return view

        return convertView;
    }
}
