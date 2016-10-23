package com.ceria.pkl.voteq.presenter.viewinterface;

/**
 * Created by root on 23/09/16.
 */
public interface LoginInterface {
    void showProgress();

    void hideProgress();

    void setCredentialError();

    void navigateToHome();

    void onNetworkFailure();

    void setEmailError();

    void setPasswordError();
}
