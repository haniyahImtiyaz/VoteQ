package com.ceria.pkl.voteq.models.network;

import com.ceria.pkl.voteq.models.pojo.GetProfileResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by Haniyah on 12/21/2016.
 */

public interface GetDetailUserClient {
    @GET("users/{id}")
    Call<GetProfileResponse> getDetailUser(@Path("id") String id);
}

