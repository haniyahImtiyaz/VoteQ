package com.ceria.pkl.voteq.presenter.view;

import android.util.Log;

import com.ceria.pkl.voteq.models.network.ApiClient;
import com.ceria.pkl.voteq.models.network.UpdateVotedClient;
import com.ceria.pkl.voteq.models.network.VotingClient;
import com.ceria.pkl.voteq.models.pojo.VotingResponse;
import com.ceria.pkl.voteq.presenter.callback.VotingCallBack;
import com.ceria.pkl.voteq.presenter.viewinterface.VotingInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by win 8 on 10/22/2016.
 */
public class VotingView implements VotingCallBack {
    private VotingInterface votingInterface;
    private Boolean voted = null;

    public VotingView(VotingInterface votingInterface, Boolean voted) {
        this.votingInterface = votingInterface;
        this.voted = voted;
    }

    @Override
    public void callVoting(String token, String id, String voted_option_id) {
        if (votingInterface != null) {
            votingInterface.showProgress();
        }
        Call<VotingResponse> call;
        if (voted == true) {
            UpdateVotedClient votingClient = ApiClient.getClient().create(UpdateVotedClient.class);
            call = votingClient.voting(token, id, voted_option_id);
        } else {
            VotingClient votingClient = ApiClient.getClient().create(VotingClient.class);
            call = votingClient.voting(token, id, voted_option_id);
        }
        call.enqueue(new Callback<VotingResponse>() {
            @Override
            public void onResponse(Call<VotingResponse> call, Response<VotingResponse> response) {
                if ((response.code() == 201) || (response.code() == 200)) {
                    votingInterface.hideProgress();
                    votingInterface.navigateToHome();
                } else {
                    Log.d("LOG", "response code : " + response.code());
                    votingInterface.hideProgress();
                    votingInterface.setCredentialError();
                }
            }

            @Override
            public void onFailure(Call<VotingResponse> call, Throwable t) {
                votingInterface.hideProgress();
                votingInterface.onNetworkFailure();
            }
        });
    }

    @Override
    public void onDestroy() {

    }
}
