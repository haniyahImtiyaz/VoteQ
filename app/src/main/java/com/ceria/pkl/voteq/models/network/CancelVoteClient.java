package com.ceria.pkl.voteq.models.network;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by win 8 on 10/22/2016.
 */
public interface CancelVoteClient {
    @DELETE("user/user_votes")
    Call<Response<Void>> cancelVote(@Header("Authorization") String authorization, @Query("vote_id") String vote_id);
}
