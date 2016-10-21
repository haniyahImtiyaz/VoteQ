package com.ceria.pkl.voteq.models.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by win 8 on 10/1/2016.
 */
public class User {
    @SerializedName("id")
    public String id;
    @SerializedName("email")
    public String email;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("updated_at")
    public String updatedAt;
    @SerializedName("auth_token")
    public String authToken;
    @SerializedName("forgot_code")
    public String forgot_code;
}
