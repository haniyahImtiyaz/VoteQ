package com.ceria.pkl.voteq.models.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by win 8 on 10/1/2016.
 */
public class User {
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("gender")
    public String gender;
    @SerializedName("age")
    public int age;
    @SerializedName("date_of_birth")
    public String date_of_birth;
    @SerializedName("phone_number")
    public String phone_number;
    @SerializedName("city")
    public String city;
    @SerializedName("province")
    public String province;
    @SerializedName("latitude")
    public String latitude;
    @SerializedName("longitude")
    public String longitude;
    @SerializedName("image")
    public String image;
}
