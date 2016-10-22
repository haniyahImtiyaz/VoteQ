package com.ceria.pkl.voteq.presenter.view;

import com.ceria.pkl.voteq.itemAdapter.ResultItem;
import com.ceria.pkl.voteq.models.network.ApiClient;
import com.ceria.pkl.voteq.models.network.SpecificVoteClient;
import com.ceria.pkl.voteq.models.pojo.Option;
import com.ceria.pkl.voteq.models.pojo.SpecificVoteResponse;
import com.ceria.pkl.voteq.models.pojo.Vote;
import com.ceria.pkl.voteq.models.pojo.VoteUser;
import com.ceria.pkl.voteq.presenter.callback.SpecificVoteCallBack;
import com.ceria.pkl.voteq.presenter.viewinterface.VotingInterface;

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
public class SpecificVoteView implements SpecificVoteCallBack {
    public String voted_option_id;
    public List<ResultItem> resultItemList = new ArrayList<>();
    public String date;
    private VotingInterface votingInterface;

    public SpecificVoteView(VotingInterface votingInterface) {
        this.votingInterface = votingInterface;
    }

    public void setDate(String date) {
      //  SimpleDateFormat formatDate = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        String dateNew = null;
        try {
            Date dateText = formatDate.parse(date);
            formatDate = new SimpleDateFormat("MMMM dd, yyyy");
            dateNew = formatDate.format(dateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.date = dateNew;
    }

    @Override
    public void callSpecificVote(String token, String id) {
        if (votingInterface != null) {
            votingInterface.showProgress();
        }

        final SpecificVoteClient specificVoteClient = ApiClient.getClient().create(SpecificVoteClient.class);
        Call<SpecificVoteResponse> call = specificVoteClient.specificVote(token, id);
        call.enqueue(new Callback<SpecificVoteResponse>() {
            @Override
            public void onResponse(Call<SpecificVoteResponse> call, Response<SpecificVoteResponse> response) {
                if (response.code() == 200) {
                    VoteUser voteUser = response.body().data;
                    Vote vote = voteUser.vote;
                    Vote vote2 = vote.vote;
                    if (vote2 == null) {
                        setDate(vote.created_at);
                        Option[] options = vote.options;
                        for (int i = 0; i < options.length; i++) {
                            String id = options[i].id;
                            String title = options[i].title;
                            String value = options[i].count;
                            String percen = options[i].percentage;
                            resultItemList.add(new ResultItem(id, title, value, percen));
                        }
                    } else {
                        setDate(vote2.created_at);
                        Option[] options = vote2.options;
                        for (int i = 0; i < options.length; i++) {
                            String id = options[i].id;
                            String title = options[i].title;
                            String value = options[i].count;
                            String percen = options[i].percentage;
                            resultItemList.add(new ResultItem(id, title, value, percen));
                        }
                        Option choosenOption = vote.choosenOption;
                        voted_option_id = choosenOption.id;
                    }

                    votingInterface.hideProgress();
                    votingInterface.onSuccedeedGetVote();

                } else {
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
