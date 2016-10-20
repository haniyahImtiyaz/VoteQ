package com.ceria.pkl.voteq.models.interactor;

/**
 * Created by win 8 on 10/1/2016.
 */
public interface SignupInteractor {
    interface OnSignupFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onSuccess();
    }

    void signUp(String username, String password, OnSignupFinishedListener listener);
}
