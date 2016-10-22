package com.ceria.pkl.voteq.presenter.viewinterface;

/**
 * Created by win 8 on 10/21/2016.
 */
public interface VotingInterface {
    void showProgress();

    void hideProgress();

    void setCredentialError();

    void onNetworkFailure();

    void navigateToHome();

    void onSucceededGetVote();

    void onSucceededCancelVote();

    void onSucceededUpdateStatus();
}
