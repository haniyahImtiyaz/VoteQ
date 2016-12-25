package com.ceria.pkl.voteq.presenter.view;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.ceria.pkl.voteq.models.network.ApiClient;
import com.ceria.pkl.voteq.models.network.UploadImageVote;
import com.ceria.pkl.voteq.presenter.viewinterface.UploadImageInterface;

import java.io.File;
import java.net.URI;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Haniyah on 12/23/2016.
 */

public class UploadImageVoteView {
    private UploadImageInterface uploadImageInterface;

    public UploadImageVoteView(UploadImageInterface uploadImageInterface) {
        this.uploadImageInterface = uploadImageInterface;
    }

    public void uploadFile(String filePath, String token, String voteId) {
        // create upload service client
        UploadImageVote service =
                ApiClient.getClient().create(UploadImageVote.class);

        File file = new File(filePath);
        RequestBody fileku = RequestBody.create(MediaType.parse("image/*"), file);

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), voteId);
        // finally, execute the request
        Call<ResponseBody> call = service.upload("Token token=" + token, fileku, name);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                if (response.code() == 201) {
                    Log.d("Upload", "success");
                    uploadImageInterface.hideProgress();
                    uploadImageInterface.navigateToHome();
                } else {
                    Log.d("Upload", String.valueOf(response.code()));
                    uploadImageInterface.hideProgress();
                    uploadImageInterface.setCredentialError();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Upload error:", t.getMessage());
                uploadImageInterface.hideProgress();
                uploadImageInterface.onNetworkFailure();
            }
        });
    }
}
