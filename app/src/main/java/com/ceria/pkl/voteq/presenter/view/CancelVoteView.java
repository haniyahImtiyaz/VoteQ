package com.ceria.pkl.voteq.presenter.view;

import android.util.Log;

import com.ceria.pkl.voteq.models.network.ApiClient;
import com.ceria.pkl.voteq.models.network.CancelVoteClient;
import com.ceria.pkl.voteq.presenter.callback.CancelVoteCallBack;
import com.ceria.pkl.voteq.presenter.viewinterface.VotingInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by win 8 on 10/22/2016.
 */
public class CancelVoteView implements CancelVoteCallBack{
    VotingInterface votingInterface;

    public CancelVoteView(VotingInterface votingInterface) {
        this.votingInterface = votingInterface;
    }

    @Override
    public void callCancelVote(String token, String vote_id) {
        if(votingInterface != null){
            votingInterface.showProgress();
        }
        CancelVoteClient cancelVoteClient = ApiClient.getClient().create(CancelVoteClient.class);
        Call<Response<Void>> call = cancelVoteClient.cancelVote(token, vote_id);
        call.enqueue(new Callback<Response<Void>>() {
            @Override
            public void onResponse(Call<Response<Void>> call, Response<Response<Void>> response) {
                if(response.code() == 204){
                    votingInterface.hideProgress();
                    votingInterface.onSucceededCancelVote();
                }else{
                    Log.d("LOG", "response code : " + response.code());
                    votingInterface.hideProgress();
                    votingInterface.setCredentialError();
                }
            }

            @Override
            public void onFailure(Call<Response<Void>> call, Throwable t) {
                votingInterface.hideProgress();
                votingInterface.onNetworkFailure();
            }
        });
    }

    @Override
    public void onDestroy() {
        votingInterface = null;
    }
}
