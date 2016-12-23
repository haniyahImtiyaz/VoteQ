package com.ceria.pkl.voteq.models.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by win 8 on 10/21/2016.
 */
public class Option {
    @SerializedName("id")
    public String id;
    @SerializedName("options")
    public String title;
    @SerializedName("percentage")
    public String percentage;
    @SerializedName("total_voter")
    public int total_voter;
    @SerializedName("image")
    public String image;
}
