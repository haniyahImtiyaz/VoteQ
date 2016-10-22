package com.ceria.pkl.voteq.presenter.callback;

/**
 * Created by win 8 on 10/22/2016.
 */
public interface CancelVoteCallBack {
    void callCancelVote(String token, String vote_id);

    void onDestroy();
}
