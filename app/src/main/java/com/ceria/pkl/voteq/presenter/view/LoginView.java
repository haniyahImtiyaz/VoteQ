package com.ceria.pkl.voteq.presenter.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ceria.pkl.voteq.activity.SignIn;
import com.ceria.pkl.voteq.models.network.ApiClient;
import com.ceria.pkl.voteq.models.network.LoginClient;
import com.ceria.pkl.voteq.models.pojo.Login;
import com.ceria.pkl.voteq.models.pojo.LoginResponse;
import com.ceria.pkl.voteq.presenter.callback.LoginCallBack;
import com.ceria.pkl.voteq.presenter.viewinterface.LoginInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 23/09/16.
 */
public class LoginView implements LoginCallBack{
    public static String token;
    private SharedPreferences sharedPreferences;
    private LoginInterface loginInterface;
    private Context signInContext;

    public LoginView(LoginInterface loginInterface, Context context){
        this.loginInterface = loginInterface;
        this.signInContext = context;
    }

    @Override
    public void auth(String email, String password) {
        Log.d("Detect", "email :" + email + ",pass :" + password);
        if (loginInterface != null) {
            loginInterface.showProgress();
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(signInContext);
        String auth_token = sharedPreferences.getString("token", "");
        if (!auth_token.isEmpty()) {
            loginInterface.navigateToHome();
        }

        LoginClient loginClientModel = ApiClient.getClient().create(LoginClient.class);
        Call<LoginResponse> call = loginClientModel.signIn(email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.code()==200) {
                    Login loginObject = response.body().data;
                    Log.d("LOG", "Body Respnse: " + loginObject.authToken);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", loginObject.authToken);
                    editor.apply();
                    token = loginObject.authToken;
                    loginInterface.hideProgress();
                    loginInterface.navigateToHome();
                }
                else {
                    Log.d("log", "response code: "+response.code());
                    loginInterface.hideProgress();
                    loginInterface.setCredentialError();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginInterface.onNetworkFailure();
            }
        });

    }

    @Override
    public void onDestroy() {
        loginInterface.onNetworkFailure();
    }

}
