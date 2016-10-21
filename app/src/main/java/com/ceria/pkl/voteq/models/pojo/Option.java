package com.ceria.pkl.voteq.models.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by win 8 on 10/21/2016.
 */
public class Option {
    @SerializedName("id")
    public String id;
    @SerializedName("title")
    public String title;
    @SerializedName("count")
    public int count;
    @SerializedName("percentage")
    public double percentage;
    @SerializedName("vote_id")
    public String vote_id;
}
