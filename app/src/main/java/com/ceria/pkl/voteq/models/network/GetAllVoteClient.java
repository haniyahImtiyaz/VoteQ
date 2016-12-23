package com.ceria.pkl.voteq.models.network;

import com.ceria.pkl.voteq.models.pojo.GetAllVoteResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by win 8 on 10/21/2016.
 */
public interface GetAllVoteClient {
    @GET("votes")
    Call<GetAllVoteResponse> getAllVote();
}
