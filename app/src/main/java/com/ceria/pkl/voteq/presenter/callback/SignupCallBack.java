package com.ceria.pkl.voteq.presenter.callback;

/**
 * Created by win 8 on 9/30/2016.
 */
public interface SignupCallBack {
    void signUpAuth(String email, String pwd, String pwdConfirm);

    void onDestroy();
}
