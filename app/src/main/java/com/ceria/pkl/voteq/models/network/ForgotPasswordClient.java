package com.ceria.pkl.voteq.models.network;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by win 8 on 10/23/2016.
 */
public interface ForgotPasswordClient {
    @POST("/user/request_forgot_password")
    Call<Response<Void>> forgotPassword(@Query("email") String email);
}
