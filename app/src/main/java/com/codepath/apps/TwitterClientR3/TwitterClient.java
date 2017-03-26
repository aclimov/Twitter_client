package com.codepath.apps.TwitterClientR3;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.apps.TwitterClientR3.models.Tweet;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi .class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "lJxNkOeRzlosOa5gjBHOxJpUs";       // Change this
	public static final String REST_CONSUMER_SECRET = "bRQMy3Wx8SAXSl5DSANV9Ds9Rn1mTYeZuHlrdkMOtH3uhFnM4C"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletwits"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here


	public void getHomeTimeLine(long minId,AsyncHttpResponseHandler handler )
	{
		String apiUrl = getApiUrl("statuses/home_timeline.json");

		RequestParams params = new RequestParams();
		params.put("count",5);
		if(minId==0) {
			params.put("since_id", 1);
		}else{
			params.put("max_id", minId);
		}
		params.put("tweet_mode","extended");
		client.get(apiUrl,params,handler);
	}

	public void getNewTweets(long maxId,AsyncHttpResponseHandler handler )
	{
		String apiUrl = getApiUrl("statuses/home_timeline.json");

		RequestParams params = new RequestParams();
		params.put("count",5);
		if(maxId==0) {
			params.put("since_id", 1);
		}else{
			params.put("since_id", maxId);
		}
		client.get(apiUrl,params,handler);
	}


	public void createTweet(Tweet tweet, AsyncHttpResponseHandler handler)
	{
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status",tweet.getBody());
		client.post(apiUrl,params,handler);
	}

	//in_reply_to_status_id

	public void replyToTweet(Tweet tweetReplyTo, Tweet tweet,AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status",tweet.getBody());
		params.put("in_reply_to_status_id",tweet.getUid());
		client.post(apiUrl,params,handler);
	}



	//compose tweet

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */

}
