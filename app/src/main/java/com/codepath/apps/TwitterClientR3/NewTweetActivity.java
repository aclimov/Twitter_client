package com.codepath.apps.TwitterClientR3;

import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.TwitterClientR3.R;
import com.codepath.apps.TwitterClientR3.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NewTweetActivity extends AppCompatActivity {

    private TwitterClient client;

    EditText editText;
    TextView tvCharsLeft;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tweet);

        button = (Button) findViewById(R.id.button);
        tvCharsLeft = (TextView)findViewById(R.id.textView);
        editText=(EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                int charLeft = 140-editText.length();
                tvCharsLeft.setText(String.valueOf(charLeft));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusUpdate();
            }
        });
    }

    private void statusUpdate()
    {
        client = TwitterApp.getRestClient();
        Tweet tweet = new Tweet();
        tweet.setBody(editText.getText().toString());

        client.createTweet(tweet,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               Tweet newTweet = Tweet.fromJson(response);
               //return Tweet object back to timeline
                //it now has Ids and can be inserted into mTweets list

                Intent data = new Intent();
                // Pass relevant data back as a result
                data.putExtra("tweet", Parcels.wrap(newTweet));

                // Activity finished ok, return the data
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish(); // closes the activity, pass data to parent
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }
}
