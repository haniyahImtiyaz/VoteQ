package com.ceria.pkl.voteq.models.network;

import com.ceria.pkl.voteq.models.pojo.LoginResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by root on 22/09/16.
 */
public interface LoginClient {
    @POST("users/sessions")
    Call<LoginResponse> signIn(@Query("email") String email, @Query("password") String password);
}