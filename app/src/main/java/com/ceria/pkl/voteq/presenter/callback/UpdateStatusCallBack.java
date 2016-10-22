package com.ceria.pkl.voteq.presenter.callback;

/**
 * Created by win 8 on 10/23/2016.
 */
public interface UpdateStatusCallBack {
    void callUpdateStatus(String vote_id, String token, String title, Boolean is_open);

    void onDestroy();
}
