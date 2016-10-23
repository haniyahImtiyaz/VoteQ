package com.ceria.pkl.voteq.presenter.interactor;

/**
 * Created by win 8 on 10/23/2016.
 */
public interface LoginInteractor {
    interface OnLoginFinishedListener {
        void onEmailError();

        void onPasswordError();

        void onSuccess();

        void onError();

        void onFailure();
    }

    void login(String email, String password, OnLoginFinishedListener listener);
}
