package com.ceria.pkl.voteq;

/**
 * Created by pandhu on 12/07/16.
 */
public interface ClientCallback {
    void onSucceeded();
    void onFailed();
    void onEmailSame();
}
