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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by win 8 on 10/21/2016.
 */
public class GetAllVoteView implements GetAllVoteCallBack {
    public List<VoteItem> voteItemList = new ArrayList<>();
    private GetAllVoteInterface getAllVoteInterface;

    public GetAllVoteView(GetAllVoteInterface getAllVoteInterface) {
        this.getAllVoteInterface = getAllVoteInterface;
    }

    private String setDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM yyyy");
        try {
            Date dateParse = sdf.parse(date);
            String dateText = sdf1.format(dateParse);
            return dateText;
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("error", e.toString());
            return date;
        }
    }

    @Override
    public void callGetAllVote() {
        final GetAllVoteClient getAllVoteClient = ApiClient.getClient().create(GetAllVoteClient.class);
        Call<GetAllVoteResponse> call = getAllVoteClient.getAllVote();
        call.enqueue(new Callback<GetAllVoteResponse>() {
            @Override
            public void onResponse(Call<GetAllVoteResponse> call, Response<GetAllVoteResponse> response) {
                if (response.code() == 200) {
                    GetAllVote getAllVoteObject = response.body().data;
                    Vote[] voteObject = getAllVoteObject.vote;
                    for (int i = 0; i < voteObject.length; i++) {
                        String started = "", ended = "";
                        if(voteObject[i].started_at != ""){
                            started = setDate(voteObject[i].started_at);
                        }
                        if(voteObject[i].ended_at != ""){
                            ended = setDate(voteObject[i].ended_at);
                        }
                        voteItemList.add(new VoteItem(voteObject[i].id, voteObject[i].user.name, started,
                                ended, voteObject[i].title, voteObject[i].category, voteObject[i].description,
                                voteObject[i].status, voteObject[i].participant, voteObject[i].user.image,
                                voteObject[i].vote_pict_url));
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
