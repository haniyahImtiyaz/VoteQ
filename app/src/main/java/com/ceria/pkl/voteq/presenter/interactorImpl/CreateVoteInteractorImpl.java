package com.ceria.pkl.voteq.presenter.interactorImpl;

import android.os.Handler;
import android.util.Log;

import com.ceria.pkl.voteq.models.network.ApiClient;
import com.ceria.pkl.voteq.models.network.CreateVoteClient;
import com.ceria.pkl.voteq.models.pojo.CreateVoteResponse;
import com.ceria.pkl.voteq.presenter.interactor.CreateVoteInteractor;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by win 8 on 10/23/2016.
 */
public class CreateVoteInteractorImpl implements CreateVoteInteractor {
    private String token, id;

    public String getId() {
        return id;
    }

    public CreateVoteInteractorImpl(String token) {
        this.token = token;
    }

    @Override
    public void createVote(final String title, final String description,
                           final String started, final String ended, final List<String> options,
                           final OnCreateVoteFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean error = false;
                if (title.isEmpty()) {
                    listener.onTitleError();
                    error = true;
                }
                if (options.size() < 2) {
                    listener.onOptionsError();
                    error = true;
                }
//                if (!option.isEmpty()) {
//                   error = true;
//                    listener.onOptionError();
//                }
                if (!error) {
                    final CreateVoteClient createVoteClient = ApiClient.getClient().create(CreateVoteClient.class);
                    Call<CreateVoteResponse> call = createVoteClient.createVote("Token token="+token, title, description, started, ended, options);
                    call.enqueue(new Callback<CreateVoteResponse>() {
                        @Override
                        public void onResponse(Call<CreateVoteResponse> call, Response<CreateVoteResponse> response) {
                            if (response.code() == 201) {
                                id = response.body().data.id;
                                Log.d("idnya", "a"+id);
                                listener.onSuccess();
                            } else {
                                Log.d("LOG", "response code: " + response.code());
                                listener.onError();
                            }
                        }

                        @Override
                        public void onFailure(Call<CreateVoteResponse> call, Throwable t) {
                            listener.onFailure();
                        }
                    });
                }

            }
        }, 2000);

    }

}

