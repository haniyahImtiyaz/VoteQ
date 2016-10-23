package com.ceria.pkl.voteq.presenter.view;

import android.util.Log;

import com.ceria.pkl.voteq.models.network.ApiClient;
import com.ceria.pkl.voteq.models.network.DeleteVoteClient;
import com.ceria.pkl.voteq.presenter.callback.DeleteVoteCallBack;
import com.ceria.pkl.voteq.presenter.viewinterface.GetAllVoteInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by win 8 on 10/23/2016.
 */
public class DeleteVoteView implements DeleteVoteCallBack {
    private GetAllVoteInterface getAllVoteInterface;

    public DeleteVoteView(GetAllVoteInterface getAllVoteInterface) {
        this.getAllVoteInterface = getAllVoteInterface;
    }

    @Override
    public void callDeleteVote(String token, String id) {
        if(getAllVoteInterface != null){
            getAllVoteInterface.showProgress();
        }
        DeleteVoteClient deleteVoteClient = ApiClient.getClient().create(DeleteVoteClient.class);
        Call<Response<Void>> call = deleteVoteClient.deleteVote(token, id);
        call.enqueue(new Callback<Response<Void>>() {
            @Override
            public void onResponse(Call<Response<Void>> call, Response<Response<Void>> response) {
                if(response.code() == 204){
                    getAllVoteInterface.hideProgress();
                    getAllVoteInterface.onSucceededDelete();
                }else{
                    Log.d("LOG", "response code : " + response.code());
                    getAllVoteInterface.hideProgress();
                    getAllVoteInterface.setCredentialError();
                }
            }

            @Override
            public void onFailure(Call<Response<Void>> call, Throwable t) {
                getAllVoteInterface.hideProgress();
                getAllVoteInterface.onNetworkFailure();
            }
        });
    }

    @Override
    public void onDestroy() {
        getAllVoteInterface = null;
    }
}
