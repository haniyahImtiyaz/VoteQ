package com.ceria.pkl.voteq.presenter.view;

import android.util.Log;

import com.ceria.pkl.voteq.models.ApiClient;
import com.ceria.pkl.voteq.models.Login;
import com.ceria.pkl.voteq.pojo.LoginResponse;
import com.ceria.pkl.voteq.presenter.callback.LoginCallBack;
import com.ceria.pkl.voteq.presenter.viewinterface.LoginInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 23/09/16.
 */
public class LoginView implements LoginCallBack{
    private LoginCallBack loginCallBack;
    private LoginInterface loginInterface;

    public LoginView(LoginInterface loginInterface){
        this.loginInterface = loginInterface;
    }

    @Override
    public void auth(String email, String password) {
        if (loginInterface != null) {
            loginInterface.showProgress();
        }

        Login loginModel = ApiClient.getClient().create(Login.class);
        Call<LoginResponse> call = loginModel.signIn(email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("WAW", "Number of movies received: " + response);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("fail-message", "fail");
            }
        });
    }

    @Override
    public void onDestroy() {

    }
}
