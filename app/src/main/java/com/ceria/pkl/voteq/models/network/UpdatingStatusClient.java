package com.ceria.pkl.voteq.models.network;

import com.ceria.pkl.voteq.models.pojo.SpecificVoteResponse;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by win 8 on 10/23/2016.
 */
public interface UpdatingStatusClient {
    @PUT("user/votes/{vote_id}")
    Call<SpecificVoteResponse> updateStatus(@Path("vote_id") String vote_id, @Header("Authorization") String authorization, @Query("title") String title, @Query("is_open") Boolean is_open);
}
