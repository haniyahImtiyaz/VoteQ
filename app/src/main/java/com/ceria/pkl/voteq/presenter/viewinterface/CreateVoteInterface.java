package com.ceria.pkl.voteq.presenter.viewinterface;

/**
 * Created by win 8 on 10/21/2016.
 */
public interface CreateVoteInterface {
    void showProgress();

    void hideProgress();

    void setCredentialError();

    void onNetworkFailure();

    void navigateToHome();

    void onSuccedeed();

    void setTitleEmpty();

    void setOptionsEmpty();

    void setOptionNotEmpty();
}
