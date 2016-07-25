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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private final List<HomeItem> homeItemList = new ArrayList<>();
    private final List<ResultItem> resultItemList = new ArrayList<>();
    private String date;
    private boolean is_voted;
    private int voted_option_id;

    public NetworkService(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public List<HomeItem> getHomeItemList() {
        return homeItemList;
    }

    public void setHomeItemList(String id, String title, String count, String label, String tokenVote) {
        homeItemList.add(get(id, title, count, label, tokenVote));
    }

    private HomeItem get(String id, String title, String count, String label, String tokenVote) {
        return new HomeItem(id, title, count, label, tokenVote);

    }

    public List<ResultItem> getResultItemList() {
        return resultItemList;
    }

    public void setResultItemList(String id, String title, String count, String percentage) {
        resultItemList.add(new ResultItem(id, title, count, percentage));
    }

    public String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
        Date dateText = new Date();
        try {
            dateText = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat("MMMM dd, yyyy");
        String dateNew = format.format(dateText);
        Log.d("getDate", dateNew.toString());
        return dateNew;
    }

    public int voted_option_id() {
        return voted_option_id;
    }

    public boolean is_voted(){
        return is_voted;
    }

    public void signUp(final String email, final String pwd, final String pwdConfirm, final ClientCallback clientCallback) {
        String url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.sign_up);
        StringRequest signUpRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject logResponse = new JSONObject(response);
                    Log.d("signUpPost", "response " + logResponse.toString(2));
                    String status = logResponse.getString("status");
                        JSONObject dataResponse = logResponse.getJSONObject("data");
                        JSONObject user = dataResponse.getJSONObject("user");
                        auth_token = user.getString("auth_token");
                        if (status.equals("success")) {
                            clientCallback.onSucceeded();
                        }else{
                            clientCallback  .onFailed();
                        }
                } catch (JSONException e) {
                    Log.d("signUpPost", "response " + response);
                    e.printStackTrace();
                    clientCallback.onFailed();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.statusCode == 422) {
                    clientCallback.onEmailSame();
                } else {
                    Log.d("signUpPostError", "error signUp " + error.toString());
                    clientCallback.onFailed();
                }

            }
        }) {
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

    public void login(final String email, final String password, final ClientCallbackSignIn clientCallback) {
        String url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.sign_in);
        StringRequest loginRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject logResponse = new JSONObject(response);
                    Log.d("response", logResponse.toString(2));
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    JSONObject dataResponse = logResponse.getJSONObject("data");
                    auth_token = dataResponse.getString("auth_token");

                    if (status.equals("success")) {
                        clientCallback.onSucceded();
                    } else {
                        clientCallback.onFailed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Response", response);
                    clientCallback.onFailed();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
                clientCallback.onFailed();
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Context-Type", "application/x-www-form-urlencoded");
                return header;
            }

            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("session[email]", email);
                params.put("session[password]", password);
                return params;
            }
        };
        requestQueue.add(loginRequest);
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void createVote(final String token, final String title, final ArrayList<String> option, final boolean is_open, final ClientCallbackSignIn clientCallback) {
        String url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.create_vote);
        StringRequest createVoteRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject logResponse = new JSONObject(response);
                    Log.d("createVote", "response " + logResponse.toString(2));
                    String status = logResponse.getString("status");
                    if (status.equals("success")) {
                        clientCallback.onSucceded();
                    } else {
                        clientCallback.onFailed();
                    }
                } catch (JSONException e) {
                    Log.d("createVote", "response " + response);
                    e.printStackTrace();
                    clientCallback.onFailed();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("createVote", "error create vote " + error.toString());
                clientCallback.onFailed();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                for (int i = 0; i < option.size(); i++) {
                    String opt = option.get(i).toString();
                    params.put("options[" + i + "]", opt);
                }
                int size = option.size();
                Log.d("optionSize", String.valueOf(size));
                params.put("is_open", String.valueOf(is_open));
                return params;
            }

        };
        requestQueue.add(createVoteRequest);
    }

    public void getAllVote(final String token, final String current_user, final ClientCallbackSignIn clientCallback) {
        final String url;
        if (current_user.equals("true")) {
            url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.get_myvote);
        } else {
            url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.create_vote);
        }
        StringRequest getAllVoteRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject logResponse = new JSONObject(response);
                    Log.d("getAllVote", "response " + logResponse.toString(2));
                    String status = logResponse.getString("status");
                    JSONObject data = logResponse.getJSONObject("data");
                    JSONArray vote = data.getJSONArray("vote");
                    for (int i = 0; i < vote.length(); i++) {
                        JSONObject dataVote = vote.getJSONObject(i);
                        String title = dataVote.getString("title");
                        String voter = dataVote.getString("voter_count");
                        String vote_id = dataVote.getString("id");
                        JSONObject user = dataVote.getJSONObject("user");
                        String tokenVote = user.getString("auth_token");
                        String label;
                        if (dataVote.getBoolean("status") == true) {
                            label = "Open";
                        } else {
                            label = "Closed";
                        }


                        setHomeItemList(vote_id, title, voter, label, tokenVote);

                    }
                    if (status.equals("OK")) {
                        clientCallback.onSucceded();
                    } else {
                        clientCallback.onFailed();
                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                    clientCallback.onFailed();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getAllVote", "error get all vote " + error.toString());
                clientCallback.onFailed();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/x-www-form-urlencoded");
                header.put("Authorization", token);
                return header;
            }

        };
        requestQueue.add(getAllVoteRequest);
    }

    public void specificVote(final String token, final String id, final ClientCallbackSignIn clientCallback) {
        String url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.create_vote) + "/" + id;
        StringRequest specificVote = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject logResponse = new JSONObject(response);
                    Log.d("specificVote", "response " + logResponse.toString(2));
                    String status = logResponse.getString("status");
                    JSONObject data = logResponse.getJSONObject("data");
                    JSONObject vote = data.getJSONObject("vote");
                    JSONObject user = vote.getJSONObject("user");
                    date = vote.getString("created_at");

                    try {
                        is_voted = true;
                        voted_option_id = vote.getInt("voted_option_id");
                    } catch (Exception e) {
                        is_voted = false;
                        voted_option_id = 0;
                    }

                    Boolean label = vote.getBoolean("status");
                    JSONArray options = vote.getJSONArray("options");
                    for (int i = 0; i < options.length(); i++) {
                        JSONObject dataOptions = options.getJSONObject(i);
                        String id = dataOptions.getString("id");
                        String title = dataOptions.getString("title");
                        String count = dataOptions.getString("count");
                        String percentage = dataOptions.getString("percentage");
                        setResultItemList(id, title, count, percentage);
                    }

                    if (status.equals("OK")) {
                        clientCallback.onSucceded();
                    } else {
                        clientCallback.onFailed();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    clientCallback.onFailed();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("specificVote", "specific vote" + error.toString());
                clientCallback.onFailed();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", token);
                return header;
            }
        };

        requestQueue.add(specificVote);
    }

    public void givingVote(final String token, final boolean voted, final String vote_id, final int option_id, final ClientCallBackVoting clientCallback) {
        String url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.giving_vote);
        int method;
        if (voted) {
            method = Request.Method.PUT;
        } else {
            method = Request.Method.POST;
        }
        StringRequest givingVoteRequest = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject logResponse = new JSONObject(response);
                    Log.d("givingVote", "response " + logResponse.toString(2));
                    String status = logResponse.getString("status");
                    if (status.equals("success")) {
                        clientCallback.onSuccedeedVoting();
                    } else {
                        clientCallback.onFailedVoting();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    clientCallback.onFailedVoting();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("givingVote", "error giving vote " + error.toString());
                clientCallback.onFailedVoting();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", token);
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("vote_id", vote_id);
                params.put("vote_option_id", String.valueOf(option_id));
                return params;
            }
        };
        requestQueue.add(givingVoteRequest);
    }

    public void updateLabel(final String token, final String id, final String title, final boolean is_open, final ClientCallBackLabel clientCallback) {
        String url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.create_vote) + "/" + id;
        StringRequest updateLabelRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject logResponse = new JSONObject(response);
                    Log.d("updateLabel", "response " + logResponse.toString(2));
                    int status = logResponse.getInt("status");
                    if (status == 200) {
                        clientCallback.succes();
                    } else {
                        clientCallback.fail();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    clientCallback.fail();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("updateLabel", "error update label " + error.toString());
                clientCallback.fail();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", token);
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("is_open", String.valueOf(is_open));
                return params;
            }
        };
        requestQueue.add(updateLabelRequest);
    }

    public void reset(final String email, final ClientCallbackReset clientCallback) {
        String url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.sign_in);
        StringRequest resetRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
                clientCallback.onFailed();
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Context-Type", "application/x-www-form-urlencoded");
                return header;
            }

            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("session[email]", email);
                return params;
            }
        };
        requestQueue.add(resetRequest);
    }

}