package com.codepath.apps.TwitterClientR3.models;

import android.text.format.DateUtils;

import com.codepath.apps.TwitterClientR3.MyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

@Table(database = MyDatabase.class)
@Parcel(analyze = Tweet.class)
public class Tweet extends BaseModel    {

    public void setBody(String body) {
        this.body = body;
    }

    @Column
    private String body;

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Column
    @PrimaryKey
    long uid;

    @Column
    @ForeignKey(saveForeignKeyModel = false)
    User user;

    @Column
    String createdAt;

    ArrayList<Media> mediaObjects;

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getTimestamp() {
        return getRelativeTimeAgo(createdAt);
    }

    public String getRelativeTimeAgo(String rawJsonDate) {

        String relativeDate = "";
        if(rawJsonDate==null) return relativeDate;
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);


        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public static Tweet fromJson(JSONObject jsonObject){
        Tweet tweet = new Tweet();
        try {
            String fullText =jsonObject.getString("full_text");
            JSONArray range = jsonObject.getJSONArray("display_text_range");
            tweet.body =fullText.substring((Integer)range.get(0),(Integer)range.get(1));
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJsonObject(jsonObject.getJSONObject("user"));

           if(jsonObject.has("extended_entities")){
                tweet.mediaObjects = Media.fromJsonArray(jsonObject.getJSONObject("extended_entities"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



        return tweet;
    }

    public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray){
        ArrayList<Tweet> tweets = new ArrayList<>();

        for (int i=0;i<jsonArray.length();i++)
        {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJson(tweetJson);

                if(tweet!=null){
                    tweets.add(tweet);
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
                continue;
            }
        }

        return tweets;
    }

}
