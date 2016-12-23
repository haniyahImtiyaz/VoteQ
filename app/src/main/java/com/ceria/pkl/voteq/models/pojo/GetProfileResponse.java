package com.ceria.pkl.voteq.models.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Haniyah on 12/21/2016.
 */

public class GetProfileResponse {
    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public User data;
}
