package com.ceria.pkl.voteq.presenter.callback;

/**
 * Created by win 8 on 10/21/2016.
 */
public interface SpecificVoteCallBack {
    void callSpecificVote(String token, String id);

    void onDestroy();
}
