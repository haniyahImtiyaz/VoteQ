package com.ceria.pkl.voteq.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 21/09/16.
 */
public class LoginResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("id")
    private int id;
    @SerializedName("email")
    private String email;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("auth_token")
    private String authToken;

    public LoginResponse(String status, int id, String email, String createdAt, String updatedAt, String authToken){
        this.status = status;
        this.id = id;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.authToken = authToken;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}