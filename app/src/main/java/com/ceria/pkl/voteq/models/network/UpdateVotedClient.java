package com.ceria.pkl.voteq.models.network;

import com.ceria.pkl.voteq.models.pojo.VotingResponse;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by win 8 on 10/22/2016.
 */
public interface UpdateVotedClient {
    @PUT("user/user_votes")
    Call<VotingResponse> voting(@Header("Authorization") String authorization, @Query("vote_id") String voteId, @Query("vote_option_id") String voteOptionId);
}

