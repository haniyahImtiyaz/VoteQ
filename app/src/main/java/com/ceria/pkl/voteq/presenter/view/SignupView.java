package com.ceria.pkl.voteq.presenter.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ceria.pkl.voteq.models.network.ApiClient;
import com.ceria.pkl.voteq.models.network.SignupClient;
import com.ceria.pkl.voteq.models.pojo.Signup;
import com.ceria.pkl.voteq.models.pojo.SignupResponse;
import com.ceria.pkl.voteq.presenter.callback.SignupCallBack;
import com.ceria.pkl.voteq.presenter.viewinterface.SignupInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by win 8 on 9/30/2016.
 */
public class SignupView implements SignupCallBack {
    private SharedPreferences sharedPreferences;
    private SignupInterface signupInterface;
    private Context signupContext;
    public String token;

    public SignupView(SignupInterface signupInterface, Context context){
        this.signupInterface = signupInterface;
        this.signupContext = context;
    }
    @Override
    public void signUpAuth(String email, String pwd, String pwdConfirm) {
        if (signupInterface != null) {
            signupInterface.showProgress();
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(signupContext);


        SignupClient signupClient = ApiClient.getClient().create(SignupClient.class);
        Call<SignupResponse> call = signupClient.signUp(email, pwd, pwdConfirm);
        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if (response.code() == 201) {
                    Signup signupObject = response.body().data;
                    Log.d("LOG", "Body Respnse: " + signupObject.user.authToken);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", signupObject.user.authToken);
                    editor.apply();
                    token = signupObject.user.authToken;
                    signupInterface.onSuccedeed();
                    signupInterface.hideProgress();
                    signupInterface.navigateToHome();
                } else if(response.code() == 422) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        JSONObject data = jsonObject.getJSONObject("data");
                        Log.d("haniyahVo", "data : " + jsonObject);
                        try {
                            JSONArray er = data.getJSONArray("email");
                            signupInterface.onEmailSame();
                        }catch (JSONException e) {
                            try {
                                JSONArray er = data.getJSONArray("password");
                                signupInterface.onPasswordLess();
                            }catch (JSONException e1) {
                                try {
                                    JSONArray er = data.getJSONArray("password_confirmation");
                                    signupInterface.onConfirmPassNotMatch();
                                }catch (JSONException e2) {
                                    e2.printStackTrace();
                                }
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    signupInterface.hideProgress();
                } else {
                    Log.d("log", "response code: " + response.code());
                    signupInterface.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                signupInterface.onNetworkFailure();
            }
        });
    }


    @Override
    public void onDestroy() {
        signupInterface.onNetworkFailure();
    }

}
