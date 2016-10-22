package com.ceria.pkl.voteq.presenter.view;

import android.util.Log;

import com.ceria.pkl.voteq.models.network.ApiClient;
import com.ceria.pkl.voteq.models.network.CreateVoteClient;
import com.ceria.pkl.voteq.models.pojo.CreateVoteResponse;
import com.ceria.pkl.voteq.presenter.callback.CreateVoteCallBack;
import com.ceria.pkl.voteq.presenter.viewinterface.CreateVoteInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by win 8 on 10/21/2016.
 */
public class CreateVoteView implements CreateVoteCallBack {
    private CreateVoteInterface createVoteInterface;

    public CreateVoteView(CreateVoteInterface createVoteInterface) {
        this.createVoteInterface = createVoteInterface;
    }

    @Override
    public void callCreateVote(String token, final String title, final List<String> options, Boolean is_open) {
        if (createVoteInterface != null) {
            createVoteInterface.showProgress();
        }
        final CreateVoteClient createVoteClient = ApiClient.getClient().create(CreateVoteClient.class);
        Call<CreateVoteResponse> call = createVoteClient.createVote(token, title, options, is_open);
        call.enqueue(new Callback<CreateVoteResponse>() {
            @Override
            public void onResponse(Call<CreateVoteResponse> call, Response<CreateVoteResponse> response) {
                if (response.code() == 201) {
                    createVoteInterface.hideProgress();
                    createVoteInterface.onSuccedeed();
                    createVoteInterface.navigateToHome();
                } else {
                    Log.d("LOG", "response code: " + response.code());
                    createVoteInterface.hideProgress();
                    createVoteInterface.setCredentialError();
                }
            }

            @Override
            public void onFailure(Call<CreateVoteResponse> call, Throwable t) {
                createVoteInterface.hideProgress();
                createVoteInterface.onNetworkFailure();
            }
        });
    }

    @Override
    public void onDestroy() {
        createVoteInterface = null;
    }
}
