package com.ceria.pkl.voteq.presenter.view;

import android.util.Log;

import com.ceria.pkl.voteq.models.network.ApiClient;
import com.ceria.pkl.voteq.models.network.CreateVoteClient;
import com.ceria.pkl.voteq.models.pojo.CreateVoteResponse;
import com.ceria.pkl.voteq.presenter.callback.CreateVoteCallBack;
import com.ceria.pkl.voteq.presenter.interactor.CreateVoteInteractor;
import com.ceria.pkl.voteq.presenter.interactorImpl.CreateVoteInteractorImpl;
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
    private String id;

    public String getId() {
        return id;
    }

    public CreateVoteView(CreateVoteInterface createVoteInterface) {
        this.createVoteInterface = createVoteInterface;
    }

    @Override
    public void callCreateVote(String token, String title, String description, String started, String ended, List<String> options) {
        if (createVoteInterface != null) {
            createVoteInterface.showProgress();
        }
        CreateVoteClient createVoteClient = ApiClient.getClient().create(CreateVoteClient.class);
        Call<CreateVoteResponse> call = createVoteClient.createVote("Token token="+token, title, description, started, ended, options);
        call.enqueue(new Callback<CreateVoteResponse>() {
            @Override
            public void onResponse(Call<CreateVoteResponse> call, Response<CreateVoteResponse> response) {
                if (response.code() == 201) {
                    id = response.body().data.id;
                    createVoteInterface.onSuccedeed();
                } else {
                    Log.d("LOG", "response code: " + response.code());
                    createVoteInterface.hideProgress();
                    createVoteInterface.setCredentialError();
                }
            }

            @Override
            public void onFailure(Call<CreateVoteResponse> call, Throwable t) {
                createVoteInterface.onNetworkFailure();
            }
        });
    }

    @Override
    public void onDestroy() {
        createVoteInterface = null;
    }

}
