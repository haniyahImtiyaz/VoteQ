package com.ceria.pkl.voteq.presenter.viewinterface;

/**
 * Created by win 8 on 9/30/2016.
 */
public interface SignupInterface {
    void showProgress();
    void hideProgress();
    void onEmailSame();
    void onPasswordLess();
    void onConfirmPassNotMatch();
    void onSuccedeed();
    void navigateToHome();
    void onNetworkFailure();

}
