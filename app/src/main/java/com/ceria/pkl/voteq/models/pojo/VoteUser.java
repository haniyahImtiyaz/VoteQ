package com.ceria.pkl.voteq.models.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by win 8 on 10/21/2016.
 */
public class VoteUser {
    @SerializedName("id")
    public String id;
    @SerializedName("vote")
    public Vote vote;
    @SerializedName("choosen_option")
    public Option choosenOption;
    @SerializedName("options")
    public Option[] options;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("updated_at")
    public String updatedAt;

}
