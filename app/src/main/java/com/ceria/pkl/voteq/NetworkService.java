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

    public void login (final String email, final String password, final ClientCallback clientCallback){
        String url = "http://crysdip.herokuapp.com/api/";
        Log.d("URL", url);
        StringRequest loginRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject logResponse = new JSONObject(response);
                    Log.d("response", logResponse.toString(2));
                    JSONObject jsonObject = new JSONObject(response); //untuk menampung semua hasil JSON
                    String  status = jsonObject.getString("status");

                    if (status.equals("success")){
                        clientCallback.onSucceeded(); //memberitahu ke LoginActivity bahwa login sukses, agan LoginActivity menjalankan onSucceedeed
                    }else{
                        clientCallback.onFailed();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Log.d("Response", response);
                    clientCallback.onFailed();
                }
            }
        }, new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error){
                Log.d("Error", error.toString());
                clientCallback.onFailed();
            }
        }) {
            public Map<String, String> getHeader() throws AuthFailureError{
                Map<String, String> header = new HashMap<>();
                header.put("Context-Type", "application/x-www-form-urlencoded");
                return header;
            }

            public Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("passwors", password);
                return params;
            }
        };
        requestQueue.add(loginRequest);
    }
}
