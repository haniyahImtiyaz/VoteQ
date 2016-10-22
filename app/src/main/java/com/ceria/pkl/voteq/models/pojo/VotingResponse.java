package com.ceria.pkl.voteq.models.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by win 8 on 10/22/2016.
 */
public class VotingResponse {
    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public UserVote data;
}
