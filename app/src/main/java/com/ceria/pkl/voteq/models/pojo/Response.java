package com.ceria.pkl.voteq.models.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayu on 23/10/16.
 */

public class Response {

    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public Response data;
}
