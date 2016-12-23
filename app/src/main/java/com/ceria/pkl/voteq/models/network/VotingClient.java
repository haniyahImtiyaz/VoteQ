package com.ceria.pkl.voteq.models.network;

import com.ceria.pkl.voteq.models.pojo.VotingResponse;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by win 8 on 10/22/2016.
 */
public interface VotingClient {
    @POST("votes/participate")
    Call<VotingResponse> voting(@Header("Authorization") String authorization, @Query("vote_id") String voteId, @Query("choosen_index") int choosen_index);
}
