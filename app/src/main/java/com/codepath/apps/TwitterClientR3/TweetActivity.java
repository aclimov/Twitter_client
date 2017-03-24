package com.codepath.apps.TwitterClientR3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.TwitterClientR3.R;
import com.codepath.apps.TwitterClientR3.models.Tweet;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class TweetActivity extends AppCompatActivity {


    TextView tvUserName;
    TextView tvFullName;
    TextView tvTweetDate;
    TextView tvBody;
    TextView tvTweetStatus;
    ImageView ivProfileImage;
    private Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);


        tvUserName=(TextView)findViewById(R.id.tvUserName);
        tvFullName=(TextView )findViewById(R.id.tvFullName);
        tvTweetDate=(TextView )findViewById(R.id.tvTweetDate);
        tvBody=(TextView )findViewById(R.id.tvBody);
        tvTweetStatus=(TextView)findViewById(R.id.tvTweetStatus);
        ivProfileImage=(ImageView )findViewById(R.id.ivProfileImage);

       tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        tvBody.setText(tweet.getBody());
        tvUserName.setText(tweet.getUser().getName());
        tvFullName.setText(tweet.getUser().getScreenName());
        tvTweetDate.setText(tweet.getCreatedAt());

       /* Picasso.with(getApplicationContext()).load(tweet.getUser().getProfileImageUrl())
                .into(ivProfileImage);*/


    }
}