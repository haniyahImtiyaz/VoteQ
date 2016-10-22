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
    @SerializedName("voter_count")
    public String voter_count;
    @SerializedName("user")
    public User user;
    @SerializedName("status")
    public String status;
    @SerializedName("options")
    public Option[] options;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("vote")
    public Vote vote;
    @SerializedName("choosen_option")
    public Option choosenOption;
}
