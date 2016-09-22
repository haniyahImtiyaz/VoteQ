package com.ceria.pkl.voteq.models.pojo;
import com.google.gson.annotations.SerializedName;
/**
 * Created by root on 23/09/16.
 */
public class LoginResponse {
    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public Login data;
}
