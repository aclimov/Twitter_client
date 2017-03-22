package com.codepath.apps.TwitterClientR3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.TwitterClientR3.R;
import com.codepath.apps.TwitterClientR3.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

   private  TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;

    private ListView lvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //init controls
        lvTweets = (ListView) findViewById(R.id.lvTweets);

        //create arraylist
        tweets = new ArrayList<Tweet>();
        //construct adapter from datasource
        aTweets =new TweetsArrayAdapter(this,tweets);
        //connectadapter with listview

        lvTweets.setAdapter(aTweets);

        client=TwitterApp.getRestClient();

        populateTimeLine();
    }


    private void populateTimeLine()
    {
       client.getHomeTimeLine(new JsonHttpResponseHandler(){
           @Override
           public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            //deserialize json
            //create models
            aTweets.addAll(Tweet.fromJsonArray(response));
           }

           @Override
           public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Log.d("DEBUG",errorResponse.toString());
           }
       });
    }
}
