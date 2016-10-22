package com.ceria.pkl.voteq.models.network;

import com.ceria.pkl.voteq.models.pojo.CreateVoteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by win 8 on 10/21/2016.
 */
public interface CreateVoteClient {
    @POST("user/votes")
    Call<CreateVoteResponse> createVote(@Header("Authorization") String authorization, @Query("title") String title, @Query("options[]") List<String> options, @Query("is_open") Boolean is_open);
}
