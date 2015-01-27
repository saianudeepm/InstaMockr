package com.salome.instamockr.model;

import android.graphics.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by saianudeepm on 1/26/15.
 */
public class InstagramPost {
    private String userName;
    private String caption;
    private String imageUrl;
    private String userProfileImageUrl;
    private int likes;
    private long createdTime;

    public InstagramPost(String userName, String caption, String imageUrl, String userProfileImageUrl) {
        this.userName = userName;
        this.caption = caption;
        this.imageUrl = imageUrl;
        this.userProfileImageUrl = userProfileImageUrl;
    }

    public InstagramPost(String userName, String caption, String imageUrl, String userProfileImageUrl, int likes, long createdTime) {
        this.userName = userName;
        this.caption = caption;
        this.imageUrl = imageUrl;
        this.userProfileImageUrl = userProfileImageUrl;
        this.likes = likes;
        this.createdTime = createdTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserProfileImageUrl() {
        return userProfileImageUrl;
    }

    public void setUserProfileImageUrl(String userProfileImageUrl) {
        this.userProfileImageUrl = userProfileImageUrl;
    }
    
    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
    
    public static ArrayList<InstagramPost> getMyFakePosts(){

        ArrayList<InstagramPost> posts = new ArrayList<InstagramPost>();

        posts.add(new InstagramPost("AnudeepLife","THis is life","",""));
        posts.add(new InstagramPost("GreataviNash","Hurry I got into a college", "",""));
        posts.add(new InstagramPost("Vregonda","Yeah I got a new job", "",""));
        return posts;
    }

    // Constructor to convert JSON object into a Java class instance
    public InstagramPost(JSONObject object){
        
        try {
            this.userName = object.getJSONObject("user").getString("username");
            this.caption = object.getJSONObject("caption").getString("text");
            this.imageUrl = object.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
            this.userProfileImageUrl = object.getJSONObject("user").getString("profile_picture");
            this.likes = Integer.parseInt(object.getJSONObject("likes").getString("count"));
            this.createdTime = Long.parseLong(object.getString("created_time"));
        } catch (JSONException e) {
            System.out.println("A json exception has occured while parsing the post");
            e.printStackTrace();
        }
    }

    // Factory method to convert an array of JSON objects into a list of objects
    // User.fromJson(jsonArray);
    public static ArrayList<InstagramPost> fromJson(JSONArray jsonObjects) {
        ArrayList<InstagramPost> posts = new ArrayList<InstagramPost>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                posts.add(new InstagramPost(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return posts;
    } 

}
