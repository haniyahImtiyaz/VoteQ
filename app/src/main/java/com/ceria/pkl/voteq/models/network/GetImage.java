package com.ceria.pkl.voteq.models.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Haniyah on 12/21/2016.
 */

public interface GetImage {
    @Streaming
    @GET()
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Header("Authorization") String authorization,  @Url String fileUrl);
}
