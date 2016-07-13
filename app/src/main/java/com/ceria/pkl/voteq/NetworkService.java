package com.ceria.pkl.voteq;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pandhu on 12/07/16.
 */
public class NetworkService {
    private final RequestQueue requestQueue;
    private final Context context;

    public NetworkService(Context context) {
        this.context = context;
        this.requestQueue = Volley. newRequestQueue(context);
    }

    public void signUp(final String email, final String pwd, final String pwdConfirm, final ClientCallback clientCallback){
        String url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.sign_up);
        StringRequest signUpRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject logResponse = new JSONObject(response);
                    Log.d("signup", "response " + logResponse.toString(2));
                    String status = logResponse.getString("status");
                    JSONObject data = logResponse.getJSONObject("data");
                    String email = data.getJSONArray("email").getString(0);
                    Log.d("signup", "msg " + email);
                    if (status.equals("success")){
                       clientCallback.onSucceeded();
                    }
                    else {
                        clientCallback.onFailed();
                    }
                } catch (JSONException e) {
                    Log.d("signup", "response "+response);
                    e.printStackTrace();
                    clientCallback.onFailed();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("signup", "error sign up "+error.toString());
                clientCallback.onFailed();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/x-www-form-urlencoded");
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user[email]", email);
                params.put("user[password]", pwd);
                params.put("user[password_confirmation]", pwdConfirm);
                return params;
            }
        };
        requestQueue.add(signUpRequest);
    }



}
