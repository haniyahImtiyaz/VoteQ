package com.ceria.pkl.voteq.presenter.interactorImpl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ceria.pkl.voteq.models.network.ApiClient;
import com.ceria.pkl.voteq.models.network.LoginClient;
import com.ceria.pkl.voteq.models.pojo.Login;
import com.ceria.pkl.voteq.models.pojo.LoginResponse;
import com.ceria.pkl.voteq.presenter.interactor.LoginInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by win 8 on 10/23/2016.
 */
public class LoginInteractorImpl implements LoginInteractor{
    public static String token;
    private SharedPreferences sharedPreferences;
    private Context context;

    public LoginInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public void login(final String email, final String password, final OnLoginFinishedListener listener) {
        // Mock login. I'm creating a handler to delay the answer a couple of seconds
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                boolean error = false;
                if (email.isEmpty()){
                    listener.onEmailError();
                    error = true;
                }
                if (password.isEmpty()){
                    listener.onPasswordError();
                    error = true;
                }

            if (!error){
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

                LoginClient loginClientModel = ApiClient.getClient().create(LoginClient.class);
                Call<LoginResponse> call = loginClientModel.signIn(email, password);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.code() == 200) {
                            Login loginObject = response.body().data;
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token", loginObject.authToken);
                            editor.apply();
                            token = loginObject.authToken;
                            listener.onSuccess();
                        } else {
                            Log.d("log", "response code: " + response.code());
                            listener.onError();
                        }
                    }


                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        listener.onFailure();
                    }
                });

            }
            }
        }, 2000);
    }
}

