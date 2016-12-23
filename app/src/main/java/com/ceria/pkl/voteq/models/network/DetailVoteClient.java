package com.ceria.pkl.voteq.models.network;

import com.ceria.pkl.voteq.models.pojo.DetailVoteResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by win 8 on 10/21/2016.
 */
public interface DetailVoteClient {
    @GET("votes/details/{id}")
    Call<DetailVoteResponse> detailVote( @Path("id") String id);
}
