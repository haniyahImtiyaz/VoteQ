package com.ceria.pkl.voteq.presenter.callback;

/**
 * Created by root on 22/09/16.
 */
public interface LoginCallBack {
    void auth(String email, String password);
    void onDestroy();
}
