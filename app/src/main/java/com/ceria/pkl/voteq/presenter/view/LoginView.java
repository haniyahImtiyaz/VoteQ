package com.ceria.pkl.voteq.presenter.view;

import android.content.Context;
import android.util.Log;

import com.ceria.pkl.voteq.presenter.callback.LoginCallBack;
import com.ceria.pkl.voteq.presenter.interactor.LoginInteractor;
import com.ceria.pkl.voteq.presenter.interactorImpl.LoginInteractorImpl;
import com.ceria.pkl.voteq.presenter.viewinterface.LoginInterface;

/**
 * Created by root on 23/09/16.
 */
public class LoginView implements LoginCallBack, LoginInteractor.OnLoginFinishedListener {
    private LoginInterface loginInterface;
    private LoginInteractor loginInteractor;

    public LoginView(LoginInterface loginInterface, Context context) {
        this.loginInterface = loginInterface;
        this.loginInteractor = new LoginInteractorImpl(context);
    }

    @Override
    public void auth(String email, String password) {
        Log.d("Detect", "email :" + email + ",pass :" + password);
        if (loginInterface != null) {
            loginInterface.showProgress();
        }
        loginInteractor.login(email, password, this);

    }

    @Override
    public void onDestroy() {
        loginInterface = null;
    }

    @Override
    public void onEmailError() {
        if(loginInterface != null){
            loginInterface.hideProgress();
            loginInterface.setEmailError();
        }
    }

    @Override
    public void onPasswordError() {
        if(loginInterface != null){
            loginInterface.hideProgress();
            loginInterface.setPasswordError();
        }
    }

    @Override
    public void onSuccess() {
        if(loginInterface != null){
            loginInterface.hideProgress();
            loginInterface.navigateToHome();
        }
    }

    @Override
    public void onError() {
        if(loginInterface != null){
            loginInterface.hideProgress();
            loginInterface.setCredentialError();
        }
    }

    @Override
    public void onFailure() {
        if(loginInterface != null){
            loginInterface.hideProgress();
            loginInterface.onNetworkFailure();
        }
    }
}
