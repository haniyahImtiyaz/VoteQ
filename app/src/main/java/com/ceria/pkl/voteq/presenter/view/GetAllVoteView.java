package com.ceria.pkl.voteq.presenter.view;

import android.content.Context;
import android.util.Log;

import com.ceria.pkl.voteq.itemAdapter.HomeItem;
import com.ceria.pkl.voteq.itemAdapter.VoteItem;
import com.ceria.pkl.voteq.models.network.ApiClient;
import com.ceria.pkl.voteq.models.network.GetAllVoteClient;
import com.ceria.pkl.voteq.models.pojo.GetAllVote;
import com.ceria.pkl.voteq.models.pojo.GetAllVoteResponse;
import com.ceria.pkl.voteq.models.pojo.User;
import com.ceria.pkl.voteq.models.pojo.Vote;
import com.ceria.pkl.voteq.presenter.callback.GetAllVoteCallBack;
import com.ceria.pkl.voteq.presenter.viewinterface.GetAllVoteInterface;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by win 8 on 10/21/2016.
 */
public class GetAllVoteView implements GetAllVoteCallBack {
    public List<VoteItem> voteItemList = new ArrayList<>();
    private GetAllVoteInterface getAllVoteInterface;
    private Context context;
    String token;

    public GetAllVoteView(GetAllVoteInterface getAllVoteInterface, Context context, String token) {
        this.getAllVoteInterface = getAllVoteInterface;
        this.context = context;
        this.token = token;
    }

    @Override
    public void callGetAllVote() {
        if (getAllVoteInterface != null) {
            getAllVoteInterface.showProgress();
        }
        final GetAllVoteClient getAllVoteClient = ApiClient.getClient().create(GetAllVoteClient.class);
        Call<GetAllVoteResponse> call = getAllVoteClient.getAllVote();
        call.enqueue(new Callback<GetAllVoteResponse>() {
            @Override
            public void onResponse(Call<GetAllVoteResponse> call, Response<GetAllVoteResponse> response) {
                if (response.code() == 200) {
                    GetAllVote getAllVoteObject = response.body().data;
                    Vote[] voteObject = getAllVoteObject.vote;
                    Log.d("GetAllVote", String.valueOf(voteObject.length));
                    for (int i = 0; i < voteObject.length; i++) {
                        String id = voteObject[i].id;
                        String title = voteObject[i].title;
                        String description = voteObject[i].description;
                        String category = voteObject[i].category;
                        String started = voteObject[i].started_at;
                        String ended = voteObject[i].ended_at;
                        User creator = voteObject[i].user;
                        String nameCreator = creator.name;
                        int responder = voteObject[i].participant;
                        String status = "Open";
                        String imageUser = creator.image;
                        String imageVote = voteObject[i].vote_pict_url;

//                        File file = new File(context.getExternalFilesDir(null) + File.separator + "avatar " + nameCreator + ".jpg");
//                        if(!file.exists()){
//                            GetImageView getImageView = new GetImageView(context, nameCreator);
//                            getImageView.getImage(token, "https://electa-engine.herokuapp.com"+creator.image);
//                        }
                        voteItemList.add(new VoteItem(id, nameCreator, started, ended, title, category, description, status, responder, imageUser, imageVote));
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
};
