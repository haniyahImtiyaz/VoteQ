package com.ceria.pkl.voteq.models.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 21/09/16.
 */
public class Login {
    @SerializedName("id")
    public int id;
    @SerializedName("email")
    public String email;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("updated_at")
    public String updatedAt;
    @SerializedName("auth_token")
    public String authToken;
}