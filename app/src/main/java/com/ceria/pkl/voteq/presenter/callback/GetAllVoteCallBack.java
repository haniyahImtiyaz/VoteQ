package com.ceria.pkl.voteq.presenter.callback;

/**
 * Created by win 8 on 10/21/2016.
 */
public interface GetAllVoteCallBack {
    void callGetAllVote(String token, Boolean currentUser);
    void onDestroy();
}
