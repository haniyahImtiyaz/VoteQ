package com.ceria.pkl.voteq;

/**
 * Created by pandhu on 13/07/16.
 */
public interface ClientCallbackSignIn{
    void onSucceded();
    void onFailed();
    void onTimeout();
}