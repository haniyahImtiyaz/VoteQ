package com.ceria.pkl.voteq.models.network;

import com.ceria.pkl.voteq.models.pojo.SignupResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by win 8 on 10/1/2016.
 */
public interface SignupClient {
    @POST("user")
    Call<SignupResponse> signUp(@Query("user[name]") String name,
                                @Query("user[email]") String email,
                                @Query("user[job]") String job,
                                @Query("user[password]") String password,
                                @Query("user[password_confirmation]") String pwd_confirmation,
                                @Query("user[date_of_birth]") String date,
                                @Query("user[gender]") String gender,
                                @Query("user[latitude]") double latitude,
                                @Query("user[longitude]") double longitude);
}
