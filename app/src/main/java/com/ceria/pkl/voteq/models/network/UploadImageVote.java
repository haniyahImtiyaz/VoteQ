package com.ceria.pkl.voteq.models.network;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Haniyah on 12/23/2016.
 */

public interface UploadImageVote {
    @Multipart
    @POST("files/votes")
    Call<ResponseBody> upload(@Header("Authorization") String token,
                              @Part("raw_file\"; filename=\"image.jpg ") RequestBody file,
                              @Part("vote_id") RequestBody vote_id);
}
