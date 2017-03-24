package com.codepath.apps.TwitterClientR3;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.TwitterClientR3.R;
import com.codepath.apps.TwitterClientR3.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 20;


    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private Set tweetIds;
    private TweetsArrayAdapter aTweets;

    private RecyclerView lvTweets;

    EndlessRecyclerViewScrollListener scrollListener;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);


        tweetIds=new HashSet();
        //init controls
        lvTweets = (RecyclerView) findViewById(R.id.lvTweets);
        //init arraylist
        tweets = new ArrayList<Tweet>();
        //construct adapter from datasource
        aTweets = new TweetsArrayAdapter(this, tweets);
        //connect adapter with recyclerView
        lvTweets.setAdapter(aTweets);

        // Set layout manager to position the items
        linearLayoutManager = new LinearLayoutManager(this);
        lvTweets.setLayoutManager(linearLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                populateTimeLine();
            }
        };
        lvTweets.addOnScrollListener(scrollListener);

        client = TwitterApp.getRestClient();
        populateTimeLine();
    }

    //Add tweets to main list and notify adapter
    private void addTweets(List<Tweet> newTweets) {
        //get item count fomr RecyclerView Adapter
        int curSize = aTweets.getItemCount();
        // replace this line with wherever you get new records
        for(Tweet ntweet :newTweets){
            if(!tweetIds.contains(ntweet.getUid())){
                tweets.add(ntweet);
                tweetIds.add(ntweet.getUid());
            }
        }
        //notify adapter to reflect changes
        aTweets.notifyItemRangeInserted(curSize, newTweets.size());
    }

    private void populateTimeLine(){
        long minId =0;


        if(tweets.size()>0) {
         minId = (long)Collections.min(tweetIds);
        }
        client.getHomeTimeLine(minId,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Tweet> newTweets = Tweet.fromJsonArray(response);
                addTweets(newTweets);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.compose:
                Intent i = new Intent(TimelineActivity.this,NewTweetActivity.class);
                startActivityForResult(i, REQUEST_CODE);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            Tweet tweet = Parcels.unwrap( data.getExtras().getParcelable("tweet"));
            if(!tweetIds.contains(tweet.getUid())){
                tweets.add(0,tweet);
                tweetIds.add(tweet.getUid());
            }
            aTweets.notifyItemInserted(0);
        }
    }

}
