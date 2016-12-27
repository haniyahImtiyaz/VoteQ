package com.ceria.pkl.voteq.models.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by win 8 on 10/21/2016.
 */
public class Vote {
    @SerializedName("id")
    public String id;
    @SerializedName("title")
    public String title;
    @SerializedName("status")
    public String status;
    @SerializedName("image")
    public String image;
    @SerializedName("description")
    public String description;
    @SerializedName("category")
    public String category;
    @SerializedName("started_at")
    public String started_at;
    @SerializedName("ended_at")
    public String ended_at;
    @SerializedName("vote_pict_url")
    public String vote_pict_url;
    @SerializedName("creator")
    public User user;
    @SerializedName("total_participant")
    public int participant;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("options")
    public Option[] options;
}
