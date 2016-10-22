package com.ceria.pkl.voteq.models.network;

import com.ceria.pkl.voteq.models.pojo.SpecificVoteResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by win 8 on 10/21/2016.
 */
public interface SpecificVoteClient {
    @GET("user/votes/{id}")
    Call<SpecificVoteResponse> specificVote(@Header("Authorization") String authorization, @Path("id") String id);
}
