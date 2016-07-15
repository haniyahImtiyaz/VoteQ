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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pandhu on 12/07/16.
 */
public class NetworkService {
    private final RequestQueue requestQueue;
    private final Context context;
    private String auth_token;
    List<HomeItem> homeItemList = new ArrayList<HomeItem>();

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
                    Log.d("signUpPost", "response " + logResponse.toString(2));
                    String status = logResponse.getString("status");
                    if(status.equals("error")) {
                        JSONObject dataResponse = logResponse.getJSONObject("data");
                        JSONArray arrayResponseEmail = dataResponse.getJSONArray("email");
                        JSONArray arrayResponsePassword = dataResponse.getJSONArray("password");
                        JSONArray arrayResponsePasswordConfirm = dataResponse.getJSONArray("password_confirmation");
                        String responseEmail = arrayResponseEmail.getString(0);
                        String responsePwd = arrayResponseEmail.getString(0);
                        if (responseEmail.equals("has already been taken")) {
                            clientCallback.onEmailSame();
                        }else {
                            clientCallback.onFailed();
                        }
                    }
                } catch (JSONException e) {
                    Log.d("signUpPost", "response "+response);
                    e.printStackTrace();
                    clientCallback.onFailed();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("signUpPost", "error signUp "+error.toString());
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

    public void login (final String email, final String password, final ClientCallbackSignIn clientCallback){
        String url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.sign_in);
        StringRequest loginRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject logResponse = new JSONObject(response);
                    Log.d("response", logResponse.toString(2));
                    JSONObject jsonObject = new JSONObject(response); //untuk menampung semua hasil JSON
                    String  status = jsonObject.getString("status");
                    JSONObject dataResponse = logResponse.getJSONObject("data");
                    auth_token = dataResponse.getString("auth_token");
                    //ada apa dengan cinta

                    if (status.equals("success")){
                        clientCallback.onSucceded();
                        //memberitahu ke LoginActivity bahwa login sukses, agan LoginActivity menjalankan onSucceedeed
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
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> header = new HashMap<>();
                header.put("Context-Type", "application/x-www-form-urlencoded");
                return header;
            }

            public Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("session[email]", email);
                params.put("session[password]", password);
                return params;
            }
        };
        requestQueue.add(loginRequest);
    }
    public String getAuth_token(){
        return auth_token;
    }

    public void createVote(final String token, final String title, final ArrayList<String> option, final boolean is_open, final ClientCallbackSignIn clientCallback){
        String url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.create_vote);
        StringRequest createVoteRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject logResponse = new JSONObject(response);
                    Log.d("createVote", "response " + logResponse.toString(2));
                    String status = logResponse.getString("status");
              //     if(status.equals("success")) {
                        clientCallback.onSucceded();
              //      }else {
              //          clientCallback.onFailed();
              //      }
                } catch (JSONException e) {
                    Log.d("createVote", "response "+response);
                    e.printStackTrace();
                    clientCallback.onFailed();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("createVote", "error create vote "+error.toString());
                clientCallback.onFailed();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", token);
                header.put("Content-Type", "application/x-www-form-urlencoded");
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                for(int i = 0; i < option.size(); i++){
                    params.put("option[]", option.get(i));
                }
                params.put("is_open", String.valueOf(is_open));
                return params;
            }
        };
        requestQueue.add(createVoteRequest);
    }
    public void getAllVote(final String token, final ClientCallbackSignIn clientCallback){
        String url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.create_vote);
        StringRequest getAllVoteRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject logResponse = new JSONObject(response);
                    Log.d("getAllVote", "response " + logResponse.toString(2));
                    String status = logResponse.getString("status");
                    JSONObject data = logResponse.getJSONObject("data");
                    JSONArray vote = data.getJSONArray("vote");
                    for (int i=0; i < vote.length(); i++){
                        JSONObject dataVote = vote.getJSONObject(i);
                        String title = dataVote.getString("title");
                        String voter = dataVote.getString("voter_count");
                        String label ;
                        if(dataVote.getBoolean("status") == true){
                            label = "open";
                        }else{
                            label = "closed";
                        }
                        homeItemList.add(get(title, voter, label, R.mipmap.ic_launcher));
                    }
                    if(status.equals("OK")) {
                        clientCallback.onSucceded();
                    }else {
                        clientCallback.onFailed();
                   }
                    Log.d("yeyeye1", homeItemList.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                    clientCallback.onFailed();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getAllVote", "error get all vote "+error.toString());
                clientCallback.onFailed();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", token);
                return header;
            }

        };

        requestQueue.add(getAllVoteRequest);
        Log.d("yeyeye2", homeItemList.toString());
    }

    public List<HomeItem> getHomeItemList() {
        Log.d("yeyeye3", homeItemList.toString());
        return homeItemList;
    }

    private HomeItem get(String title, String count,String label, int image){
        return new HomeItem(title,count,label,image);
    }
}

