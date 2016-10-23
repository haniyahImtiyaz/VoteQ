package com.ceria.pkl.voteq.models.network;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by win 8 on 10/23/2016.
 */
public interface DeleteVoteClient {
    @DELETE("user/votes/{id}")
    Call<Response<Void>> deleteVote(@Header("Authorization") String authorization, @Path("id") String id);
}
