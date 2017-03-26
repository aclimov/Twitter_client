package com.codepath.apps.TwitterClientR3.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.TwitterClientR3.LinkifiedTextView;
import com.codepath.apps.TwitterClientR3.R;
import com.codepath.apps.TwitterClientR3.RoundedCornersTransformation;
import com.codepath.apps.TwitterClientR3.models.Hashtag;
import com.codepath.apps.TwitterClientR3.models.Mention;
import com.codepath.apps.TwitterClientR3.models.Tweet;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class TweetActivity extends AppCompatActivity {

    TextView tvUserName;
    TextView tvFullName;
    TextView tvTweetDate;
    LinkifiedTextView tvBody;

    TextView tvReply;
    TextView tvRetweet;
    TextView tvLike;

    ImageView ivProfileImage;
    ImageView ivTweetMedia;
    private Tweet tweet;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }



        tvUserName=(TextView)findViewById(R.id.tvDisplayName);
        tvFullName=(TextView )findViewById(R.id.tvName);
        tvTweetDate=(TextView )findViewById(R.id.tvTweetDate);
        tvReply=(TextView )findViewById(R.id.tvReply);
        tvRetweet=(TextView )findViewById(R.id.tvRetweet);
        tvLike=(TextView )findViewById(R.id.tvLike);
        tvBody=(LinkifiedTextView)findViewById(R.id.tvBody);
        ivProfileImage=(ImageView)findViewById(R.id.ivProfileImage);
        ivTweetMedia=(ImageView)findViewById(R.id.ivTweetMedia);

        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        fillTweetBody(tweet.getBody());
        tvUserName.setText(tweet.getUser().getName());
        tvFullName.setText(tweet.getUser().getScreenName());
        tvTweetDate.setText(tweet.getCreatedAt());

        if(!TextUtils.isEmpty(tweet.getUser().getProfileImageUrl())){


            Glide.with(TweetActivity.this).load(tweet.getUser().getProfileImageUrl())
                    .bitmapTransform(new RoundedCornersTransformation(TweetActivity.this, 15, 2))
                    .into(ivProfileImage);
        }

        if(tweet.getType().equals("photo")){
            Glide.with(TweetActivity.this).load(tweet.getMediaObjects().get(0).getMediaUrl())
                    .bitmapTransform(new RoundedCornersTransformation(TweetActivity.this, 15, 2))
                    .into(ivTweetMedia);
        }

        if(tweet.getFavoriteCount()>0){
            tvLike.setText(String.valueOf(tweet.getFavoriteCount()));
        }

        if(tweet.getRetweetCount()>0){
            tvRetweet.setText(String.valueOf(tweet.getRetweetCount()));
        }
    }

    private void fillTweetBody(String body)
    {
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

        tvBody.setText(ss);
        tvBody.setMovementMethod(LinkMovementMethod.getInstance());
        tvBody.setHighlightColor(Color.CYAN);
    }
}
