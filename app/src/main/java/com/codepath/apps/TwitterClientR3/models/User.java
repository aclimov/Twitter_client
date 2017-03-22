package com.codepath.apps.TwitterClientR3.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alex_ on 3/21/2017.
 */

public class User {

    //List of attributes;

    private String name;
    private long uid;
    private String screenName;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    private String profileImageUrl;

    //deserialize the user json=> User
    public static User fromJsonObject(JSONObject jsonObject)
    {
        User user = new User();

        try {
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getLong("id");
            user.screenName=jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return user;
    }
}
