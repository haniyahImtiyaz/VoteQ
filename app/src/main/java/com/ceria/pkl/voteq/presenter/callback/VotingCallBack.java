package com.ceria.pkl.voteq.presenter.callback;

/**
 * Created by win 8 on 10/22/2016.
 */
public interface VotingCallBack {
    void callVoting(String token, String id, String voted_option_id);

    void onDestroy();
}