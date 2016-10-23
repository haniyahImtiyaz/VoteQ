package com.ceria.pkl.voteq.presenter.callback;

/**
 * Created by win 8 on 10/23/2016.
 */
public interface DeleteVoteCallBack {
    void callDeleteVote(String token, String id);

    void onDestroy();
}
