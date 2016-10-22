package com.ceria.pkl.voteq.presenter.view;

import android.util.Log;

import com.ceria.pkl.voteq.itemAdapter.HomeItem;
import com.ceria.pkl.voteq.models.network.ApiClient;
import com.ceria.pkl.voteq.models.network.GetAllVoteClient;
import com.ceria.pkl.voteq.models.pojo.GetAllVote;
import com.ceria.pkl.voteq.models.pojo.GetAllVoteResponse;
import com.ceria.pkl.voteq.models.pojo.Vote;
import com.ceria.pkl.voteq.presenter.callback.GetAllVoteCallBack;
import com.ceria.pkl.voteq.presenter.viewinterface.GetAllVoteInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by win 8 on 10/21/2016.
 */
public class GetAllVoteView implements GetAllVoteCallBack {
    public static String token;
    public List<HomeItem> homeItemList = new ArrayList<>();
    private GetAllVoteInterface getAllVoteInterface;

    public GetAllVoteView(GetAllVoteInterface getAllVoteInterface) {
        this.getAllVoteInterface = getAllVoteInterface;
    }

    @Override
    public void callGetAllVote(String token, Boolean current_user) {
        final GetAllVoteClient getAllVoteClient = ApiClient.getClient().create(GetAllVoteClient.class);
        Call<GetAllVoteResponse> call = getAllVoteClient.getAllVote(token, current_user);
        call.enqueue(new Callback<GetAllVoteResponse>() {
            @Override
            public void onResponse(Call<GetAllVoteResponse> call, Response<GetAllVoteResponse> response) {
                if (response.code() == 200) {
                    GetAllVote getAllVoteObject = response.body().data;
                    Log.d("logt", String.valueOf(getAllVoteObject.vote.length));
                    Vote[] voteObject = getAllVoteObject.vote;
                    for (int i = 0; i < voteObject.length; i++) {
                        String id = voteObject[i].id;
                        String title = voteObject[i].title;
                        String count = voteObject[i].voter_count;
                        String label;
                        if (voteObject[i].status == "true") {
                            label = "Open";
                        } else {
                            label = "Closed";
                        }
                        String idCreator = voteObject[i].user.authToken;
                        homeItemList.add(new HomeItem(id, title, count, label, idCreator));
                    }
                    getAllVoteInterface.onSucceeded();
                    getAllVoteInterface.hideProgress();
                } else {
                    Log.d("LOG", "response code: " + response.code());
                    getAllVoteInterface.hideProgress();
                    getAllVoteInterface.setCredentialError();
                }

            }

            @Override
            public void onFailure(Call<GetAllVoteResponse> call, Throwable t) {
                Log.d("logGetVote", "eror: " + t);
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
