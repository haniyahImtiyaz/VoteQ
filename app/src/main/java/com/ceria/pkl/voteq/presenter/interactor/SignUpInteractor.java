package com.ceria.pkl.voteq.presenter.interactor;

/**
 * Created by ayu on 23/10/16.
 */
public interface SignUpInteractor {
    interface OnSignUpFinishedListener {

        void onEmailError();

        void onPasswordError();

        void onConfirmPasswordError();

        void onSuccess();

        void onError();

        void onFailure();
    }

    void signup(String email, String password, OnSignUpFinishedListener listener);
}
