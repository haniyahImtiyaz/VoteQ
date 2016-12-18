package com.ceria.pkl.voteq.presenter.callback;

/**
 * Created by win 8 on 9/30/2016.
 */
public interface SignupCallBack {
    void signUpAuth(String name, String email, String job, String pwd, String pwdConfirm, String date, String gender, Double latitude, Double longitude);

    void onDestroy();
}
