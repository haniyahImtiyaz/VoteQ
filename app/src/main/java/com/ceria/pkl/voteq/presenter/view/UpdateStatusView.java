package com.ceria.pkl.voteq.presenter.view;

import android.util.Log;

import com.ceria.pkl.voteq.models.network.ApiClient;
import com.ceria.pkl.voteq.models.network.UpdatingStatusClient;
import com.ceria.pkl.voteq.models.pojo.SpecificVoteResponse;
import com.ceria.pkl.voteq.presenter.callback.UpdateStatusCallBack;
import com.ceria.pkl.voteq.presenter.viewinterface.VotingInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by win 8 on 10/23/2016.
 */
public class UpdateStatusView implements UpdateStatusCallBack {
    private VotingInterface votingInterface;
    public Boolean is_open;

    public UpdateStatusView(VotingInterface votingInterface) {
        this.votingInterface = votingInterface;
    }

    @Override
    public void callUpdateStatus(final String vote_id, String token, String title, Boolean is_open) {
        this.is_open = is_open;
        if(votingInterface != null){
            votingInterface.showProgress();
        }
        UpdatingStatusClient updatingStatusClient = ApiClient.getClient().create(UpdatingStatusClient.class);
        Call<SpecificVoteResponse> call = updatingStatusClient.updateStatus(vote_id, token, title, is_open);
        call.enqueue(new Callback<SpecificVoteResponse>() {
            @Override
            public void onResponse(Call<SpecificVoteResponse> call, Response<SpecificVoteResponse> response) {
                if (response.code() == 200){
                    votingInterface.hideProgress();
                    votingInterface.onSucceededUpdateStatus();
                }else{
                    Log.d("LOG", "response code : " + response.code());
                    votingInterface.hideProgress();
                    votingInterface.setCredentialError();
                }
            }

            @Override
            public void onFailure(Call<SpecificVoteResponse> call, Throwable t) {
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
